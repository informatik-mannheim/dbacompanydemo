use firmenwelt;

/*
Hinweise:

Wir benutzen die Auto-Inkrement-Funktion hier absichtlich nicht, damit
wir die Referenzen einfacher nachverfolgen können.

Die gleichen Daten können auch mit der Anwendung
net.gumbix.dba.companydemo.test.ExampleData erzeugt werden.
*/

-- Abteilung (abteilungsNr, bezeichnung)
insert into Abteilung
values (1, 'Management'),
       (2, 'Personalverwaltung'),
       (3, 'Einkauf'),
       (4, 'Verkauf & Marketing'),
       (5, 'IT'),
       (6, 'Forschung & Entwicklung'),
       (7, 'Produktion'),
       (8, 'Qualitätssicherung'),
       (9, 'Buchhaltung'),
       (10, 'Kundendienst');

-- Ort (plz, ortsname)
insert into Ort
values ('10437', 'Berlin'),
       ('12487', 'Berlin'),
       ('68113', 'Mannheim'),
       ('68163', 'Mannheim'),
       ('68305', 'Mannheim'),
       ('68309', 'Mannheim'),
       ('69115', 'Heidelberg'),
       ('69214', 'Eppelheim');

-- Mitarbeiter (personalNr, vorname, nachname, strasse, hausNr, plz#, gebDatum, abteilungsNr#, funktion, vorgesetzterNr#)
-- Angestellter (personalNr#, telefonNr)
-- Arbeiter (personalNr#, Arbeitsplatz)
insert into Mitarbeiter
values (1, 'Fransiska', 'Lohe', 'ChefstraÃŸe', '1a', '68305', '1968-01-01 00:00:00', 1, 'Vorstand', null),
       (2, 'Hans', 'Lindemann', 'Pappelallee', '1a', '10437', '1968-02-21 00:00:00', 1, 'Personalreferent', 1),
       (3, 'Walter', 'Müller', 'Flussweg', '23', '68113', '1949-03-11 00:00:00', 7, 'Produktionsleiter', 1),
       (4, 'August', 'Kleinschmidt', 'WasserturmstraÃŸe', '29', '69214', '1955-08-23 00:00:00', 7, 'NachfÃ¼ller', 3),
       (5, 'Peter', 'Ziegler', 'Wasserweg', '4', '69115', '1961-12-15 00:00:00', 7, 'AuffÃ¼ller', 3),
       (6, 'Hanna', 'Schmidt', 'Wasserweg', '16', '69115', '1974-11-29 00:00:00', 7, 'AuffÃ¼ller', 3),
       (7, 'Justin', 'Albrecht', 'Liesgewann', '6', '69115', '1991-10-09 00:00:00', 7, 'Azubi', 6),
       (8, 'Peter', 'Ziegler', 'Ulmenweg', '34', '69115', '1967-02-13 00:00:00', 5, 'IT-Leiter', 1),
       (9, 'Thomas', 'Bauer', 'DorfstraÃŸe', '1a', '68309', '1980-03-24 00:00:00', 5, 'Sys.-Admin', 8),
       (10, 'Jan', 'Fischer, Dr.', 'Untere StraÃŸe', '2', '68163', '1968-05-10 00:00:00', 6, 'F&E_Leiter', 1),
       (11, 'Sabrina', 'Walther, Dr.', 'Hansaweg', '22', '68163', '1978-08-16 00:00:00', 6, 'CAD-Experte', 10),
       (12, 'Max', 'Thorn', 'HauptstraÃŸe', '110a', '68163', '1956-03-01 00:00:00', 6, 'Physiker', 10),
       (13, 'Bernd', 'Gänzler', 'Hauptstraße', '110b', '68163', '1964-02-05 00:00:00', 3, 'EinkÃ¤ufer', 1),
       (14, 'Gabi', 'Richter', 'Ahornweg', '2', '68163', '1970-07-06 00:00:00', 4, 'Verkaufsleitung', 1),
       (15, 'Marcus', 'Reinhard', 'Hauptstraße', '11', '68163', '1973-06-20 00:00:00', 4, 'Vertriebler', 14),
       (16, 'Paul', 'Uhl', 'Langestraße', '1', '68163', '1978-05-20 00:00:00', 4, 'Vertriebler', 14),
       (17, 'Gisela', 'Weiß', 'Unter den Linden', '141', '12487', '1959-09-10 00:00:00', null, 'Beraterin', 1);

insert into Arbeiter
values (4, 'Halle A/Platz 30'),
       (5, 'Halle A/Platz 31'),
       (6, 'Halle A/Platz 32'),
       (7, 'Halle A/Platz 33');

insert into Angestellter
values (1, '+49 621 12345-100'),
       (2, '+49 621 12345-110'),
       (3, '+49 621 12345-200'),
       (8, '+49 621 12345-300'),
       (9, '+49 621 12345-310'),
       (10, '+49 621 12345-400'),
       (11, '+49 621 12345-410'),
       (12, '+49 621 12345-420'),
       (13, '+49 621 12345-600'),
       (14, '+49 621 12345-500'),
       (15, '+49 621 12345-510'),
       (16, '+49 621 12345-520'),
       (17, '+49 621 12345-599');

-- Auto (modell, marke)
insert into Auto
values ('Passat', 'VW'), ('S-Klasse', 'Mercedes'), ('Touran', 'VW');

-- Firmenwagen (nummernschild, modell#, personalNr#)
insert into Firmenwagen
values ('MA-MA 1234', 'S-Klasse', 1),
       ('MA-MA 1235', 'Passat', 3),
       ('MA-MA 1236', 'Touran', 10),
       ('MA-MA 1237', 'Passat', 14),
       ('MA-MA 1238', 'Touran', null);

-- Projekt (projektNr, bezeichnung)
insert into Projekt
values ('DBP', 'DB portieren.', 2),
       ('FOP', 'Neues Produkt entwickeln.', 6),
       ('LES', 'Personal einstellen.', 7),
       ('SEC', 'Security-Konzept für Firma', 2);

-- MitarbeiterArbeitetAnProjekt (personalNr#, projektNr#, prozAnteil, taetigkeit)
insert into MitarbeiterArbeitetAnProjekt
values (1, 'LES', 'Verträge ausstellen.', 10),
       (8, 'DBP', 'Architektur entwerfen.', 20),
       (8, 'SEC', 'Security-Konzept entwerfen.', 40),
       (9, 'DBP', 'Skripte schreiben.', 70),
       (9, 'SEC', 'Hacking', 40),
       (11, 'FOP', 'Modelle entwerfen.', 50),
       (12, 'FOP', 'Thermodynamik berechnen.', 100);

insert into Statusbericht
values ('FOP', 3, '2012-09-17 00:00:00', 'Das ist der erste Statusbericht'),
       ('FOP', 5, '2012-09-28 00:00:00', 'Fortschritte beim Modell'),
       ('LES', 4, '2011-11-17 00:00:00', 'Das ist der erste Statusbericht'),
       ('LES', 6, '2011-11-18 00:00:00', 'Das ist noch ein Statusbericht');