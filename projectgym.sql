use home;
create table admin(
	id int auto_increment primary key
    ,username varchar(50)
    ,password varchar(200)
);
create table coach(
	id char(10) primary key
    ,coach_name varchar(100)
    ,coach_gender enum("Male", "Female", "Others")
    ,coach_phone char(20)
    ,coach_address varchar(200)
    ,coach_status enum("Active","Inactive")
);
create table tblmember(
	id varchar(10) primary key,
    memberName varchar(40),
    address varchar(100),
    gender varchar(10),
    phone varchar(20),
    schedule varchar(20),
    startDate date,
    endDate date,
    status varchar(20)
    ,coachId char(10)
);
create table tblpayment(
	id int auto_increment primary key
    ,memberId char(10)
    ,subtotal double
    ,discount double
    ,total double
    ,create_at datetime
);