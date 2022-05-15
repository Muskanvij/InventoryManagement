--	This is a MySQL script to
--		Create a database
--		Create a table
--		Add some data to the table

Drop database if exists CatalogDB;

Create database CatalogDB;

Use CatalogDB;

create table Product(
id varchar(5),
name varchar(20) not null,
price real,
onSale boolean,
primary key (id));

insert into Product
values('p10', 'table', 20.50, 1);

insert into Product
values('p20', 'chair', 10.50, 0);

insert into Product
values('p30', 'lamp', 40.50, 1);

insert into Product
values('p40', 'desk', 30.50, 0);

insert into Product
values('p50', 'table', 50.50, 1);