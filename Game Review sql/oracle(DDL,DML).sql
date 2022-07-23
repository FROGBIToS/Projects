-- Table: Company
CREATE TABLE Company (
    ID_Company integer  NOT NULL,
    Name char(50)  NOT NULL,
    Capitalization number(10,5)  NOT NULL,
    Location char(40)  NOT NULL,
    CONSTRAINT Company_pk PRIMARY KEY (ID_Company)
) ;

-- Table: GAMECOMM
CREATE TABLE GAMECOMM (
    ID_Game integer  NOT NULL,
    ID_Company integer  NOT NULL,
    CONSTRAINT GAMECOMM_pk PRIMARY KEY (ID_Game,ID_Company)
) ;

-- Table: GAMEREV
CREATE TABLE GAMEREV (
    KOD_Review integer  NOT NULL,
    ID_Game integer  NOT NULL,
    CONSTRAINT GAMEREV_pk PRIMARY KEY (KOD_Review,ID_Game)
) ;

-- Table: Games
CREATE TABLE Games (
    IDGame integer  NOT NULL,
    ReDate date  NULL,
    NaGame char(20)  NOT NULL,
    Genre char(15)  NOT NULL,
    CONSTRAINT Games_pk PRIMARY KEY (IDGame)
) ;

-- Table: Genre
CREATE TABLE Genre (
    Genre_nazwa char(15)  NOT NULL,
    CONSTRAINT Genre_pk PRIMARY KEY (Genre_nazwa)
) ;

-- Table: REVWRITER
CREATE TABLE REVWRITER (
    KOD_Review integer  NOT NULL,
    ID_WRITER integer  NOT NULL,
    CONSTRAINT REVWRITER_pk PRIMARY KEY (KOD_Review,ID_WRITER)
) ;

-- Table: Review
CREATE TABLE Review (
    KOD_Review integer  NOT NULL,
    Title char(30)  NOT NULL,
    Date_Writed date  NULL,
    Body_Review char(20)  NOT NULL,
    CONSTRAINT Review_pk PRIMARY KEY (KOD_Review)
) ;

-- Table: Writer
CREATE TABLE Writer (
    ID_WRITER integer  NOT NULL,
    NaWriter char(30)  NOT NULL,
    SurnWriter char(30)  NOT NULL,
    Numphone varchar2(12)  NULL,
    Mail varchar2(30)  NULL,
    CONSTRAINT Writer_pk PRIMARY KEY (ID_WRITER)
) ;

-- foreign keys
-- Reference: Games_Genre (table: Games)
ALTER TABLE Games ADD CONSTRAINT Games_Genre
    FOREIGN KEY (Genre)
    REFERENCES Genre (Genre_nazwa);

-- Reference: REVWRITER_Review (table: REVWRITER)
ALTER TABLE REVWRITER ADD CONSTRAINT REVWRITER_Review
    FOREIGN KEY (KOD_Review)
    REFERENCES Review (KOD_Review);

-- Reference: REVWRITER_Writer (table: REVWRITER)
ALTER TABLE REVWRITER ADD CONSTRAINT REVWRITER_Writer
    FOREIGN KEY (ID_WRITER)
    REFERENCES Writer (ID_WRITER);

-- Reference: Table_7_Company (table: GAMECOMM)
ALTER TABLE GAMECOMM ADD CONSTRAINT Table_7_Company
    FOREIGN KEY (ID_Company)
    REFERENCES Company (ID_Company);

-- Reference: Table_7_Games (table: GAMECOMM)
ALTER TABLE GAMECOMM ADD CONSTRAINT Table_7_Games
    FOREIGN KEY (ID_Game)
    REFERENCES Games (IDGame);

-- Reference: Table_8_Games (table: GAMEREV)
ALTER TABLE GAMEREV ADD CONSTRAINT Table_8_Games
    FOREIGN KEY (ID_Game)
    REFERENCES Games (IDGame);

-- Reference: Table_8_Review (table: GAMEREV)
ALTER TABLE GAMEREV ADD CONSTRAINT Table_8_Review
    FOREIGN KEY (KOD_Review)
    REFERENCES Review (KOD_Review);


--Genre
INSERT ALL
INTO GENRE (GENRE_NAZWA)
VALUES ('Action')
INTO GENRE (GENRE_NAZWA)
VALUES ('RPG')
INTO GENRE (GENRE_NAZWA)
VALUES ('Adventure')
INTO GENRE (GENRE_NAZWA)
VALUES ('Puzzle')
INTO GENRE (GENRE_NAZWA)
VALUES ('Gore')
INTO GENRE (GENRE_NAZWA)
VALUES ('Fighting')
SELECT * FROM DUAL;

--WRITER
INSERT ALL
INTO WRITER (ID_WRITER, NAWRITER,SURNWRITER,NUMPHONE,MAIL)
VALUES (11,'John','Calips','+12354560102','jhon_cal@gmail.com')
INTO WRITER (ID_WRITER, NAWRITER,SURNWRITER,NUMPHONE,MAIL)
VALUES (12,'Mark','Bread','+12345876511',null)
INTO WRITER (ID_WRITER, NAWRITER,SURNWRITER,NUMPHONE,MAIL)
VALUES (13,'Koll','Cosmo',null,'koll_cosmo@gmail.com')
INTO WRITER (ID_WRITER, NAWRITER,SURNWRITER,NUMPHONE,MAIL)
VALUES (14,'Rico','Sanco','+48546875442','rico_san@gmail.com')
INTO WRITER (ID_WRITER, NAWRITER,SURNWRITER,NUMPHONE,MAIL)
VALUES (15,'Pingin','Harait','+38097865432','pinni@gmail.com')
SELECT * FROM DUAL;

--REVIEW
INSERT ALL
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (21,'BAD GAME IN HOME', TO_DATE('2001-10-03','YYYY-MM-DD'),'PLIK_01')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (22,'WHY', TO_DATE('2001-11-03','YYYY-MM-DD'),'PLIK_43')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (23,'LOVE TO DOG', TO_DATE('2002-01-09','YYYY-MM-DD'),'PLIK_45')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (24,'DOOM 3', TO_DATE('2002-01-12','YYYY-MM-DD'),'PLIK_03')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (25,'WHAT NEXT GAME', TO_DATE('2003-02-15','YYYY-MM-DD'),'PLIK_07')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (26,'PRISON?', TO_DATE('2003-08-20','YYYY-MM-DD'),'PLIK_91')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (27,'SMALL ENEMY', TO_DATE('2004-07-18','YYYY-MM-DD'),'PLIK_85')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (28,'BIG ENEMY', TO_DATE('2004-03-07','YYYY-MM-DD'),'PLIK_55')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (29,'HEROES IN OUR HEART', TO_DATE('2005-06-09','YYYY-MM-DD'),'PLIK_12')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (210,'SAM 2', TO_DATE('2005-11-11','YYYY-MM-DD'),'PLIK_36')
INTO REVIEW (KOD_REVIEW,TITLE,DATE_WRITED,BODY_REVIEW)
VALUES (211,'RESIDENT EVIL 4', TO_DATE('2002-10-02','YYYY-MM-DD'),'PLIK_82')
SELECT * FROM DUAL;

--Games
INSERT ALL
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (31,TO_DATE('2001-10-03','YYYY-MM-DD'), 'BAD HOME', 'Action')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (32,TO_DATE('2004-10-08','YYYY-MM-DD'), 'Doom 3', 'Gore')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (33,TO_DATE('2000-03-03','YYYY-MM-DD'), 'Happy evil', 'Adventure')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (34,TO_DATE('2001-10-04','YYYY-MM-DD'), 'Mood', 'Action')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (35,TO_DATE('2002-08-15','YYYY-MM-DD'), 'Mortal Kombat', 'Fighting')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (36,null, 'HHHHim', 'Action')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (37,null, 'Puzzle', 'Puzzle')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (38,TO_DATE('2004-09-21','YYYY-MM-DD'), 'The hare', 'Adventure')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (39,TO_DATE('2003-10-30','YYYY-MM-DD'), 'Elil', 'Action')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (310,TO_DATE('2005-10-03','YYYY-MM-DD'), 'grom', 'RPG')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (311,TO_DATE('2002-11-13','YYYY-MM-DD'), 'SAM 2', 'Action')
INTO GAMES (IDGAME,REDATE,NAGAME,GENRE)
VALUES (312,TO_DATE('2003-04-23','YYYY-MM-DD'), 'RESIDENT EVIL 4', 'Adventure')
SELECT * FROM DUAL;

--COMPANY
INSERT ALL
INTO COMPANY (ID_COMPANY, NAME,CAPITALIZATION,LOCATION)
VALUES (41,'Sony',13.2,'Minato')
INTO COMPANY (ID_COMPANY, NAME,CAPITALIZATION,LOCATION)
VALUES (42,'Ubisoft',0.633,'Montrei')
INTO COMPANY (ID_COMPANY, NAME,CAPITALIZATION,LOCATION)
VALUES (43,'EA',3.122,'California')
INTO COMPANY (ID_COMPANY, NAME,CAPITALIZATION,LOCATION)
VALUES (44,'Sega',5.152,'Tokyo')
INTO COMPANY (ID_COMPANY, NAME,CAPITALIZATION,LOCATION)
VALUES (45,'Activision',6.424,'California')
INTO COMPANY (ID_COMPANY, NAME,CAPITALIZATION,LOCATION)
VALUES (46,'PopKey',0.152,'Warszaw')
INTO COMPANY (ID_COMPANY, NAME,CAPITALIZATION,LOCATION)
VALUES (47,'RoseBlack',0.531,'Minato')
SELECT * FROM DUAL;


--GAMECOMM
INSERT ALL 
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (31,41)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (31,46)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (32,42)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (33,43)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (34,41)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (34,47)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (35,45)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (36,47)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (36,46)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (37,42)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (37,46)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (37,47)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (38,41)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (39,42)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (39,43)
INTO GAMECOMM (ID_GAME,ID_COMPANY)
VALUES (310,41)
SELECT * FROM DUAL;

--GAMEREV
INSERT ALL 
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (21,31)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (21,33)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (22,36)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (22,37)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (22,38)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (22,33)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (23,33)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (24,32)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (24,310)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (25,35)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (25,39)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (25,34)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (26,37)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (27,32)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (27,31)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (27,38)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (27,39)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (27,310)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (28,311)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (28,32)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (210,311)
INTO GAMEREV (KOD_REVIEW,ID_GAME)
VALUES (211,312)
SELECT * FROM DUAL;

--REVWRITER
INSERT ALL
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (21,11)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (21,12)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (22,15)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (22,14)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (22,13)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (23,13)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (24,11)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (25,12)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (25,13)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (26,14)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (26,15)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (27,12)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (28,11)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (28,13)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (29,11)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (29,12)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (29,13)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (29,14)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (29,15)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (210,12)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (210,11)
INTO REVWRITER (KOD_REVIEW,ID_WRITER)
VALUES (211,15)
SELECT * FROM DUAL;
