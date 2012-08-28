/*
Abfragen zum Ausprobieren. 
In der MySQL Workbench z.B. einfach den Cursor auf die Abfrage bewegen und CTRL-RETURN drücken.
Nicht vergessen, zuvor die Datenbank firmenwelt auszuwählen.
*/
use firmenwelt;

-- ####################
-- I. Einfache Abfragen
-- ####################

-- I.1
-- Liste alle Mitarbeiter auf:
select * from Mitarbeiter;

-- I.2
-- Liste den Nachnnamen und Vornamen aller Mitarbeiter auf:
select nachname, vorname from Mitarbeiter;

-- I.3
-- wie I.2, die Liste soll zusätzlich alphabetisch nach Nachname, 
-- dann nach Vorname aufsteigend sortiert auf:
select nachname, vorname from Mitarbeiter
order by nachname, vorname;

-- I.4
-- wie I.3, die Spalten sollen nun 'Name' und 'Vorname' heißen :
select nachname as 'Name', vorname as 'Vorname' from Mitarbeiter
order by nachname, vorname;

-- I.5
-- wie I.4, aber nur für Mitarbeiter, deren Nachname mit M anfängt:
select nachname as 'Name', vorname as 'Vorname' 
from Mitarbeiter
where nachname like 'M%'
order by nachname, vorname;

-- I.??
-- Liste alle Mitarbeiter auf, die einen Vorgesetzten haben:
select nachname as 'Name', vorname as 'Vorname' 
from Mitarbeiter 
where vorgesetzterNr is not null ;

-- ###########################
-- II. Anspruchsvolle Abfragen
-- ###########################

-- II.1
-- wie I.4, aber zeige nur Mitarbeiter, die einer Abteilung zugeordnet sind. Liste auch den Namen
-- der Abteilung auf:
select nachname as 'Name', vorname as 'Vorname', bezeichnung 
from Mitarbeiter natural join Abteilung
order by nachname, vorname;

-- Wer (Nachname, Vorname) arbeitet in der Abteilung "Produktion"? Die Liste
-- soll aufsteigend nach den Nachnamen sortiert sein.
select m.nachname, m.vorname 
from Mitarbeiter as m natural join Abteilung where Abteilung.bezeichnung = 'Produktion'
order by m.nachname;

-- #######################
-- III. Knifflige Abfragen
-- #######################

-- III.1
-- wie II.1, zeige jedoch stets alle Angestellten, auch wenn sie keiner Abteilung zugeordnet sind:
select nachname as 'Name', vorname as 'Vorname', a.bezeichnung as 'Abteilung'
from Mitarbeiter as m left outer join Abteilung as a on m.abteilungsNr = a.abteilungsNr
order by a.bezeichnung, m.nachname;

-- wie III.2, jedoch soll "- ohne -" und nicht null für eine nicht vorhandene Abteilung
-- ausgegeben werden:
select nachname, vorname, bezeichnung as Abteilung
from 
    (select nachname, vorname, bezeichnung
    from Mitarbeiter as m join Abteilung as a on m.abteilungsNr = a.abteilungsNr
    union
    select m.nachname, m.vorname, '- ohne -' as bezeichnung
    from Mitarbeiter as m
    where abteilungsNr is null
    ) as tmp
order by bezeichnung, nachname;

-- III.3
-- Zeige, welcher Mitarbeiter (Vorname, Nachname) was (macht) in welchem Projekt ('für Projekt')
-- mit wie viel % macht. Die Liste soll nach Nachnamen sortiert sein.
select Mitarbeiter.vorname, Mitarbeiter.nachname, MitarbeiterArbeitetAnProjekt.taetigkeit as macht,
Projekt.bezeichnung as 'für Projekt', MitarbeiterArbeitetAnProjekt.prozAnteil as "wieviel in %"
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt natural join Projekt
order by Mitarbeiter.nachname;

-- ########################
-- IV. Abgefahrene Abfragen
-- ########################

-- IV.1
-- Liste alle Mitarbeiter (Nachname, Vorname, Funktion) auf, die ein Chef sind:
select m2.nachname, m2.vorname, m2.funktion 
from Mitarbeiter m1 join Mitarbeiter m2 on m1.vorgesetzterNr = m2.personalNr
group by m2.personalNr, m2.nachname, m2.vorname, m2.funktion; 

-- IV.2
-- wie IV.1, gib zusätzlich ihre Abteilung an:
select m2.nachname, m2.vorname, m2.funktion, a.bezeichnung 
from Mitarbeiter m1 join Mitarbeiter m2 on m1.vorgesetzterNr = m2.personalNr 
join Abteilung a on m2.abteilungsNr = a.abteilungsNr
group by m2.personalNr, m2.nachname, m2.vorname, m2.funktion, a.bezeichnung; 

/*
IV.3
Gib an, wie viele Mitarbeiter jeweils an ihren Chef berichten.
*/
select m2.nachname, m2.vorname, count(m2.personalNr) as 'Teamgröße'
from Mitarbeiter m1 join Mitarbeiter m2 on m1.vorgesetzterNr = m2.personalNr
-- order by m2.nachname;
group by m2.personalNr, m2.nachname, m2.vorname; 

-- ab hier noch zu verifizieren...

-- Zeige, welcher Mitarbeiter was macht,
-- i.e. Name, welche T�tigkeit, in welchem Projekt mit wieviel %
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, MitarbeiterArbeitetAnProjekt.taetigkeit as macht,
 projekt.bezeichnung as 'für Projekt', MitarbeiterArbeitetAnProjekt.prozAnteil as "wieviel in %"
from MitarbeiterArbeitetAnProjekt natural join (Mitarbeiter, Projekt);

-- Wieviel % arbeit ein Employee an Projekten (sortiert nach Name)?
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, sum(prozAnteil) as '%'
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt group by Mitarbeiter.personalNr
order by Mitarbeiter;

-- Wer arbeitet 100% an Projekten (sortiert nach Name)?
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt group by Mitarbeiter.personalNr having sum(prozAnteil) = 100
order by Mitarbeiter;

-- Wer arbeitet weniger als 100% an Projekten (sortiert nach Name)?
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, sum(prozAnteil) as '%'
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt group by Mitarbeiter.personalNr 
having sum(prozAnteil) < 100
order by Mitarbeiter.nachname;

-- Zeige alle Employee und ihre Abteilungen, sofern sie in einer arbeiten:
-- Lösung ohne left outer join Befehl:
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter , Abteilung.bezeichnung as Abteilung
from Mitarbeiter, Abteilung
where Mitarbeiter.abteilungsNr = Abteilung.abteilungsNr
union
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Name, null as Abteilung
from Mitarbeiter
where abteilungsNr is null;



-- Temporäres Umbennen einer gesamten Tabelle:
select * from (select abteilungsNr as abteilungsNr2, bezeichnung as bezeichnung2 from Abteilung) as Mitarbeiter2;

-- Zeige alle Angestellten (PersonalNr und Name), 
-- die keinen Firmenwagen fahren:
select m.personalNr, m.nachname 
from Angestellter a left outer join Firmenwagen f 
on a.personalNr = f.personalNr 
join Mitarbeiter m on a.personalNr = m.personalNr
where f.personalNr is null;