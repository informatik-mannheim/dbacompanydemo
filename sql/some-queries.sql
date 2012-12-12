/*
Abfragen zum Ausprobieren. 
In der MySQL Workbench z.B. einfach den Cursor auf die Abfrage bewegen und CTRL-RETURN drücken.
Nicht vergessen, zuvor die Datenbank firmenwelt auszuwählen.
*/
use firmenwelt;

-- ####################
-- I. Einfache Abfragen
-- ####################

/*
I.1
Liste alle Mitarbeiter auf.
*/
select * from Mitarbeiter;

/*
I.2
Liste den Nachnnamen und Vornamen aller Mitarbeiter auf.
*/
select nachname, vorname from Mitarbeiter;

/*
I.3
wie I.2, die Liste soll zusätzlich alphabetisch nach Nachname, 
dann nach Vorname aufsteigend sortiert auf.
*/
select nachname, vorname from Mitarbeiter
order by nachname, vorname;

/*
I.4
wie I.3, die Spalten sollen nun 'Name' und 'Vorname' heißen.
*/
select nachname as 'Name', vorname as 'Vorname' from Mitarbeiter
order by nachname, vorname;

/*
I.5
wie I.4, aber nur für Mitarbeiter, deren Nachname mit M anfängt.
*/
select nachname as 'Name', vorname as 'Vorname' 
from Mitarbeiter
where nachname like 'M%'
order by nachname, vorname;

/*
I.??
Liste alle Mitarbeiter auf, die einen Vorgesetzten haben.
*/
select nachname as 'Name', vorname as 'Vorname' 
from Mitarbeiter 
where vorgesetzterNr is not null;

/*
I.??
Liste alle Mitarbeiter auf, die zwischen 20 und 30 Jahren alt sind. Gib den Nachnamen,
Vornamen und das aktuelle Alter in Jahren an.
*/
select nachname, vorname, gebDatum, datediff(curdate(), gebDatum) / 365.25 as 'Alter'
from Mitarbeiter
where datediff(curdate(), gebDatum) >= 20 * 365.25 
and datediff(curdate(), gebDatum) <= 30 * 365.25;

/*
I.??
Gib das Durchschnittsalter aller Mitarbeiter in Jahren an.
*/
select avg(datediff(curdate(), gebDatum) / 365.25) as 'Durchschnittsalter'
from Mitarbeiter;

/*
Gib das Durchschnittsgehalt aller Mitarbeiter aus.
*/
select avg(gehalt) as 'Durchschnittsgehalt'
from Mitarbeiter;

/*
Wer verdient 6000 € im Monat oder mehr? Das Ergebnis soll absteigend nach dem Gehalt
sortiert werden. Geben Sie den Vornamen, Namen und das Gehalt aus.
*/
select vorname, nachname, gehalt 
from Mitarbeiter
where gehalt >= 6000
order by gehalt desc;

/*
Wie viele Mitarbeiter verdienen weniger als 2000 € monatlich?
*/
select count(*) as 'Geringverdiener'
from Mitarbeiter
where gehalt < 2000;

-- ###########################
-- II. Anspruchsvolle Abfragen
-- ###########################

/*
II.1
wie I.4, aber zeige nur Mitarbeiter, die einer Abteilung zugeordnet sind. Liste auch den Namen
der Abteilung auf.
*/
select nachname as 'Name', vorname as 'Vorname', bezeichnung 
from Mitarbeiter m join Abteilung a on m.abteilungsId = a.abteilungsNr
order by nachname, vorname;

/*
II.??
Wer (Nachname, Vorname) arbeitet in der Abteilung "Produktion"? Die Liste
soll aufsteigend nach den Nachnamen sortiert sein.
*/
select m.nachname, m.vorname 
from Mitarbeiter m join Abteilung a on m.abteilungsId = a.abteilungsNr
where a.bezeichnung = 'Produktion'
order by m.nachname;

/* 
Wer arbeitet mehr als 100% an Projekten (sortiert nach Name)? 
*/
select concat_ws(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, sum(prozAnteil) as '%'
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt group by Mitarbeiter.personalNr 
having sum(prozAnteil) > 100
order by Mitarbeiter.nachname;

/*
Welche Abteilungen haben noch keine Mitarbeiter?
*/
select bezeichnung as 'leere Abteilung'
from Abteilung
where abteilungsNr not in (select abteilungsId from Mitarbeiter where abteilungsId is not null);

select a.bezeichnung as 'leere Abteilung'
from Abteilung a left outer join Mitarbeiter m on a.abteilungsNr = m.abteilungsId
where m.abteilungsId is null;

/*
Welcher Angestellten arbeiten aktuell an keinem Projekt?
*/
select m.nachname, m.vorname, m.funktion 
from Mitarbeiter m natural join Angestellter a 
where a.personalNr not in (select personalNr from MitarbeiterArbeitetAnProjekt)
order by m.nachname;

/**
Wie viele Personen (Fulltime Equivalents, FTE) arbeiten an den Projekten?
*/
select projektId as 'ID', p.bezeichnung as 'Projekt', sum(prozAnteil) / 100 as 'FTE'
from MitarbeiterArbeitetAnProjekt m natural join Projekt p
group by m.projektId;

/*
Wer fährt alle einen VW?
*/
select m.nachname, m.vorname, m.funktion, a.*
from Auto as a natural join Firmenwagen natural join Mitarbeiter as m
where a.marke = 'VW';

/*
In welchen Abteilungen werden welche Fahrzeuge gefahren?
*/
select a.bezeichnung, f.modell 
from Abteilung a natural join Mitarbeiter natural join Firmenwagen f
order by a.bezeichnung;


-- #######################
-- III. Knifflige Abfragen
-- #######################

/*
III.1
wie II.1, zeige jedoch stets alle Angestellten, auch wenn sie keiner Abteilung zugeordnet sind.
*/
select nachname as 'Name', vorname as 'Vorname', a.bezeichnung as 'Abteilung'
from Mitarbeiter as m left outer join Abteilung as a on m.abteilungsNr = a.abteilungsNr
order by a.bezeichnung, m.nachname;

/*
wie III.2, jedoch soll "- ohne -" und nicht null für eine nicht vorhandene Abteilung
ausgegeben werden.
*/
select nachname, vorname, bezeichnung as Abteilung
from 
    (select nachname, vorname, bezeichnung
    from Mitarbeiter as m join Abteilung as a on m.abteilungsId = a.abteilungsNr
    union
    select m.nachname, m.vorname, '- ohne -' as bezeichnung
    from Mitarbeiter as m
    where abteilungsId is null
    ) as tmp
order by bezeichnung, nachname;

/*
III.3
Zeige, welcher Mitarbeiter (Vorname, Nachname) was (macht) in welchem Projekt ('für Projekt')
mit wie viel % macht. Die Liste soll nach Nachnamen sortiert sein.
*/
select Mitarbeiter.vorname, Mitarbeiter.nachname, MitarbeiterArbeitetAnProjekt.taetigkeit as macht,
Projekt.bezeichnung as 'für Projekt', MitarbeiterArbeitetAnProjekt.prozAnteil as "wieviel in %"
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt natural join Projekt
order by Mitarbeiter.nachname;

/*
III.4
Wo wohnen die meisten Mitarbeiter (gemäß PLZ)? Liste hierzu die PLZ und die Häufigkeit
absteigend sortiert auf.
*/
select plz, anzahl
from
(
 select plz, count(plz) as anzahl from Mitarbeiter
 group by plz
) as t
order by anzahl desc;


-- ########################
-- IV. Abgefahrene Abfragen
-- ########################

/*
IV.1
Liste alle Mitarbeiter (Nachname, Vorname, Funktion) auf, die ein Chef sind.
*/
select m2.nachname, m2.vorname, m2.funktion 
from Mitarbeiter m1 join Mitarbeiter m2 on m1.vorgesetzterNr = m2.personalNr
group by m2.personalNr, m2.nachname, m2.vorname, m2.funktion; 

/*
IV.2
wie IV.1, gib zusätzlich ihre Abteilung an.
*/
select m2.nachname, m2.vorname, m2.funktion, a.bezeichnung 
from Mitarbeiter m1 join Mitarbeiter m2 on m1.vorgesetzterNr = m2.personalNr 
join Abteilung a on m2.abteilungsNr = a.abteilungsNr
group by m2.personalNr, m2.nachname, m2.vorname, m2.funktion, a.bezeichnung; 

/*
IV.3
Gib an, wie viele Mitarbeiter jeweils an ihren Chef berichten.
*/
select m2.nachname, m2.vorname, count(*) as 'Teamgröße'
from Mitarbeiter m1 join Mitarbeiter m2 
on m1.vorgesetzterNr = m2.personalNr
group by m2.personalNr, m2.nachname, m2.vorname
order by m2.nachname;

/**                             
IV.4
wie III.4, jedoch soll nur die PLZ mit der größten Häufigkeit ausgeben werden (d.h. nur eine Zeile).
*/
select plz
from
(
 select plz, count(plz) as anzahl from Mitarbeiter
 group by plz
) as t
where anzahl >= all 
(
 select count(plz) as anzahl from Mitarbeiter
 group by plz
);