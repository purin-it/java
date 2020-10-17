CREATE TABLE user_data
(
   id INT NOT NULL,
   name VARCHAR(40) NOT NULL,
   birth_year INT NOT NULL,
   birth_month INT NOT NULL,
   birth_day INT NOT NULL,
   sex CHAR(1) NOT NULL,
   memo VARCHAR(1024),
   PRIMARY KEY(id)
);

CREATE TABLE m_sex
(
   sex_cd CHAR(1) NOT NULL,
   sex_value VARCHAR(4) NOT NULL,
   PRIMARY KEY(sex_cd)
);