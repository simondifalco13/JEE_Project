CREATE TABLE worker(
  worker_id NUMBER(5) NOT NULL,
  worker_firstname VARCHAR2(100) NOT NULL,
  worker_lastname VARCHAR2(100) NOT NULL,
  worker_mail VARCHAR2(100) NOT NULL,
  worker_password VARCHAR2(200) NOT NULL,
  site_id NUMBER(5) NOT NULL, 
  CONSTRAINT worker_pk PRIMARY KEY (worker_id),
  CONSTRAINT fk_site_worker FOREIGN KEY (site_id) REFERENCES site(site_id)
)