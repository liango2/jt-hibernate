
create table CUSTOMERS (
  ID number(19,0) not null primary key,
  NAME varchar(15) not null,
  EMAIL varchar(128) not null,
  PASSWORD varchar(8) not null,
  PHONE int ,
  ADDRESS varchar(255),
  SEX char(1) ,
  IS_MARRIED int,
  DESCRIPTION VARCHAR2(256),
  IMAGE blob,
  BIRTHDAY date,
  REGISTERED_TIME timestamp
);

