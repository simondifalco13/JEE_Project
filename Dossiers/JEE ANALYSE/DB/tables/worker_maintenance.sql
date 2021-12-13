CREATE TABLE worker_maintenance(
   maintenance_id NUMBER(5) NOT NULL,
   worker_id NUMBER(5) NOT NULL,
   rapport VARCHAR2(500) NOT NULL,
   CONSTRAINT worker_maintenance_pk PRIMARY KEY (maintenance_id,worker_id)
)