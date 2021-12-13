CREATE TABLE leader(
  leader_id NUMBER(5) NOT NULL,
  leader_firstname VARCHAR2(100) NOT NULL,
  leader_lastname VARCHAR2(100) NOT NULL,
  leader_mail VARCHAR2(100) NOT NULL,
  leader_password VARCHAR2(200) NOT NULL,
  site_id NUMBER(5) NOT NULL, 
  CONSTRAINT leader_pk PRIMARY KEY (leader_id),
  CONSTRAINT fk_site_leader FOREIGN KEY (site_id) REFERENCES site(site_id)
)