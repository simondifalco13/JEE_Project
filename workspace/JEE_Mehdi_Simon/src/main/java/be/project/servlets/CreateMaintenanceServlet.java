package be.project.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.MaintenanceStatus;
import be.project.javabeans.Area;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Maintenance;
import be.project.javabeans.Site;
import be.project.javabeans.Worker;

/**
 * Servlet implementation class CreateMaintenanceServlet
 */

public class CreateMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session!=null) {
			FactoryMachine machine=(FactoryMachine) session.getAttribute("machine");
			ArrayList<Area> machineAreas=machine.getMachineAreas();
			for(int i=0;i<machineAreas.size();i++) {
				Site areasSite=machineAreas.get(i).getAreaSite();
				areasSite.setSiteWorkers(Worker.findSiteWorker(areasSite));
				machineAreas.get(i).setAreaSite(areasSite);
			}
			machine.setMachineAreas(machineAreas);
			session.setAttribute("machine", machine);
			request.getRequestDispatcher("/WEB-INF/JSP/CreateMaintenance.jsp").forward(request,response);
		}else {
			//redirection sur page d'erreur
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String errors="";
		if(session!=null) {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			FactoryMachine machine=(FactoryMachine) session.getAttribute("machine");
			String inputDate= request.getParameter("date");
			String inputStartTime=request.getParameter("start");
			String[] inputWorkers=request.getParameterValues("worker");
			System.out.println("DATE : "+inputDate+ " ; start at = "+inputStartTime
					+" workers : "+inputWorkers[0]+" et "+inputWorkers[1]);
			ArrayList<Worker> workers=new ArrayList<Worker>();
			MaintenanceStatus status=MaintenanceStatus.todo;
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm");

			try {
				Date maintenanceDate = dateFormat.parse(inputDate);
				LocalTime localTime = LocalTime.parse(inputStartTime, timeformat);
				for(int i=0;i<inputWorkers.length;i++) {
					Worker worker=new Worker();
					int workerId=Integer.valueOf(inputWorkers[i]);
					worker.setSerialNumber(workerId);
					workers.add(worker);
				}
				//verification de validité des champs
				Maintenance maintenance=new Maintenance(maintenanceDate,localTime,machine,status,workers,leader);
				boolean success=maintenance.insertMaintenance();
				if(success) {
					//redirection sur la page machine et pop up  
				}
;
				
			} catch (ParseException e) {
				errors+="Select a correct date and a correct hour.";
			}
			
			if(!errors.isEmpty()) {
				request.setAttribute("error", errors);
				doGet(request,response);
			}

		}else {
			//redirection sur page d'erreur
		}
		
	}

}
