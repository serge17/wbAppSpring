drop table Data if exists;

create table Data(
	id varchar(100) not null,
	indicatorId varchar(100),
	indicatorValue varchar(255),
	countryId varchar(100),
	countryValue varchar(255),
	val double,
	decim int,
	year int,
	primary key (id)
);