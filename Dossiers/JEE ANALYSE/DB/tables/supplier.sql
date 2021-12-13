CREATE TABLE supplier(
	supplier_id NUMBER(5) NOT NULL,
	supplier_name VARCHAR2(100) NOT NULL,
	supplier_phone VARCHAR2(50) NOT NULL,
	supplier_mail VARCHAR2(100) NOT NULL,
	CONSTRAINT supplier_pk PRIMARY KEY (supplier_id)
);