create database donatelinkDB;

use donatelinkDB;

create table userlogin(username varchar(50) primary key, password varchar(50), usertype varchar(20));

create table usercustomer(username varchar(50) primary key, customer_fullname varchar(100), customer_address varchar(300), customer_email varchar(300), customer_phonenumber bigint);

create table usercharity(username varchar(50) primary key, charityname varchar(500) unique, inchargename varchar(100), address varchar(300), strength bigint, charitytype varchar(100), email varchar(300), phonenumber bigint, wantedproducts text);

create table useradmin(username varchar(50) primary key, admin_fullname varchar(100), admin_address varchar(300), admin_email varchar(300), admin_phonenumber bigint);

select * from userlogin;

select * from useradmin;