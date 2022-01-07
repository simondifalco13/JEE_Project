
INSERT INTO worker (worker_firstname,worker_lastname,worker_mail,worker_password,site_id)
VALUES ('Daniel','Craig','daniel.craig@gmail.com','Lstbnd070',1);

INSERT INTO worker (worker_firstname,worker_lastname,worker_mail,worker_password,site_id)
VALUES ('Michel','Platini','miche.platini@gmail.com','PlFr19or',1);



INSERT INTO leader (leader_firstname,leader_lastname,leader_mail,leader_password,site_id)
VALUES ('Martin','Garix','garix.martin@gmail.com','Mdrtsf85',1);

INSERT INTO leader(leader_firstname,leader_lastname,leader_mail,leader_password,site_id) VALUES ('John','Doe','john.doe@hotmail.com','ptdTr791', 1);



INSERT INTO factory_employee (employee_firstname,employee_lastname,employee_mail,employee_password,site_id)
VALUES ('Cliff','Richard','cliff.richard@gmail.com','Clifsq8d',1);

INSERT INTO factory_employee (employee_firstname,employee_lastname,employee_mail,employee_password,site_id)
VALUES ('Dan','Bison','bison.dan@hotmail.com','B3redape',1);

INSERT INTO areas(section,dangerousness,site_id) VALUES ('A','green',1);
INSERT INTO areas(section,dangerousness,site_id) VALUES ('B','green',1);
INSERT INTO areas (section,dangerousness,site_id) values ('B', 'red' , 2);


INSERT INTO site(city,address) VALUES('Charleroi','Chaussee de marcinelle');
INSERT INTO site (city,address) VALUES('Bruxelles' ,'Boulevard Lambermont ');

BEGIN 
 insert_machine ('production',1,'running','ProdZ52','BelgiumProd',null,1);
  END;
  /
BEGIN 
 insert_machine ('production',1,'running','ProdZ42','BelgiumProd',null,1);
  END;
  /


INSERT INTO supplier(supplier_name,supplier_phone,supplier_mail) VALUES ('MCSteel','07145397204','mcsteel.fournisseur@gmail.com');
INSERT INTO supplier(supplier_name,supplier_phone,supplier_mail) VALUES ('BelgiumFab','07185241019','belgiumfab.fournisseur@gmail.com');



insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('Assembly2000','AssemblyUnion',3000,'Newest Assembly machine',1,'assembly');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('Assembly2001','AssemblyUnion',6000,'Latest Assembly machine',1,'assembly');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('HQ1','GGMFactory',10000,'',1,'sorting');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('HQ2','GGMFactory',12000,'',1,'sorting');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('HQ3','GGMFactory',15000,'',1,'sorting');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('HBK1','ACMIndustry',5000,'',2,'sorting');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('HBK2','ACMIndustry',6000,'',2,'sorting');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('HBK3','ACMIndustry',9000,'',2,'sorting');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('Masd2000','SteelSet',20000,'',1,'assembly');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('Masd4000','SteelSet',40000,'',1,'assembly');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('TheOne','TopMachine',30000,'',2,'assembly');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('BrodV1','ADNMetal',120000,'',1,'production');
insert into supplier_machine(model,brand,price,description,supplier_id,machine_type) VALUES ('BrodV2','ADNMetal',400000,'',1,'production');


 DECLARE
  id maintenance.maintenance_id%type;
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_maintenance('2022-01-07','todo',2,30000,null,null,id,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  dbms_output.put_line('Id créer : ' || id);
  END;
 /
 DECLARE
  id maintenance.maintenance_id%type;
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_maintenance('2022-01-10','todo',2,30000,null,null,id,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  dbms_output.put_line('Id créer : ' || id);
  END;
 /
 DECLARE
  id maintenance.maintenance_id%type;
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_maintenance('2022-01-09','todo',2,30000,null,null,id,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  dbms_output.put_line('Id créer : ' || id);
  END;
 /

 DECLARE
  id maintenance.maintenance_id%type;
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_maintenance('2022-01-07','toredo',1,30003,null,null,id,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  dbms_output.put_line('Id créer : ' || id);
  END;
 /

DECLARE
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_worker_maintenance(20000,26,null,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  END;
 /
DECLARE
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_worker_maintenance(20000,27,null,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  END;
 /
DECLARE
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_worker_maintenance(20001,29,null,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  END;
 /
DECLARE
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_worker_maintenance(20001,30,null,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  END;
 /

DECLARE
  errorCode maintenance.maintenance_id%TYPE;
  BEGIN
  insert_worker_maintenance(20001,31,null,errorCode);
  dbms_output.put_line('code renvoyer : ' || errorCode);
  END;
 /

 DECLARE
  id orders.order_id%type;
  code NUMBER;
  BEGIN
  insert_orders('2022-01-04',5000,'40000',id,2,code);
  dbms_output.put_line('code renvoyer : ' || code);
  dbms_output.put_line('Id créer : ' || id);
  END;
  /
DECLARE
  id orders.order_id%type;
  code NUMBER;
  BEGIN
  insert_orders('2022-01-05',7000,'40000',id,5,code);
  dbms_output.put_line('code renvoyer : ' || code);
  dbms_output.put_line('Id créer : ' || id);
  END;
  /
DECLARE
  id orders.order_id%type;
  code NUMBER;
  BEGIN
  insert_orders('2022-01-01',100000,'40001',id,8,code);
  dbms_output.put_line('code renvoyer : ' || code);
  dbms_output.put_line('Id créer : ' || id);
  END;
  /







