DROP TABLE IF EXISTS poc_process_batch.person;
CREATE TABLE poc_process_batch.person (
  person_id bigint(20) NOT NULL AUTO_INCREMENT,
  first_name varchar(40) DEFAULT NULL,
  last_name varchar(40) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  PRIMARY KEY (person_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;