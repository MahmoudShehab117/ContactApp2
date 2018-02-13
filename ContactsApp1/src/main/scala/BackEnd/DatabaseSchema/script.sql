DROP TABLE IF EXISTS Contact;
create table CONTACT
(
    ID INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    FIRSTNAME VARCHAR NOT NULL,
    LASTNAME VARCHAR NOT NULL,
    EMAIL VARCHAR NOT NULL,
    PHONE VARCHAR NOT NULL
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