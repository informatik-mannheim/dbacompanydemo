-- Anlegen eines neuen technischen Benutzers, der aus Java/Scala benutzt wird.
create user techuser1 identified by 'techuser1';
create user techuser2 identified by 'techuser2';

create view MitarbeiterVerkauf as
select * from Mitarbeiter where abteilungsId = 3;

-- Sales-Tool:
grant select (personalNr, nachname, vorname) 
on firmenwelt.MitarbeiterVerkauf to techuser1@'%';

-- Personalabteilungstool:
grant select on firmenwelt.Mitarbeiter to techuser2@'%'; 
grant update (nachname) on firmenwelt.Mitarbeiter to techuser2@'%'; 
