CREATE TABLE orders(
	order_id NUMBER(5) NOT NULL,
	order_date DATE NOT NULL,
	price NUMBER(10,2) NOT NULL,
	employee_id NUMBER(5) NOT NULL,
	CONSTRAINT orders_pk PRIMARY KEY (order_id),
	CONSTRAINT fk_orders FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
);