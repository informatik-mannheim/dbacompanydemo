use firmenwelt;

/*
Hinweise:

Wir benutzen die Auto-Inkrement-Funktion hier absichtlich nicht, damit
wir die Referenzen einfacher nachverfolgen können.

Die gleichen Daten sollten (noch nicht der Fall) mit der Anwendung
net.gumbix.dba.companydemo.test.ExampleData erzeugt werden.
*/

-- Abteilung (abteilungsNr, bezeichnung)
insert into Abteilung values 
(1, 'Personal'),
(2, 'Einkauf'),
(3, 'Verkauf'),
(4, 'Abt_Verwaltung'),
(5, 'Entwicklung'),
(6, 'Produktion'),
(7, 'Vorstand'),
-- author Patrick Sturm:
(8, 'IT-Abteilung'),
(9, 'Buchhaltung'),
(10, 'Marketing'),
(11, 'Kundendienst'),
(12, 'Forschung'),
(13, 'Logistik'),
(14, 'Materialwirtschaft'),
(15, 'PR-Abteilung'),
(16, 'Qualitätssicherung');

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
(5, 'Tatjana', 'Hohl', 'Mausloch Käsereistr.', '5', '80331', '1950-04-16', 2, 'Leiterin', 1);
insert into Angestellter values (5, '+49 621 12345-262');

insert into Mitarbeiter values
(6, 'Theodor', 'Willschrei', 'Ahornweg', '4', '01067', '1969-05-19', 2, 'Mitarbeiter', 2);
insert into Angestellter values (6, '+49 621 12345-462');

insert into Mitarbeiter values
(7, 'Hans', 'Richter', 'Buchweg', '28', '70173', '1973-06-20', 5, 'Leiter', 3);
insert into Angestellter values (7, '+49 621 12345-862');

insert into Mitarbeiter values
(8, 'Brunhilde', 'Wiesenland', 'Mopsstr.', '45', '70173', '1966-01-05', 6, 'Mitarbeiterin', 3),
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
('KUM', 'Kundenumfrage', 1),
('VEM', 'Verkaufsmesse', 1),
('LES', 'Leute einstellen', 1),
('DBA', 'DB portieren', 1),
-- author Patrick Sturm:
('PRO', 'Prozessoptimierung', 1);

-- MitarbeiterArbeitetAnProjekt (personalNr#, projektNr#, prozAnteil, taetigkeit)
insert into MitarbeiterArbeitetAnProjekt values
(1, 'KUM', 'erstellt Kundenprofil', 25.50), -- Sophia Lorenz, Personal
(2, 'VEM', 'Ausstellungsstücke einkaufen', 10.00), -- Tatjana Hohl, Einkauf
(3, 'VEM', 'Ausstellungsstücke einkaufen', 100.00), -- Theodor Willschrei, Einkauf
(7, 'VEM', 'telefonieren', 20.00), -- Hans Richter, Verkauf
(5, 'VEM', 'ist Verkäufer', 70.00), -- Brunhilde Wiesenland, Verkauf
(5, 'KUM', 'erstellt Statistiken', 30.00),
(6, 'VEM', 'ist Aushilfe', 100.00),
-- author Patrick Sturm:
(10, 'PRO', 'erfasst IT-Prozesse, führt neue Prozessabläufe ein', 25.00), -- Peter Zieger, IT-Abteilung
(26, 'PRO', 'optimiert IT-Prozesse', 100.00); -- Dieter Müller, IT-Abteilung

insert into Statusbericht values 
('LES', 1, '2011-10-15', 'Das ist ein Statusbericht');