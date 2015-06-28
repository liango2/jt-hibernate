drop table  CUSTOMERS;
create table CUSTOMERS (
       ID number(19,0) not null, 
       NAME varchar(15) not null unique, 
       REGISTERED_TIME timestamp, 
       AGE integer not null check (AGE>10), 
       SEX char(1), 
       IS_MARRIED int, 
       DESCRIPTION varchar2(256), 
       primary key (ID));

create index IDX_REGISTERED_TIME on CUSTOMERS (REGISTERED_TIME);


create sequence hibernate_sequence;