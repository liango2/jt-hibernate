
create table CUSTOMERS (
   ID number not null,
   NAME varchar(15),
   AGE number,
   primary key (ID)
);

create table ORDERS (
   ID number(19,0) not null,
   ORDER_NUMBER varchar(15),
   PRICE double precision,
   CUSTOMER_ID  number(19,0),
   primary key (ID)
);

create index IDX_CUSTOMER on ORDERS(CUSTOMER_ID);

alter table ORDERS
   add constraint FK_CUSTOMER foreign key (CUSTOMER_ID) references CUSTOMERS
   (ID);

