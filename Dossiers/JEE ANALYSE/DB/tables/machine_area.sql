CREATE TABLE machine_area(
  machine_id NUMBER(5) NOT NULL,
  area_id NUMBER(5) NOT NULL,
  CONSTRAINT machine_area_pk PRIMARY KEY (machine_id,area_id)
)