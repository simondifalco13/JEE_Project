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



public class CreateMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public CreateMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
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
		}
		catch(Exception e) {
			System.out.println("Exception dans createmaintenance servlet doGet " + e.getMessage() + e.toString());
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String errors="";
		try {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			FactoryMachine machine=(FactoryMachine) session.getAttribute("machine");
			String inputDate= request.getParameter("date");
			String inputStartTime=request.getParameter("start");
			String[] inputWorkers=request.getParameterValues("worker");
					
			ArrayList<Worker> workers=new ArrayList<Worker>();
			MaintenanceStatus status=MaintenanceStatus.todo;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm");
			Date maintenanceDate = null;
			LocalTime localTime = null;
			try {
				if(inputDate!=null && !inputDate.equals("")) {
					maintenanceDate = dateFormat.parse(inputDate);
					Date currentDate=new Date();
					Date currentDateFinal=new Date(currentDate.getDate(),currentDate.getMonth(),currentDate.getYear());
					Date maintenanceDateFinal=new Date(maintenanceDate.getDate(),maintenanceDate.getMonth(),maintenanceDate.getYear());
					if(maintenanceDateFinal.compareTo(currentDateFinal)<0) {
						errors+="Please enter a correct date : today or future days.<br>";
					}
				}else {
					errors+="Please select a date.<br>";
				}
				
				if(inputStartTime!=null && !inputStartTime.equals("")) {
					localTime = LocalTime.parse(inputStartTime, timeformat);
				}else {
					errors+="Please select an hour.<br>";
				}
				
				if(inputWorkers==null) {
					errors+="Please select at least one worker.<br>";
				}else {
					for(int i=0;i<inputWorkers.length;i++) {
						Worker worker=new Worker();
						int workerId=Integer.valueOf(inputWorkers[i]);
						worker.setSerialNumber(workerId);
						workers.add(worker);
					}
				}

				if(errors.equals("")) {
					Maintenance maintenance=new Maintenance(maintenanceDate,localTime,machine,status,workers,leader);
					boolean success=maintenance.insertMaintenance();
					if(success) {
						response.sendRedirect("machines");
					}
				}
				
			} catch (ParseException e) {
				errors+="Select a correct date and a correct hour.<br>";
			}
			
			if(!errors.isEmpty()) {
				request.setAttribute("error", errors);
				doGet(request,response);
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans createmaintenance servlet doPost " + e.getMessage() + e.toString());
		}
			

	
		
	}

}
