CREATE TABLE area(
  area_id NUMBER(5) NOT NULL,
  section VARCHAR2(15) NOT NULL,
  dangerousness VARCHAR2(50) NOT NULL,
  site_id NUMBER(5) NOT NULL,
  CONSTRAINT area_pk PRIMARY KEY (area_id),
  CONSTRAINT check_dangerousness_type CHECK (dangerousness IN ('green', 'orange', 'red','black')),
  CONSTRAINT fk_site_area FOREIGN KEY (site_id) REFERENCES site(site_id)
)