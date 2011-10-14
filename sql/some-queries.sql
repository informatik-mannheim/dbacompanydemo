-- Zeige, welcher Mitarbeiter was macht,
-- i.e. Name, welche T�tigkeit, in welchem Projekt mit wieviel %
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, MitarbeiterArbeitetAnProjekt.taetigkeit as macht,
 projekt.bezeichnung as für, MitarbeiterArbeitetAnProjekt.prozAnteil as "wieviel in %"
from MitarbeiterArbeitetAnProjekt natural join (Mitarbeiter, Projekt)

-- Wieviel % arbeit ein Employee an Projekten (sortiert nach Name)?
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, sum(prozAnteil) as '%'
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt group by Mitarbeiter.personalNr
order by Mitarbeiter

-- Wer arbeitet 100% an Projekten (sortiert nach Name)?
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt group by Mitarbeiter.personalNr having sum(prozAnteil) = 100
order by Mitarbeiter

-- Wer arbeitet weniger als 100% an Projekten (sortiert nach Name)?
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, sum(prozAnteil) as '%'
from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt group by Mitarbeiter.personalNr having sum(prozAnteil) < 100
order by Mitarbeiter

-- Wer arbeitet in der Department "Personal"?
select * from Mitarbeiter natural join Abteilung where Abteilung.bezeichnung = "Personal"

-- Zeige alle Employee und ihre Abteilungen, sofern sie in einer arbeiten:
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, Abteilung.bezeichnung as Abteilung
from Mitarbeiter left outer join Abteilung on Mitarbeiter.abteilungsNr = Abteilung.abteilungsNr

-- Zeige alle Employee und ihre Abteilungen, sofern sie in einer arbeiten:
-- Lösung ohne left outer join Befehl:
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter , Abteilung.bezeichnung as Abteilung
from Mitarbeiter, Abteilung
where Mitarbeiter.abteilungsNr = Abteilung.abteilungsNr
union
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Name, null as Abteilung
from Mitarbeiter
where abteilungsNr is null

-- Zeige alle Employee und ihre Abteilungen, sofern sie in einer arbeiten:
-- Gib "- ohne -" für die Department aus, wenn keine vorhanden.
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter , Abteilung.bezeichnung as Abteilung
from Mitarbeiter, Abteilung
where Mitarbeiter.abteilungsNr = Abteilung.abteilungsNr
union
select CONCAT_WS(' ',Mitarbeiter.vorname,Mitarbeiter.nachname) as Mitarbeiter, "- ohne -" as Abteilung
from Mitarbeiter
where abteilungsNr is null

-- Temporäres Umbennen einer gesamten Tabelle:
select * from (select abteilungsNr as abteilungsNr2, bezeichnung as bezeichnung2 from Abteilung) as Mitarbeiter2