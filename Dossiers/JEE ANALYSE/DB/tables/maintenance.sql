CREATE TABLE maintenance(
   maintenance_id NUMBER(5) NOT NULL,
   maintenance_date DATE NOT NULL,
   maintenance_status VARCHAR2(100) NOT NULL,
   duration INTERVAL DAY TO SECOND,
   machine_id NUMBER(5) NOT NULL,
   leader_id NUMBER(5) NOT NULL,
   CONSTRAINT maintenance_pk PRIMARY KEY (maintenance_id),
   CONSTRAINT fk_maintenance_machine FOREIGN KEY (machine_id) REFERENCES machine(machine_id),
   CONSTRAINT fk_maintenance_leader FOREIGN KEY (leader_id) REFERENCES leader(leader_id),
   CONSTRAINT check_maintenance_status CHECK (maintenance_status IN ('todo','done','ongoing','toredo','validated'))	
   
)