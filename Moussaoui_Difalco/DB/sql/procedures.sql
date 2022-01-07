




CREATE OR REPLACE PROCEDURE insert_machine(
	type IN machine.machine_type%TYPE,
	site_id IN site.site_id%TYPE,
	status IN machine.machine_status%TYPE,
	model IN machine.model%TYPE,
	brand IN machine.brand%TYPE,
	description IN machine.description%TYPE,
	areas_id IN areas.areas_id%TYPE,
	created_machine_id OUT machine.machine_id%TYPE) 
AS
BEGIN
	INSERT INTO machine(machine_type,site_id,machine_status,model,brand,description) 
	VALUES (type,site_id,status,model,brand,description) 
	RETURNING machine_id INTO created_machine_id;
	insert_machine_areas(created_machine_id,areas_id);
	COMMIT;
	EXCEPTION 
		WHEN OTHERS THEN 
			DBMS_OUTPUT.PUT_lINE('Error while creating a new machine');	
	
END;





CREATE OR REPLACE PROCEDURE insert_machine_areas(
	machine_id IN machine.machine_id%TYPE,
	areas_id IN areas.areas_id%TYPE)
AS
BEGIN
	INSERT INTO machine_areas(machine_id,areas_id)
	VALUES (machine_id,areas_id);
	COMMIT;
	EXCEPTION 
		WHEN OTHERS THEN
			DBMS_OUTPUT.PUT_lINE('Error while inserting machine in area');		
END;




CREATE OR REPLACE PROCEDURE update_machine(
	machine_id_var IN machine.machine_id%TYPE,
	status IN machine.machine_status%TYPE,
	forException OUT machine.machine_id%TYPE
	) 
AS
	UNHANDLED_EXCEPTION_CODE machine.machine_id%TYPE;
BEGIN
	UNHANDLED_EXCEPTION_CODE := -1;
	UPDATE machine SET machine_status=status WHERE machine_id=machine_id_var;
	COMMIT;
	EXCEPTION 
		WHEN OTHERS THEN 
			DBMS_OUTPUT.PUT_lINE('Error while creating a new machine');
			forException :=  SQLCODE;	
END;
/




create or replace PROCEDURE selectAllFactoryMachines
(out_cursor OUT SYS_REFCURSOR)
AS
BEGIN
  OPEN out_cursor FOR
 SELECT machine_id,machine_type,site_id,
 machine_status,model,brand,description 
 FROM machine ;
END;

create or replace PROCEDURE selectMachineMaintenances
(machine_id_param maintenance.machine_id%type, out_cursor OUT SYS_REFCURSOR)
AS
BEGIN
  OPEN out_cursor FOR
 SELECT 
 maintenance_id,maintenance_date,maintenance_status,
 leader_id,maintenance_start,maintenance_end 
 FROM maintenance 
 WHERE machine_id=machine_id_param;
END;


create or replace PROCEDURE selectAllSiteMachine
(site_id_param machine.site_id%type,out_cursor OUT SYS_REFCURSOR)
AS
BEGIN
  OPEN out_cursor FOR
SELECT 
 machine_id,machine_type,site_id,machine_status,
 model,brand,description 
 FROM machine 
 WHERE site_id=site_id_param;
END;






CREATE OR REPLACE PROCEDURE insert_maintenance(
	date_m IN VARCHAR2,
	status IN maintenance.maintenance_status%TYPE,
	machine_id IN machine.machine_id%TYPE,
	leader_id IN leader.leader_id%TYPE ,
	maintenance_start IN maintenance.maintenance_start%TYPE,
	maintenance_end IN maintenance.maintenance_end%TYPE,
	created_maintenance_id OUT maintenance.maintenance_id%TYPE,
	forException OUT maintenance.maintenance_id%TYPE) 
AS
	ex_date_format exception;
  	pragma exception_init(ex_date_format, -1843);
BEGIN
	INSERT INTO maintenance (maintenance_date,maintenance_status,machine_id,leader_id,maintenance_start,maintenance_end) 
	VALUES (TO_DATE(date_m, 'YYYY-MM-DD'),status,machine_id,leader_id,maintenance_start,maintenance_end)
	RETURNING maintenance_id INTO created_maintenance_id;
	COMMIT;
	EXCEPTION
		WHEN ex_date_format THEN 
			DBMS_OUTPUT.PUT_lINE('Date not valid : neeed YYYY-MM-DD format');
			forException := SQLCODE;
		WHEN OTHERS THEN 
			DBMS_OUTPUT.PUT_lINE('Error while creating a new maintenance');	
			forException := SQLCODE;
	
END;





CREATE OR REPLACE PROCEDURE insert_worker_maintenance(
	worker_id IN worker.worker_id%TYPE,
	maintenance_id IN maintenance.maintenance_id%TYPE,
	report IN worker_maintenance.report%TYPE,
	forException OUT maintenance.maintenance_id%TYPE  ) 
AS
BEGIN
	INSERT INTO worker_maintenance (worker_id,maintenance_id,report) 
	VALUES (worker_id,maintenance_id,report);
	COMMIT;
	EXCEPTION 
		WHEN OTHERS THEN 
			DBMS_OUTPUT.PUT_lINE('Error while creating a new record in worker_maintenance');
			forException := SQLCODE; 	
	
END;



CREATE OR REPLACE PROCEDURE update_maintenance(
	maintenance_id_param IN maintenance.maintenance_id%TYPE,
	maintenance_d IN maintenance.maintenance_date%TYPE,
	start_t IN maintenance.maintenance_start%TYPE,
	status IN maintenance.maintenance_status%TYPE,
	forException OUT maintenance.maintenance_id%TYPE 
	)
AS
	end_t  maintenance.maintenance_end%TYPE :=null;
	final_start_t maintenance.maintenance_start%TYPE :=start_t;
	final_maintenance_d maintenance.maintenance_date%TYPE := maintenance_d;
	
BEGIN
	forException := -1;
	IF status='done' OR status='toredo' THEN
		IF status='done' THEN 
			end_t:=CURRENT_TIMESTAMP;
		END IF;
		IF status='toredo' THEN
			final_start_t := CURRENT_TIMESTAMP;
			final_maintenance_d := TO_DATE(CURRENT_DATE, 'YYYY-MM-DD');
		END IF;
		UPDATE maintenance 
		SET maintenance_status=status,
		maintenance_start=final_start_t,
		maintenance_date=final_maintenance_d,
		maintenance_end=end_t
		WHERE maintenance_id=maintenance_id_param;
	END IF;
	IF status='validated' THEN
		UPDATE maintenance 
		SET maintenance_status=status,
		maintenance_start=final_start_t,
		maintenance_date=final_maintenance_d
		WHERE maintenance_id=maintenance_id_param;
	END IF;
	IF status NOT IN ('done','toredo','validated') THEN
		UPDATE maintenance 
		SET maintenance_status=status,
		maintenance_start=final_start_t,
		maintenance_date=final_maintenance_d,
		maintenance_end=end_t
		WHERE maintenance_id=maintenance_id_param;
	END IF;
	
	COMMIT;
	EXCEPTION 
		WHEN OTHERS THEN 
			DBMS_OUTPUT.PUT_lINE('Error while updating maintenance');
			forException := SQLCODE;
END;



CREATE OR REPLACE PROCEDURE changeStatusDone(maintenance_id_param worker_maintenance.maintenance_id%type,
status maintenance.maintenance_status%type, code OUT NUMBER) IS
return_number number;
begin
code :=0;
UPDATE maintenance 
SET maintenance_status= status, maintenance_end =CURRENT_TIMESTAMP
WHERE maintenance_id = maintenance_id_param;
return_number :=sql%rowcount;

if(return_number<1)
	then code :=-1;
	DBMS_OUTPUT.PUT_LINE('Pas de update');

else
	DBMS_OUTPUT.PUT_LINE('Update OK');
	commit;

end if;
EXCEPTION
 WHEN OTHERS THEN
	code :=  SQLCODE;
end;




create or replace PROCEDURE selectWorkerMaintenances
(worker_id_param worker_maintenance.worker_id%type, out_cursor OUT SYS_REFCURSOR)
AS
BEGIN
  OPEN out_cursor FOR
 SELECT m.maintenance_id,m.maintenance_date, m.maintenance_status,
 m.maintenance_start,m.maintenance_end,m.machine_id, m.leader_id,
 l.leader_lastname, wm.report 
 FROM maintenance m, worker_maintenance wm, leader l
 WHERE m.maintenance_id = wm.maintenance_id AND m.leader_id = l.leader_id 
 AND worker_id=worker_id_param;
END;




CREATE OR REPLACE PROCEDURE createReport(maintenance_id_param worker_maintenance.maintenance_id%type,
 worker_id_param worker_maintenance.worker_id%type, report_param worker_maintenance.report%type, code OUT NUMBER) IS
return_number number;
begin
code :=0;
UPDATE worker_maintenance 
	SET report = report_param
	WHERE maintenance_id = maintenance_id_param AND worker_id = worker_id_param;
return_number :=sql%rowcount;

if(return_number<1)
	then code :=-1;
	DBMS_OUTPUT.PUT_LINE('Pas de update');

else
	DBMS_OUTPUT.PUT_LINE('Update OK');
	commit;

end if;
EXCEPTION
 WHEN OTHERS THEN
	code :=  SQLCODE;
end;



CREATE OR REPLACE PROCEDURE insert_orders(
	order_date IN VARCHAR2,
	price IN orders.price%TYPE,
	empl_id IN factory_employee.employee_id%TYPE,
	created_orders_id OUT orders.order_id%TYPE,
	machine_id order_supplier_machine.machine_id%type,
	errorCode OUT NUMBER) IS
	return_code NUMBER;
	errorCodeSecond NUMBER;
BEGIN
	errorCode:=0;
	INSERT INTO orders (order_date,price,employee_id)
	VALUES (TO_DATE(order_date, 'YYYY-MM-DD'),price,empl_id) 
	RETURNING order_id INTO created_orders_id;
	return_code:= sql%rowcount;
     	if(return_code<1)
		then errorCode :=-1;
		DBMS_OUTPUT.PUT_LINE('Insertion failed');

	else
		DBMS_OUTPUT.PUT_LINE('Insertion OK');
	end if;
	if(errorCode=0) then
		insert_order_supplier_machine(machine_id,created_orders_id,1,errorCodeSecond); 
	end if;
	if(errorCodeSecond=0)then
		commit;
	else 	errorCode:=-1;
		rollback;
	end if;
	EXCEPTION 
		WHEN OTHERS THEN 
			DBMS_OUTPUT.PUT_lINE('Error while creating a new record in orders');
	
END;




CREATE OR REPLACE PROCEDURE insert_order_supplier_machine(
	machine_id IN machine.machine_id%TYPE,
	order_id IN orders.order_id%TYPE,
	quantity IN order_supplier_machine.quantity%TYPE,
	errorCode OUT NUMBER) IS
return_code NUMBER;
BEGIN
errorCode:=0;
	INSERT INTO order_supplier_machine(machine_id,order_id,quantity)
	VALUES (machine_id,order_id,quantity);
	return_code:= sql%rowcount;
     	if(return_code<1)
		then errorCode :=-1;
		DBMS_OUTPUT.PUT_LINE('Insertion failed');

	else
		DBMS_OUTPUT.PUT_LINE('Insertion OK');
	end if;
	EXCEPTION 
		WHEN OTHERS THEN 
			DBMS_OUTPUT.PUT_lINE('Error while creating a new record in worker_maintenance');
END;