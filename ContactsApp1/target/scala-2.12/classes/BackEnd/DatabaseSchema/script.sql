DROP TABLE IF EXISTS Contact;
CREATE TABLE Contact(
Id INT,
firstName varchar(50),
lastName varchar(50),
email varchar(50),
phone varchar(50)
);

DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
userName varchar(50),
password varchar(500)
);


insert into Contact VALUES (1,'mahmoud','shehab','saas@gmail.com','123');
insert into Contact VALUES (2,'salah','shehab','yy@gmail.com','354');
insert into Contact VALUES (3,'hamada','shehab','ww@gmail.com','56');
insert into Contact VALUES (4,'khaled','shehab','rrr@gmail.com','767777777');
insert into Contact VALUES (5,'yehia','shehab','sddd@gmail.com','7777777');

insert into Users VALUES ('test','test');