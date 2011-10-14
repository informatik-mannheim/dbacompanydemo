-- Anlegen eines neuen technischen Benutzers, der aus Java/Scala benutzt wird.
drop user firmenwelt; -- falls Benutzer bereits vorhanden.
create user firmenwelt identified by 'firmenwelt10';
-- Anlegen eines Datenbankschemas für die Anwendung CompanyDemo:
drop database if exists firmenwelt;
create database if not exists firmenwelt;
-- Der technische Benutzer compandydemo erhält vollen Zugriff auf dieses DB-Schema:
grant all on firmenwelt.* to firmenwelt@'%'; 