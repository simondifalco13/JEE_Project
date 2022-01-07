
DROP TABLE machine_areas;
DROP TABLE worker_maintenance;
DROP TABLE areas;
DROP TABLE worker;
DROP TABLE maintenance;
DROP TABLE machine;
DROP TABLE leader;
DROP TABLE order_supplier_machine;
DROP TABLE orders;
DROP TABLE factory_employee;
DROP TABLE site;
DROP TABLE supplier_machine;
DROP TABLE supplier;



DROP SEQUENCE site_sequence;
DROP SEQUENCE areas_sequence;
DROP SEQUENCE worker_sequence;
DROP SEQUENCE leader_sequence;
DROP SEQUENCE machine_sequence;
DROP SEQUENCE maintenance_sequence;
DROP SEQUENCE employee_sequence;
DROP SEQUENCE supplier_sequence;
DROP SEQUENCE orders_sequence;
DROP SEQUENCE supplier_machine_sequence;





CREATE TABLE site (
  site_id NUMBER(5) CONSTRAINT pk_site PRIMARY KEY,
  city VARCHAR2(100) NOT NULL,
  address VARCHAR2(100) NOT NULL
);


CREATE TABLE areas(
  areas_id NUMBER(5) NOT NULL CONSTRAINT areas_pk PRIMARY KEY,
  section VARCHAR2(15) NOT NULL,
  dangerousness VARCHAR2(50) NOT NULL,
  site_id NUMBER(5) NOT NULL CONSTRAINT fk_site_area REFERENCES site(site_id),
  CONSTRAINT check_dangerousness_type CHECK (dangerousness IN ('green', 'orange', 'red','black'))
);

CREATE TABLE worker(
  worker_id NUMBER(5) NOT NULL CONSTRAINT worker_pk PRIMARY KEY,
  worker_firstname VARCHAR2(100) NOT NULL,
  worker_lastname VARCHAR2(100) NOT NULL,
  worker_mail VARCHAR2(100) NOT NULL,
  worker_password VARCHAR2(200) NOT NULL,
  site_id NUMBER(5) NOT NULL CONSTRAINT fk_site_worker REFERENCES site(site_id) 
);


CREATE TABLE leader(
  leader_id NUMBER(5) NOT NULL CONSTRAINT leader_pk PRIMARY KEY ,
  leader_firstname VARCHAR2(100) NOT NULL,
  leader_lastname VARCHAR2(100) NOT NULL,
  leader_mail VARCHAR2(100) NOT NULL,
  leader_password VARCHAR2(200) NOT NULL,
  site_id NUMBER(5) NOT NULL CONSTRAINT fk_site_leader REFERENCES site(site_id)
);

CREATE TABLE machine(
  machine_id NUMBER(5) NOT NULL CONSTRAINT machine_pk PRIMARY KEY,
  machine_type VARCHAR(50) NOT NULL,
  machine_status VARCHAR(100) NOT NULL,
  model VARCHAR(50) NOT NULL,
  brand VARCHAR(50) NOT NULL,
  description VARCHAR(200),
  site_id NUMBER(5) NOT NULL CONSTRAINT fk_site_machine  REFERENCES site(site_id),
  CONSTRAINT check_machine_type CHECK (machine_type IN ('production','assembly','sorting')),
  CONSTRAINT check_machine_status CHECK (machine_status IN ('running','off','down','waitingformaintenance','toreplace'))
);



CREATE TABLE maintenance(
   maintenance_id NUMBER(5) NOT NULL CONSTRAINT maintenance_pk PRIMARY KEY ,
   maintenance_date DATE NOT NULL,
   maintenance_status VARCHAR2(100) NOT NULL,
   maintenance_start TIMESTAMP,
   maintenance_end TIMESTAMP,
   machine_id NUMBER(5) NOT NULL CONSTRAINT fk_maintenance_machine REFERENCES machine(machine_id),
   leader_id NUMBER(5) NOT NULL CONSTRAINT fk_maintenance_leader REFERENCES leader(leader_id),
   CONSTRAINT check_maintenance_status CHECK (maintenance_status IN ('todo','done','ongoing','toredo','validated'))
);

CREATE TABLE worker_maintenance(
   maintenance_id NUMBER(5) NOT NULL CONSTRAINT fk_maintenance_worker_maintenance REFERENCES maintenance(maintenance_id),
   worker_id NUMBER(5) NOT NULL CONSTRAINT fk_worker_worker_maintenance REFERENCES worker(worker_id),
   report VARCHAR2(500),
   CONSTRAINT uc_worker_maintenance UNIQUE (maintenance_id,worker_id)
);


CREATE TABLE machine_areas(
  machine_id NUMBER(5) NOT NULL CONSTRAINT fk_machine_machine_area REFERENCES machine(machine_id),
  areas_id NUMBER(5) NOT NULL CONSTRAINT fk_areas_machine_areas REFERENCES areas(areas_id),
  CONSTRAINT uc_machine_areas UNIQUE (machine_id,areas_id)
);

 CREATE TABLE factory_employee(
	employee_id NUMBER(5) NOT NULL CONSTRAINT employee_pk PRIMARY KEY,
	employee_firstname VARCHAR2(100) NOT NULL,
	employee_lastname VARCHAR2(100) NOT NULL,
	employee_mail VARCHAR2(100) NOT NULL,
	employee_password VARCHAR2(200) NOT NULL,
	site_id NUMBER(5) NOT NULL CONSTRAINT fk_siteid_employee REFERENCES site(site_id)
);


CREATE TABLE supplier(
	supplier_id NUMBER(5) NOT NULL CONSTRAINT supplier_pk PRIMARY KEY,
	supplier_name VARCHAR2(100) NOT NULL,
	supplier_phone VARCHAR2(50) NOT NULL,
	supplier_mail VARCHAR2(100) NOT NULL
);


CREATE TABLE orders(
	order_id NUMBER(5) NOT NULL CONSTRAINT orders_pk PRIMARY KEY ,
	order_date DATE NOT NULL,
	price NUMBER(10,2) NOT NULL,
	employee_id NUMBER(5) NOT NULL CONSTRAINT fk_orders REFERENCES factory_employee(employee_id)
);


CREATE TABLE supplier_machine(
	supplier_machine_id NUMBER(5) NOT NULL CONSTRAINT supplier_machine_pk PRIMARY KEY,
	model VARCHAR2(50) NOT NULL,
	brand VARCHAR2(50) NOT NULL,
	price number(10,2) NOT NULL,
	machine_type VARCHAR2(50) NOT NULL,
	description VARCHAR2(200), 
	supplier_id NUMBER(5) NOT NULL CONSTRAINT fk_supplier_machine REFERENCES supplier (supplier_id),
	CONSTRAINT check_supplier_machine_type CHECK (machine_type IN ('production','assembly','sorting'))
);

CREATE TABLE order_supplier_machine(
	order_id NUMBER(5) NOT NULL CONSTRAINT fk_order_order_supplier_machine REFERENCES orders(order_id),
	quantity SMALLINT NOT NULL,
	machine_id NUMBER(5) NOT NULL CONSTRAINT fk_machine_order_supplier_machine REFERENCES supplier_machine(supplier_machine_id),
	CONSTRAINT uc_order_supplier_machine UNIQUE (machine_id,order_id)
);





CREATE SEQUENCE site_sequence
INCREMENT BY 1
START WITH 1
NOCACHE;

CREATE SEQUENCE areas_sequence
INCREMENT BY 1
START WITH 1
NOCACHE;

CREATE SEQUENCE worker_sequence
INCREMENT BY 1
START WITH 20000
MAXVALUE 29999
NOCACHE;


CREATE SEQUENCE leader_sequence
INCREMENT BY 1
START WITH 30000
MAXVALUE 39999
NOCACHE;

CREATE SEQUENCE machine_sequence
INCREMENT BY 1
START WITH 1
NOCACHE;

CREATE SEQUENCE maintenance_sequence
INCREMENT BY 1
START WITH 1
NOCACHE;

CREATE SEQUENCE employee_sequence
INCREMENT BY 1
START WITH 40000
MAXVALUE 49999
NOCACHE;


CREATE SEQUENCE supplier_sequence
INCREMENT BY 1
START WITH 1
NOCACHE;

CREATE SEQUENCE orders_sequence
INCREMENT BY 1
START WITH 1
NOCACHE;

CREATE SEQUENCE supplier_machine_sequence
INCREMENT BY 1
START WITH 1
NOCACHE;





CREATE OR REPLACE TRIGGER site_on_insert
  BEFORE INSERT ON site
  FOR EACH ROW
BEGIN
  SELECT site_sequence.nextval
  INTO :new.site_id
  FROM dual;
END;
/


CREATE OR REPLACE TRIGGER areas_on_insert
  BEFORE INSERT ON areas
  FOR EACH ROW
BEGIN
  SELECT areas_sequence.nextval
  INTO :new.areas_id
  FROM dual;
END;
/


CREATE OR REPLACE TRIGGER worker_on_insert
  BEFORE INSERT ON worker
  FOR EACH ROW
BEGIN
  SELECT worker_sequence.nextval
  INTO :new.worker_id
  FROM dual;
END;
/

CREATE OR REPLACE TRIGGER leader_on_insert
  BEFORE INSERT ON leader
  FOR EACH ROW
BEGIN
  SELECT leader_sequence.nextval
  INTO :new.leader_id
  FROM dual;
END;
/


CREATE OR REPLACE TRIGGER machine_on_insert
  BEFORE INSERT ON machine
  FOR EACH ROW
BEGIN
  SELECT machine_sequence.nextval
  INTO :new.machine_id
  FROM dual;
END;
/

CREATE OR REPLACE TRIGGER maintenance_on_insert
  BEFORE INSERT ON maintenance
  FOR EACH ROW
BEGIN
  SELECT maintenance_sequence.nextval
  INTO :new.maintenance_id
  FROM dual;
END;
/

CREATE OR REPLACE TRIGGER employee_on_insert
  BEFORE INSERT ON factory_employee
  FOR EACH ROW
BEGIN
  SELECT employee_sequence.nextval
  INTO :new.employee_id
  FROM dual;
END;
/


CREATE OR REPLACE TRIGGER supplier_on_insert
  BEFORE INSERT ON supplier
  FOR EACH ROW
BEGIN
  SELECT supplier_sequence.nextval
  INTO :new.supplier_id
  FROM dual;
END;
/


CREATE OR REPLACE TRIGGER orders_on_insert
  BEFORE INSERT ON orders
  FOR EACH ROW
BEGIN
  SELECT orders_sequence.nextval
  INTO :new.order_id
  FROM dual;
END;
/


CREATE OR REPLACE TRIGGER supplier_machine_on_insert
  BEFORE INSERT ON supplier_machine
  FOR EACH ROW
BEGIN
  SELECT orders_sequence.nextval
  INTO :new.supplier_machine_id
  FROM dual;
END;
/