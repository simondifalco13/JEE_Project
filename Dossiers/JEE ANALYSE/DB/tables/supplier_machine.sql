CREATE TABLE supplier_machine(
	supplier_machine_id NUMBER(5) NOT NULL,
	model VARCHAR2(50) NOT NULL,
	brand VARCHAR2(50) NOT NULL,
	price number(10,2) NOT NULL,
	description VARCHAR2(200)NOT NULL, 
	supplier_id NUMBER(5) NOT NULL,
	CONSTRAINT supplier_machine_pk PRIMARY KEY (supplier_machine_id),
	CONSTRAINT fk_supplier_machine FOREIGN KEY (supplier_id) REFERENCES supplier (supplier_id)
);