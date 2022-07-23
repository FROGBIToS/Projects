------------------------------PROCEDURES-------------------------------------
---------------------------------------------------------------------------------------1
CREATE OR REPLACE PROCEDURE GETINFOABOUTWRITER(IDWRITER INTEGER)
AS
    nameW VARCHAR2(50);
    surnameW VARCHAR2(50);
    numberW VARCHAR2(12);
    mailW VARCHAR2(50);
    kontaktW VARCHAR2(50);
    
    LASTREVIEWTITLE VARCHAR2(100);
    LASTREVIEWDATE DATE;
    LASTREVIEWID INTEGER;
    COUNTREVIEW INTEGER;
    ILEXIST INT;
    
    EXCEPTIONNOTFOUND EXCEPTION;
BEGIN
    SELECT COUNT(1) INTO ILEXIST FROM WRITER WHERE ID_WRITER = IDWRITER;
    
    IF (ILEXIST > 0) THEN
    
        SELECT MAX(DATE_WRITED) INTO LASTREVIEWDATE FROM REVIEW
                                WHERE KOD_REVIEW IN (SELECT KOD_REVIEW FROM REVWRITER WHERE ID_WRITER = IDWRITER);
        SELECT KOD_REVIEW INTO LASTREVIEWID FROM REVIEW 
                                WHERE KOD_REVIEW IN (SELECT KOD_REVIEW FROM REVWRITER WHERE ID_WRITER = IDWRITER)
                                AND DATE_WRITED = LASTREVIEWDATE;
        SELECT TITLE INTO LASTREVIEWTITLE FROM REVIEW WHERE KOD_REVIEW = LASTREVIEWID;                    
        SELECT COUNT(1) INTO COUNTREVIEW FROM REVWRITER WHERE ID_WRITER = IDWRITER;
        
        SELECT NUMPHONE INTO numberW FROM WRITER WHERE ID_WRITER = IDWRITER;
        SELECT MAIL INTO mailW FROM WRITER WHERE ID_WRITER = IDWRITER;
        SELECT NAWRITER INTO NAMEW FROM WRITER WHERE ID_WRITER = IDWRITER;
        SELECT SURNWRITER INTO SURNAMEW FROM WRITER WHERE ID_WRITER = IDWRITER;
        
        dbms_output.put_line(Length(NUMBERW));
        
        IF (Length(NUMBERW) > 0) THEN
            kontaktW := numberW;
        ELSIF (Length(mailW) > 0) THEN 
            kontaktW := mailW;
        ELSE
            kontaktW := '+12111222333';
        END IF;
        
        dbms_output.put_line('Who : ' || nameW || ' ' || surnameW);
        dbms_output.put_line('-------------------');
        dbms_output.put_line('Last review : ' || LASTREVIEWTITLE || '|' || LASTREVIEWDATE || ';' );
        dbms_output.put_line('How many he wrote : ' || COUNTREVIEW || ';');
        dbms_output.put_line('Kontakt : ' || kontaktW || ';');
        dbms_output.put_line('-------------------');
        
        ELSE 
            RAISE EXCEPTIONNOTFOUND;
    END IF;
    
    EXCEPTION 
    WHEN EXCEPTIONNOTFOUND THEN
        dbms_output.put_line('WRITER NOT FOUND');
END;

-----------------------------------------------------------------------------2
CREATE OR REPLACE PROCEDURE NUBERGAMESOFGRUPS(GROUPMEMBERS INTEGER)
AS
    NUMOFMATCHES INTEGER;
    GAMESMATCHED INTEGER := 0;
    P_IDGAME INTEGER;
    CURSOR GAMECURSOR IS SELECT IDGAME FROM GAMES;
    CURS GAMECURSOR%ROWTYPE;
BEGIN
    OPEN GAMECURSOR;
    LOOP
        FETCH GAMECURSOR INTO CURS;
        EXIT WHEN GAMECURSOR%NOTFOUND;
        
        SELECT COUNT(1) INTO NUMOFMATCHES FROM GAMECOMM WHERE ID_GAME = CURS.IDGAME;
        
        IF (NUMOFMATCHES = GROUPMEMBERS) THEN
            GAMESMATCHED := GAMESMATCHED+1;
        END IF;
        
    END LOOP;
    
    dbms_output.put_line('Games produced by '|| GROUPMEMBERS ||' companies -> ' || GAMESMATCHED);
    
END;

--------------------------------TRIGGERS-------------------------------
---------------------------------------------------------------------------------1


CREATE OR REPLACE TRIGGER DELETINGREVIEW
BEFORE DELETE ON REVIEW
FOR EACH ROW
DECLARE 
UNDELETINGDATE DATE := '01.01.02';
DATEGAME DATE;
BEGIN
    SELECT MAX(G.REDATE) INTO DATEGAME FROM GAMES G   
                                JOIN GAMEREV GR ON G.IDGAME = GR.ID_GAME
                                WHERE GR.KOD_REVIEW = :old.kod_review;
                                
    IF (DATEGAME > UNDELETINGDATE OR DATEGAME = NULL) THEN
        raise_application_error(-20325, 'You can delete reviews only for games released before ' || UNDELETINGDATE);
    END IF;
END;

CREATE OR REPLACE TRIGGER DELETINGREVIEWONREVWRITER
BEFORE DELETE ON REVWRITER
FOR EACH ROW
DECLARE 
UNDELETINGDATE DATE := '01.01.02';
DATEGAME DATE;
BEGIN
    SELECT MAX(G.REDATE) INTO DATEGAME FROM GAMES G   
                                JOIN GAMEREV GR ON G.IDGAME = GR.ID_GAME
                                WHERE GR.KOD_REVIEW = :old.kod_review;
                                
    IF (DATEGAME > UNDELETINGDATE OR DATEGAME = NULL) THEN
        raise_application_error(-20325, 'You can delete reviews only for games released before ' || UNDELETINGDATE);
    END IF;
END;

CREATE OR REPLACE TRIGGER DELETINGREVIEWONGAMEREV
BEFORE DELETE ON GAMEREV
FOR EACH ROW
DECLARE 
UNDELETINGDATE DATE := '01.01.02';
DATEGAME DATE;
BEGIN
    SELECT MAX(G.REDATE) INTO DATEGAME FROM GAMES G   
                                WHERE IDGAME = :old.ID_GAME;
                                
    IF (DATEGAME > UNDELETINGDATE OR DATEGAME = NULL) THEN
        raise_application_error(-20325, 'You can delete reviews only for games released before ' || UNDELETINGDATE);
    END IF;
END;

----------------------------------------------------------------------2
CREATE OR REPLACE TRIGGER ADDINGWRITER
BEFORE INSERT ON WRITER 
FOR EACH ROW
BEGIN
    IF (SUBSTR(:new.numphone,0,1) != '+') THEN
        :new.numphone := '+' || :new.numphone;
        IF (LENGTH(:new.numphone) < 12) THEN
            raise_application_error(-20325,'Wrong phone number');
        END IF;
    END IF;
END;