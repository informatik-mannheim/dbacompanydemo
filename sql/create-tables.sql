/* 
Achtung: Das DB-Schema firmenwelt muss leer sein, d.h. 
keine Tabellen etc. enthalten.
*/
use firmenwelt;

/*
Wir verwenden Constraints wie z.B. foreign-key. Das bedeutet aber:
die Reihenfolge des Anlegens bzw. Löschens ist wichtig.
*/

/*
a)	Auto (modell, marke)
b)	Firmenwagen (nummernschild, modell#, personalNr#)
c)	Ort (plz, ortsname)
d)	Mitarbeiter (personalNr, vorname, nachname, strasse, hausNr, plz#, gebDatum, abteilungsNr#, funktion, vorgesetzterNr#)
e)	Arbeiter (personalNr, arbeitsplatz)
f)	Angestellter (personalNr, telefonNr)
g)	Abteilung (abteilungsNr, bezeichnung)
h)	Projekt (projektNr, bezeichnung)
i)	Statusbericht (projektNr#, fortlaufendeNr, datum, inhalt)
j)	MitarbeiterArbeitetAnProjekt (personalNr#, projektNr#, prozAnteil, taetigkeit)
*/

-- Ort (plz, ortsname)
create table Ort (
plz char(5) primary key not null,
ortsname varchar(20)
);

-- Abteilung (abteilungsNr, bezeichnung)
create table Abteilung (
abteilungsNr integer not null auto_increment,
bezeichnung varchar(30),
primary key (abteilungsNr)
);

-- Mitarbeiter (personalNr, vorname, nachname, straße, hausNr, plz#, gebDatum, abteilungsNr#, funktion, vorgesetzterNr#)
create table Mitarbeiter (
personalNr integer not null auto_increment,
vorname varchar(30),
nachname varchar(30),
strasse varchar(30),
hausNr varchar(5),
plz char(5),
gebDatum date,
abteilungsNr integer,
funktion varchar(30), -- innerhalb der Abteilung
vorgesetzterNr integer,
primary key (personalNr),
foreign key (vorgesetzterNr) references Mitarbeiter (personalNr),
foreign key (abteilungsNr) references Abteilung(abteilungsNr),
foreign key (plz) references Ort(plz)
);
create index nameIdx on Mitarbeiter(nachname);

-- Angestellter (personalNr#, telefonNr)
create table Angestellter (
personalNr integer primary key not null,
telefonNr varchar(20),
foreign key (personalNr) references Mitarbeiter (personalNr)
  on update cascade on delete cascade
);

-- Arbeiter (personalNr#, arbeitsplatz)
create table Arbeiter (
personalNr integer primary key not null,
arbeitsplatz varchar(20),
foreign key (personalNr) references Mitarbeiter (personalNr)
  on update cascade on delete cascade
);

-- Auto (modell, marke)
create table Auto (
modell varchar(20) primary key not null,
marke varchar(20)
);

-- Firmenwagen (nummernschild, modell#, personalNr#)
create table Firmenwagen (
nummernschild varchar(12) primary key not null,
modell varchar(20),
personalNr integer,
foreign key (modell) references Auto(modell),
foreign key (personalNr) references Mitarbeiter(personalNr)
);

-- Projekt (projektNr, bezeichnung)
create table Projekt (
projektNr integer primary key not null,
bezeichnung varchar(30)
);

-- Statusbericht (projektNr#, fortlaufendeNr, datum, inhalt)
create table Statusbericht (
projektNr integer not null,
fortlaufendeNr integer not null,
datum date,
inhalt text,
primary key (projektNr, fortlaufendeNr),
foreign key (projektNr) references Projekt(projektNr)
);

-- MitarbeiterArbeitetAnProjekt (personalNr#, projektNr#, prozAnteil, taetigkeit)
create table MitarbeiterArbeitetAnProjekt (
personalNr integer not null,
projektNr integer not null,
taetigkeit varchar(255),
prozAnteil decimal(5, 2), -- 5 Zahlen insgesamt, davon 2 Nachkommastellen (000,00)
primary key (personalNr, projektNr),
foreign key (personalNr) references Mitarbeiter(personalNr),
foreign key (projektNr) references Projekt(projektNr)
);

/*
Wir benutzen die Auto-Inkrement-Funktion hier absichtlich nicht, damit
wir die Referenzen einfacher nachverfolgen können.
*/

-- Abteilung (abteilungsNr, bezeichnung)
insert into Abteilung values (1, 'Personal');
insert into Abteilung values (2, 'Einkauf');
insert into Abteilung values (3, 'Verkauf');
insert into Abteilung values (4, 'Abt_Verwaltung');
insert into Abteilung values (5, 'Entwicklung');
insert into Abteilung values (6, 'Produktion');
insert into Abteilung values (7, 'Vorstand');
-- author Patrick Sturm:
insert into Abteilung values (8, 'IT-Abteilung');
insert into Abteilung values (9, 'Buchhaltung');
insert into Abteilung values (10, 'Marketing');
insert into Abteilung values (11, 'Kundendienst');
insert into Abteilung values (12, 'Forschung');
insert into Abteilung values (13, 'Logistik');
insert into Abteilung values (14, 'Materialwirtschaft');
insert into Abteilung values (15, 'PR-Abteilung');
insert into Abteilung values (16, 'Qualitätssicherung');

-- Ort (plz, ortsname)
insert into Ort values
('68123', 'Mannheim-Neckarau'),
('69214', 'Eppelheim'),
('69123', 'Heidelberg'),
('68199', 'Mannheim'),
('80331', 'München'),
('01067', 'Dresden'),
('70173', 'Stuttgart'),
('76133', 'Karlsruhe'),
-- author Patrick Sturm:
('60311', 'Frankfurt am Main'),
('50441', 'Köln'),
('40210', 'Düsseldorf'),
('44135', 'Dortmund'),
('90402', 'Nürnberg'),
('89073', 'Ulm'),
('10437', 'Berlin'),
('14401', 'Potsdam'),
('20001', 'Hamburg'),
('45001', 'Essen'),
('28001', 'Bremen'),
('30159', 'Hannover'),
('04003', 'Leipzig'),
('47001', 'Duisburg'),
('44701', 'Bochum'),
('42001', 'Wuppertal');

-- Mitarbeiter (personalNr, vorname, nachname, strasse, hausNr, plz#, gebDatum, abteilungsNr#, funktion, vorgesetzterNr#)
-- Angestellter (personalNr#, telefonNr)
-- Arbeiter (personalNr#, Arbeitsplatz)
insert into Mitarbeiter values (1, 'Fransika', 'Lohe', 'Sonstigestr.', '1a', '69214', '1974-12-01', 7, 'Vorstandschefin', null);
insert into Angestellter values (1, '+49 621 12345-123');

insert into Mitarbeiter values (2, 'Sophia', 'Lorenz', 'Nirgendwostr.', '1a', '69214', '1974-12-01', 1, 'Leiterin', 1);
insert into Angestellter values (2, '+49 621 12345-225');

insert into Mitarbeiter values (3, 'Walter', 'Müller', 'Straße 1', '1a', '69123', '1949-02-11', 6, 'Produktionsleiter', 1);
insert into Angestellter values (3, '+49 621 12345-126');

insert into Mitarbeiter values (4, 'August', 'Kleinschmidt', 'Überstr.', '1a', '69123', '1949-02-11', 6, 'Produktion', 3);
insert into Arbeiter values (4, 'Platz 23');

insert into Mitarbeiter values
(5, 'Tatjana', 'Hohl', 'Mausloch Käsereistr.', '5', '80331', '1950-04-16', 2, 'Leiterin', 1),
(6, 'Theodor', 'Willschrei', 'Katzbergen Ahornweg', '4', '01067', '1969-05-19', 2, 'MItarbeiter', 2),
(7, 'Hans', 'Richter', 'Katzenhausen Buchweg', '28', '70173', '1973-06-20', 5, 'Leiter', 3),
(8, 'Brunhilde', 'Wiesenland', 'Hundsberg Mopsstr.', '45', '70173', '1966-01-05', 6, 'Mitarbeiterin', 3),
(9, 'Günther', 'Maier', 'Katzendorf Hopsstr.', '4', '01067', '1980-05-06', null, 'freier Employee', null);

-- author Patrick Sturm:
insert into Mitarbeiter values (10, 'Peter', 'Zieger', 'Ulmenweg', '34', '47001', '1967-01-13', 8, 'CIO', 1);
insert into Angestellter values (10, '+49621 12345-431');

insert into Mitarbeiter values (11, 'Julia', 'Link', 'Langestr.', '2', '14401', '1971-11-09', 12, 'Forschungsleiterin', 1);
insert into Angestellter values (11, '+49 621 12345-378');

insert into Mitarbeiter values (12, 'Franz-Dieter', 'Obermaier', 'Vogelweg', '17', '50441', '1976-05-28', 13, 'Leiter der Logistik', 1);
insert into Angestellter values (12, '+49 621 12345-278');

insert into Mitarbeiter values (13, 'Sven', 'Lange', 'Hauptstr.', '298', '20001', '1983-04-02', 13, 'Logistik-Mitarbeiter', 12);
insert into Arbeiter values (13, '+49 621 12345-654');

insert into Mitarbeiter values (14, 'Bruno', 'Maier', 'Distelweg', '4', '69123', '1960-04-02', 13, 'Logistik-Mitarbeiter', 12);
insert into Arbeiter values (14, '+49 621 12345-491');

insert into Mitarbeiter values (15, 'Paula', 'Schulze', 'Gartenstr.', '40b', '89073', '1984-09-05', 13, 'Leiterin der Buchhaltung', 1);
insert into Angestellter values (15, '+49 621 12345-456');

insert into Mitarbeiter values (16, 'Mike', 'Rosoft', 'Luisenstr.', '46', '89073', '1980-10-30', 16, 'Leiter der Qualitätssicherung', 1);
insert into Angestellter values (16, '+49 621 12345-956');

insert into Mitarbeiter values (17, 'Karla', 'Jürgens', 'Paradestr.', '17', '44135', '1972-11-13', 16, 'Qualitätsbeauftragte', 16);
insert into Angestellter values (17, '+49 621 12345-956');

insert into Mitarbeiter values (18, 'Jan', 'Hambsch', 'Ostenstr.', '1', '80331', '1981-11-13', 16, 'Qualitätsbeauftragter', 16);
insert into Angestellter values (18, '+49 621 12345-926');

insert into Mitarbeiter values (19, 'Franziska', 'Rothardt', 'Kirchengasse', '10', '42001', '1982-01-03', 13, 'Sachbearbeiterin Buchhaltung', 15);
insert into Angestellter values (19, '+49 621 12345-933');

insert into Mitarbeiter values (20, 'Daniel', 'Bjorg', 'Kapellenstr.', '98', '45001', '1983-11-11', 13, 'Sachbearbeiter Buchhaltung', 15);
insert into Angestellter values (20, '+49 621 12345-933');

insert into Mitarbeiter values (21, 'Sabrina', 'Paulsen', 'Kolpingstr.', '112', '89073', '1978-10-15', 14, 'Leiterin Materialwirtschaft', 1);
insert into Angestellter values (21, '+49 621 12345-955');

insert into Mitarbeiter values (22, 'Janine', 'Siegel', 'Pappelallee', '65', '10437', '1976-09-10', 14, 'Lageristin', 21);
insert into Arbeiter values (22, '+49 621 1234-792');

insert into Mitarbeiter values (23, 'Pascal', 'Maier-Franz', 'Distelstr.', '5', '47001', '1981-11-02', 14, 'Lagerist', 21);
insert into Arbeiter values (23, '+49 621 1234-618');

insert into Mitarbeiter values (24, 'Fritz', 'Hummel', 'Birkenweg', '51', '44701', '1961-01-24', 15, 'Leiter der PR-Abteilung', 1);
insert into Angestellter values (24, '+49 621 1234-728');

insert into Mitarbeiter values (25, 'Michaela', 'Vogel', 'Rheinstr.', '199', '69123', '1982-12-03', 15, 'PR-Consultant', NULL);
insert into Angestellter values (25, '+49 621 1234-729');

insert into Mitarbeiter values (26, 'Dieter', 'Müller', 'Birkenstr.', '71', '69123', '1969-03-20', 8, 'IT-Consultant', NULL);
insert into Angestellter values (26, '+49 621 1234-788');

-- Auto (modell, marke)
insert into Auto values ('Touran', 'VW');
insert into Auto values ('S-Klasse', 'Mercedes-Benz');
-- author Patrick Sturm:
insert into Auto values ('A-Klasse', 'Mercedes-Benz');
insert into Auto values ('B-Klasse', 'Mercedes-Benz');
insert into Auto values ('C-Klasse', 'Mercedes-Benz');
insert into Auto values ('E-Klasse', 'Mercedes-Benz');
insert into Auto values ('Sprinter', 'Mercedes-Benz');
insert into Auto values ('A3', 'Audi');
insert into Auto values ('A4', 'Audi');
insert into Auto values ('A6', 'Audi');
insert into Auto values ('A8', 'Audi');
insert into Auto values ('Golf', 'VW');
insert into Auto values ('Jetta', 'VW');
insert into Auto values ('Tiguan', 'VW');
insert into Auto values ('Passat', 'VW');
insert into Auto values ('Sharan', 'VW');
insert into Auto values ('Phaeton', 'VW');

-- Firmenwagen (nummernschild, modell#, personalNr#)
insert into Firmenwagen values ('MA-MA 100', 'S-Klasse', 1);
insert into Firmenwagen values ('MA-MA 102', 'Touran', 2);
-- Angestellter Nr. 3 hat aktuell keinen Firmenwagen.
insert into Firmenwagen values ('MA-MA 103', 'A-Klasse', 10);
insert into Firmenwagen values ('MA-MA 104', 'B-Klasse', 11);
insert into Firmenwagen values ('MA-MA 105', 'C-Klasse', 12);
insert into Firmenwagen values ('MA-MA 106', 'E-Klasse', 15);
insert into Firmenwagen values ('MA-MA 107', 'Sprinter', 16);
insert into Firmenwagen values ('MA-MA 108', 'A3', 19);
insert into Firmenwagen values ('MA-MA 109', 'A4', 20);
insert into Firmenwagen values ('MA-MA 110', 'A6', 21);
insert into Firmenwagen values ('MA-MA 111', 'A8', 24);
insert into Firmenwagen values ('MA-MA 112', 'Golf', 25);
insert into Firmenwagen values ('MA-MA 113', 'Jetta', 26);
/*
-- Diese Mitarbeiter sind keine Angestellten!
insert into Firmenwagen values ('MA-MA 114', 'Tiguan', 14);
insert into Firmenwagen values ('MA-MA 115', 'Passat', 15);
insert into Firmenwagen values ('MA-MA 116', 'Sharan', 16);
insert into Firmenwagen values ('MA-MA 117', 'Phaeton', 17);
*/

-- Projekt (projektNr, bezeichnung)
insert into Projekt values
(1, 'Kundenumfrage'),
(2, 'Verkaufsmesse'),
(3, 'Leute einstellen'),
(4, 'DB portieren'),
-- author Patrick Sturm:
(5, 'Prozessoptimierung');

-- MitarbeiterArbeitetAnProjekt (personalNr#, projektNr#, prozAnteil, taetigkeit)
insert into MitarbeiterArbeitetAnProjekt values
(1, 3, 'erstellt Kundenprofil', 25.50), -- Sophia Lorenz, Personal
(2, 2, 'Ausstellungsstücke einkaufen', 10.00), -- Tatjana Hohl, Einkauf
(3, 2, 'Ausstellungsstücke einkaufen', 100.00), -- Theodor Willschrei, Einkauf
(4, 3, 'telefonieren', 20.00), -- Hans Richter, Verkauf
(5, 2, 'ist Verkäufer', 70.00), -- Brunhilde Wiesenland, Verkauf
(5, 3, 'erstellt Statistiken', 30.00),
(6, 2, 'ist Aushilfe', 100.00),
-- author Patrick Sturm:
(10, 5, 'erfasst IT-Prozesse, führt neue Prozessabläufe ein', 25.00), -- Peter Zieger, IT-Abteilung
(26, 5, 'optimiert IT-Prozesse', 100.00); -- Dieter Müller, IT-Abteilung

-- Einige Views für die Programmierung

-- Wird für das Laden von Mitarbeitern benötigt:firmenwelt.personnel
create view Personnel as
select m.*, o.ortsname, w.personalNr as wPersNr, w.arbeitsplatz, a.personalNr as aPersNr, a.telefonNr
from Mitarbeiter m left outer join Arbeiter w on m.personalNr = w.personalNr
left outer join Angestellter a on m.personalNr = a.personalNr natural join Ort o;