CREATE TABLE machine(
  machine_id NUMBER(5) NOT NULL,
  machine_type VARCHAR(50) NOT NULL,
  machine_status VARCHAR(100) NOT NULL,
  site_id NUMBER(5) NOT NULL,
  CONSTRAINT machine_pk PRIMARY KEY (machine_id),
  CONSTRAINT fk_site_machine FOREIGN KEY (site_id) REFERENCES site(site_id),
  CONSTRAINT check_machine_type CHECK (machine_type IN ('production','assembly','sorting')),
  CONSTRAINT check_machine_status CHECK (machine_status IN ('running','off','down','waitingformaintenance','toreplace'))
)