CREATE TABLE control(
   control_id NUMBER(5) NOT NULL,
   control_date DATE NOT NULL,
   observations VARCHAR2(500) NOT NULL,
   machine_id NUMBER(5) NOT NULL,
   leader_id NUMBER(5) NOT NULL,
   CONSTRAINT control_pk PRIMARY KEY (control_id),
   CONSTRAINT fk_control_machine FOREIGN KEY (machine_id) REFERENCES machine(machine_id),
   CONSTRAINT fk_control_leader FOREIGN KEY (leader_id) REFERENCES leader(leader_id)

)