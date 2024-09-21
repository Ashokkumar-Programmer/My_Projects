create database ecommerce;

use ecommerce;

create table userdata(username varchar(100) primary key not null, 
					password varchar(100) not null, 
                    name varchar(300) not null, 
                    address varchar(300) not null, 
                    phonenumber long not null, 
                    usertype varchar(30) not null);

insert into userdata values("ashokkumarprogrammer@gmail.com", "ashokkumar", "Ashok Kumar","Nazerath, Tamilnadu", 9489458843, "customer");

update userdata set name="Ashok" where username="ashokkumarprogrammer@gmail.com";

delete from userdata where username="hariharan1@gmail.com";

select * from userdata;

update userdata set address="Nazerath, Tamilnadu" where username="ashokkumarprogrammer@gmail.com";

create table products(productId int auto_increment primary key not null,
					imageURL varchar(500) not null,
                    productname varchar(300) not null,
                    productprice int not null,
                    description varchar(2000) not null,
                    highlights varchar(2000) not null,
                    category varchar(30) not null,
                    cart int not null,
                    solded int not null);

alter table products auto_increment=1;

drop table products;

delete from products where productId=3;

select * from products;

update products set cart=0;

describe products;

update products set category="Electronics" where productId=1;

update products set solded=0;

update products set cart=2 where productId=1;

describe userdata;








select * from userdata;

select * from products;

