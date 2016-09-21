create database bankapp;
create table accounts (id int not null auto_increment, accountNumber int not null, balance int, primary key(id))
insert into accounts(accountNumber, balance) values (100, 50);