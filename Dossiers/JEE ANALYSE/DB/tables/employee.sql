CREATE TABLE employee(
	employee_id NUMBER(5) NOT NULL,
	employee_firstname VARCHAR2(100) NOT NULL,
	employee_lastname VARCHAR2(100) NOT NULL,
	employee_mail VARCHAR2(100) NOT NULL,
	employee_password VARCHAR2(200) NOT NULL,
	CONSTRAINT employee_pk PRIMARY KEY (employee_id)
);