-- Anlegen eines neuen technischen Benutzers, der aus Java/Scala benutzt wird.
drop user if exists firmenwelt; -- falls Benutzer bereits vorhanden.
create user firmenwelt identified by 'firmenwelt10';
-- Anlegen eines Datenbankschemas für die Anwendung CompanyDemo:
drop database if exists firmenwelt;
create database if not exists firmenwelt;
-- Der technische Benutzer firmenwelt eingeschränkten Zugriff auf dieses DB-Schema:
grant insert, select, update, delete on firmenwelt.* to firmenwelt@'%';
-- Wenn Sie vollen Zugriff erlauben wollen, bitte dies ausführen:
-- grant all on firmenwelt.* to firmenwelt@'%';