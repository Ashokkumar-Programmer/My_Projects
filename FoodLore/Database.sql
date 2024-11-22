create database foodlore;

use foodlore;

create table userdata(name varchar(300), username varchar(300) primary key, password varchar(300), usertype varchar(15));

select * from userdata;

update userdata set name="Selvam" where username="ashokkumarprogrammer@gmail.com";

create table foods(id int primary key auto_increment, imageUrl text, name varchar(350), description text, comments text);

select * from foods;	

alter table foods add column favourite int;

alter table foods add column comments text;

update foods set comments="";

alter table foods auto_increment=1;

delete from foods;

drop table foods;

describe foods;

delete from userdata;

drop table userdata;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';

flush privileges;