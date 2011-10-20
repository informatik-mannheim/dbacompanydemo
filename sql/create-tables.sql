use firmenwelt;

-- Zuerst werden alle Tabellen gelöscht
drop table if exists Statusbericht, MitarbeiterArbeitetAnProjekt, Projekt;
drop table if exists Firmenwagen, Auto; 
drop table if exists Angestellter, Arbeiter, Mitarbeiter;
drop table if exists Ort, Abteilung;
drop view if exists Personnel;

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
foreign key (personalNr) references Angestellter(personalNr)
);

-- Projekt (projektNr, bezeichnung)
create table Projekt (
projektId char(3) primary key not null,
bezeichnung varchar(30),
naechsteStatusNummer integer not null -- wg. zusammengesetzen Schlüssel ProjNr, StatusNr
);
create index bezeichnungIdx on Projekt(bezeichnung);

-- Statusbericht (projektId#, fortlaufendeNr, datum, inhalt)
create table Statusbericht (
projektId char(3) not null,
fortlaufendeNr integer not null,
datum date,
inhalt text,
primary key (projektId, fortlaufendeNr),
foreign key (projektId) references Projekt(projektId)
);

-- MitarbeiterArbeitetAnProjekt (personalNr#, projektId#, prozAnteil, taetigkeit)
create table MitarbeiterArbeitetAnProjekt (
personalNr integer not null,
projektId char(3) not null,
taetigkeit varchar(255),
prozAnteil decimal(5, 2), -- 5 Zahlen insgesamt, davon 2 Nachkommastellen (000,00)
primary key (personalNr, projektId),
foreign key (personalNr) references Mitarbeiter(personalNr),
foreign key (projektId) references Projekt(projektId)
);

-- Einige Views für die Programmierung

-- Wird für das Laden von Mitarbeitern benötigt:
create view Personnel as
select m.*, o.ortsname, w.personalNr as wPersNr, w.arbeitsplatz, a.personalNr as aPersNr, a.telefonNr
from Mitarbeiter m left outer join Arbeiter w on m.personalNr = w.personalNr
left outer join Angestellter a on m.personalNr = a.personalNr natural join Ort o;