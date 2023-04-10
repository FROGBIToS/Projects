CREATE TABLE Author (
    Id_Author int NOT NULL AUTO_INCREMENT,
    FirstName varchar(30) NOT NULL,
    LastName varchar(30) NOT NULL,
    Email varchar(50) NOT NULL,
    CONSTRAINT Author_pk PRIMARY KEY (Id_Author)
);

CREATE TABLE Author_Book (
    Id_Author_Book int NOT NULL AUTO_INCREMENT,
    Publisher varchar(40) NOT NULL,
    Amount_Copy int NOT NULL,
    Is_Sold bool NOT NULL,
    Id_Author int NOT NULL,
    Id_Book int NOT NULL,
    CONSTRAINT Author_Book_pk PRIMARY KEY (Id_Author_Book)
);

CREATE TABLE Book (
    Id_Book int NOT NULL AUTO_INCREMENT,
    Name varchar(25) NOT NULL,
    Writting_date date NOT NULL,
    Language varchar(30) NOT NULL,
    Is_Bestseller int NULL,
    CONSTRAINT Id_Book PRIMARY KEY (Id_Book)
);

ALTER TABLE Author_Book ADD CONSTRAINT Author_Book_Author FOREIGN KEY Author_Book_Author (Id_Author)
    REFERENCES Author (Id_Author);

ALTER TABLE Author_Book ADD CONSTRAINT Author_Book_Book FOREIGN KEY Author_Book_Book (Id_Book)
    REFERENCES Book (Id_Book);

INSERT INTO Book(Id_Book, Name, Writting_date, Language, Is_Bestseller)
VALUES(1,'Złota ręka','2004-10-04','Polski',FALSE),
      (2,'Crazy days','2005-02-02','English',FALSE),
      (3,'Czarna dziura','2013-08-22','Polski',TRUE),
      (4,'Moloda','2024-09-01','Ukrainian',FALSE);

INSERT INTO Author(Id_Author, FirstName, LastName, Email)
VALUES (1,'Dawid','Narowski','edN@gmail.com'),
       (2,'Olena','Panasenko','panasenkoOlena@ukr.net'),
       (3,'Jhon','Climps','climps@gmail.com');

INSERT INTO Author_Book(Id_Author_Book, Publisher, Amount_Copy, Is_Sold, Id_Author, Id_Book)
VALUES (1,'DDD_Public',100,FALSE,1,1),
       (2,'DDD_Public',400,FALSE,3,1),
       (3,'SarafanO',10000,TRUE,2,4),
       (4,'Warsaw_Public',0,TRUE,1,2),
       (5,'Remino',1234,FALSE,2,3),
       (6,'Warsaw_Public',0,TRUE,3,2);

