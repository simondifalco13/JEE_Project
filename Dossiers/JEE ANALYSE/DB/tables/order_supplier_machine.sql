CREATE TABLE order_supplier_machine(
	order_id NUMBER(5) NOT NULL,
	quantity SMALLINT NOT NULL,
	machine_id NUMBER(5) NOT NULL,
	CONSTRAINT order_supplier_machine_pk PRIMARY KEY (machine_id,order_id)
);