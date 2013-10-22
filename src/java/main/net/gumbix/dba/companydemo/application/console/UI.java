/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011  the authors listed below.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package net.gumbix.dba.companydemo.application.console;

import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.db4o.Db4oAccess;
import net.gumbix.dba.companydemo.domain.*;
import net.gumbix.dba.companydemo.hibernate.HibernateDBAccess;
import net.gumbix.dba.companydemo.jdbc.JdbcAccess;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Patrick Sturm (patrick-sturm@gmx.net)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class UI {

    private static String NAME = "CompanyDemo";
    private static String VERSION = "0.2.2";
    private static String PROMPT = "Ihre Eingabe (Zahl): ";
    private static String INVALID_INPUT = "Fehlerhafte Eingabe \n\n";
    private static DBAccess db;

    public static void main(String[] args) {
        try {
            selectDB();
            System.out
                    .println("\nVielen Dank, dass Sie " + NAME + " genutzt haben.");
            System.out.println("Wir hoffen, es hat Spass gemacht.");
            credits();
            pressAnyKey();
        } catch (Exception e) {
            System.out.println("### Fehler verursacht: " + e.getMessage());
            e.printStackTrace();
            System.out.println("### Das Programm wird neu gestartet.\n\n");
            main(null); // Will result in a stack overflow some times...
        }
    }

    private static void selectDB() throws Exception {
        int menuChoice;

        do {
            System.out
                    .println("*** Willkommen zu " + NAME + " " + VERSION + " ***\n\n"
                            + "Welche Datenbankzugriffsart moechten sie nutzen? \n\n"
                            + "1 SQL-Datenbank der Hochschule Mannheim (Ueber JDBC) \n"
                            + "2 SQL-Datenbank der Hochschule Mannheim (Ueber Hibernate) \n"
                            + "3 eigene SQL-Datenbank (Ueber JDBC) \n"
                            + "4 eigene SQL-Datenbank (Ueber Hibernate) \n"
                            + "5 eigene DB4O-Datenbank (Servermode; vorher startDb4oServer.bat starten) \n"
                            + "6 lokale DB4O-Datenbank \n\n"
                            + "7 Benutzerbasierte Anmeldung an SQL-Datenbank der Hochschule Mannheim (Ueber JDBC) (EXPERIMENTAL) \n"
                            + "8 lokale Benutzerbasierte Anmeldung (Ueber JDBC) (EXPERIMENTAL)\n\n"
                            + "9 Credits \n\n"
                            + "0 Programm beenden");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                case 1:
                    db = new JdbcAccess();
                    menu();
                    break;

                case 2:
                    System.out.println("Noch nicht unterstuetzt.");
                    break;

                case 3:
                    db = new JdbcAccess("firmenwelt", "firmenwelt10");
                    menu();
                    break;

                case 4:
                    db = new HibernateDBAccess();
                    menu();
                    break;

                case 5:
                    db = new Db4oAccess("localhost", 8732, "firmenwelt",
                            "firmenwelt10");
                    menu();
                    break;

                case 6:
                    db = new Db4oAccess("firmenwelt.yap");
                    menu();
                    break;
                /*********Experimental*********/
                case 7:
                	try{
                		db = userLogin4DB("jdbc:mysql://codd.ki.hs-mannheim.de:3306/firmenwelt");
                	}catch(SQLException sqlExc){
                		System.out.println(sqlExc.getMessage());
                		break;
                	}catch(LoginException loginExp){
                		System.out.println(loginExp.getMessage());
                		break;
                	}
                	menu();
                	break;
                case 8:
                	try{
                		db = userLogin4DB("jdbc:mysql://localhost:3306/firmenwelt");
                	}catch(SQLException sqlExc){
                		System.out.println(sqlExc.getMessage());
                		break;
                	}catch(LoginException loginExp){
                		System.out.println(loginExp.getMessage());
                		break;
        			}
                	menu();
                	break;
                /*****************************/
                case 9:
                    credits();
                    pressAnyKey();
                    break;

                case 0:
                    System.out.println("Programm wird beendet.");
                    break;

                // Invalide Eingabe:
                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        } while (menuChoice != 0);
    }
    
    /*********Experimental*********/
    private static DBAccess userLogin4DB(String url) throws SQLException, LoginException, Exception {
    	if(System.console() == null){
    		throw new LoginException("\n\nBenutzerbasierte Anmeldung nicht möglich\n\n");
    	}
    	
    	DBAccess dbx = null;
    	System.out.print("Benutzer: ");
    	String user = getUserInputString(); 
    	String pwd = "";
    	System.out.print("Passwort (Eingabe ist unsichtbar): ");
    	pwd = new String(System.console().readPassword());
    	
    	dbx = new JdbcAccess(url, user, pwd);
 
    	System.out.println("Benutzer "+user+" angemeldet...");
    	return dbx;
    }
    /*****************************/

    private static void menu() throws Exception {
        int menuChoice;

        do {
            System.out.println("*** DBA-Firmenbeispiel-Hauptmenu ***\n\n"
                    + "Was moechten Sie tun?\n\n"
                    + "1 Personal verwalten\n"
                    + "2 Projekte verwalten\n"
                    + "3 Firmenwagen verwalten\n"
                    + "4 Abteilungen verwalten\n"
                    + "5 Berichte\n\n"
                    + "0 Zuruck");

            menuChoice = getMenuChoice();

            switch (menuChoice) {
                case 1:
                    personnelMenu();
                    break;

                case 2:
                    projectsMenu();
                    break;

                case 3:
                    companyCarMenu();
                    break;

                case 4:
                    departmentsMenu();
                    break;

                case 5:
                    reportsMenu();
                    break;

                case 0:
                    break;

                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        } while (menuChoice != 0);
    }

    private static void personnelMenu() throws Exception {
        int menuChoice;

        do {
            System.out.println("*** Personal verwalten ***\n\n"
                    + "Was moechten Sie tun?\n\n"
                    + "1 Mitarbeiter/Arbeiter/Angestellter suchen (Ueber Personalnr.)\n"
                    + "2 Mitarbeiter/Arbeiter/Angestellter suchen (Ueber Name, Vorname)\n"
                    + "3 Mitarbeiter neu anlegen \n"
                    + "4 Mitarbeiter editieren \n"
                    + "5 Arbeiter neu anlegen \n"
                    + "6 Arbeiter editieren \n"
                    + "7 Angestellte neu anlegen \n"
                    + "8 Angestellte editieren \n"
                    + "9 Mitarbeiter/Arbeiter/Angestellter loeschen\n"
                    + "0 Zurueck");

            menuChoice = getMenuChoice();

            switch (menuChoice) {
                case 1:
                    System.out
                            .println("*** Mitarbeiter suchen (Ueber Personalnummer) ***\n");

                    System.out.print("Personalnummer: ");
                    long persNr = getUserInputLong();

                    try {
                        Personnel personnel = db.loadPersonnel(persNr);
                        System.out.println(personnel.toFullString());
                        pressAnyKey();
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Personalnummer nicht vergeben!\n");
                    }
                    break;

                case 2:
                    System.out
                            .println("*** Mitarbeiter suchen (Ueber Nachname, Vorname) ***\n");

                    System.out.print("Nachname (* am Ende moeglich): ");
                    String lastName = getUserInputString();

                    System.out.print("Vorname (* am Ende moeglich): ");
                    String firstName = getUserInputString();

                    List<Personnel> list = db.queryPersonnelByName(firstName,
                            lastName);

                    if (!list.isEmpty()) {
                        for (Personnel p : list) {
                            System.out.println(p.toString());
                        }
                    } else {
                        System.out.println("Keine(n) Mitarbeiter gefunden!\n");
                    }
                    pressAnyKey();
                    break;

                case 3:
                    System.out
                            .println("*** Mitarbeiter neu anlegen ***\n");
                    Personnel personnel = new Personnel(null, null, null, null);
                    createPersonnel(personnel);
                    db.storePersonnel(personnel);
                    System.out.println(personnel + " angelegt.");
                    break;

                case 5:
                    System.out
                            .println("*** Arbeiter neu anlegen ***\n");
                    Worker worker = new Worker(null, null, null, null, null);
                    createPersonnel(worker);
                    System.out.print("Arbeitsplatz: ");
                    String workplace = getUserInputString();
                    worker.setWorkspace(workplace);
                    db.storePersonnel(worker);
                    System.out.println(worker + " angelegt.");
                    pressAnyKey();
                    break;

                case 7:
                    System.out
                            .println("*** Angestellten neu anlegen ***\n");
                    Employee employee = new Employee(null, null, null, null, null);
                    createPersonnel(employee);
                    System.out.print("Telefon: ");
                    String telephone = getUserInputString();
                    employee.setPhoneNumber(telephone);
                    db.storePersonnel(employee);
                    System.out.println(employee + " angelegt.");
                    pressAnyKey();
                    break;

                case 9:
                    System.out
                            .println("*** Mitarbeiter/Arbeiter/Angestellten loeschen ***\n");

                    System.out.print("Personalnr.: ");
                    long personalNumber = getUserInputLong();
                    try {
                        personnel = db.loadPersonnel(personalNumber);
                        db.deletePersonnel(personnel);
                        System.out.println(personnel + " geloescht.");
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Mitarbeiter zum Loeschen nicht gefunden!\n");
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Noch nicht implementiert.");
                    System.out.println(INVALID_INPUT);
                    break;
            }
        } while (menuChoice != 0);
    }

    private static void createPersonnel(Personnel personnel) throws Exception {

        System.out.println("-------- Personalien -------- ");
        if (personnel.getLastName() != null) {
            System.out.println("Nachname: " + personnel.getLastName());
        }
        System.out.print("Nachname: ");
        String lastName = getUserInputString();

        System.out.print("Vorname: ");
        String firstName = getUserInputString();

        System.out.print("Geburtsdatum (z.B. 30.11.1957):");
        String bDate = getUserInputString();

        System.out.println("-------- Adresse -------- ");
        System.out.print("Strasse: ");
        String street = getUserInputString();

        System.out.print("Hausnr.: ");
        String houseNo = getUserInputString();

        System.out.print("Postleitzahl: ");
        String zip = getUserInputString();

        System.out.print("Stadt: ");
        String city = getUserInputString();

        Address adr = new Address(street, houseNo, zip, city);

        personnel.setLastName(lastName);
        personnel.setFirstName(firstName);
        personnel.setBirthDate(stringToDate(bDate));
        personnel.setAddress(adr);
    }

    private static void projectsMenu() throws Exception {

        int menuChoice;

        do {
            System.out.println("*** Projekte verwalten ***\n\n"
                    + "Was moechten Sie tun?\n\n"
                    + "1 Projekt suchen (nach Bezeichnung)\n"
                    + "2 Projekt neu anlegen \n"
                    + "3 Projekt loeschen\n\n"
                    + "4 Statusberichte fuer Projekt ausgeben\n"
                    + "5 Statusbericht eingeben\n"
                    + "6 Statusbericht Aendern\n\n"
                    + "0 Zurueck");

            menuChoice = getMenuChoice();

            switch (menuChoice) {
                case 1:
                    System.out
                            .println("*** Projekt suchen (Ueber Bezeichnung) ***\n");

                    System.out.print("Bezeichnung (* am Ende moeglich): ");
                    String description = getUserInputString();
                    List<Project> list = db.queryProjectByDescription(description);
                    for (Project p : list) {
                        System.out.println(p);
                        for (WorksOn w : p.getEmployees()) {
                            System.out.println(" " + w.getEmployee());
                        }
                        System.out.println("---");
                    }
                    pressAnyKey();
                    break;

                case 2:
                    System.out.println("*** Projekt neu anlegen ***\n");
                    System.out.print("Projektkuerzel: ");
                    String projId = getUserInputString();
                    System.out.print("Projektbezeichnung: ");
                    description = getUserInputString();

                    Project project = new Project(projId, description);
                    db.storeProject(project);
                    break;

                case 3:
                    System.out.println("*** Projekt loeschen ***\n");

                    System.out.print("Projektkuerzel : ");
                    projId = getUserInputString();
                    try {
                        project = db.loadProject(projId);
                        db.deleteProject(project);
                        System.out.println(project + " geloescht.");
                    } catch (ObjectNotFoundException e) {
                        System.out
                                .println("Kein Projekt zum Loeschen gefunden.");
                    }
                    pressAnyKey();
                    break;

                case 4:
                    System.out
                            .println("*** Statusberichte fuer Projekt ausgeben ***\n");

                    System.out.print("Projektkuerzel: ");
                    projId = getUserInputString();

                    try {
                        project = db.loadProject(projId);
                        for (StatusReport statusReport : project.getStatusReports()) {
                            System.out.println(statusReport.toFullString());
                        }
                        System.out.println("---");

                    } catch (ObjectNotFoundException e) {
                        System.out.println("Projekt (fuer Statusbericht) nicht gefunden!");
                    }
                    pressAnyKey();
                    break;

                case 5:
                    System.out.println("*** Statusbericht eingeben ***\n");

                    try {
                        System.out.print("Projektkuerzel: ");
                        projId = getUserInputString();
                        project = db.loadProject(projId);
                        System.out.print("Inhalt: ");
                        String content = getUserInputString();
                        StatusReport newReport = new StatusReport(content, project);
                        db.storeStatusReport(newReport);
                        System.out.println(newReport + " angelegt.");

                    } catch (ObjectNotFoundException e) {
                        System.out.println("Projekt fuer diesen Statusbericht nicht gefunden!");
                    }
                    pressAnyKey();
                    break;

                case 6:
                    System.out.println("*** Statusbericht Aendern ***\n");
                    try {
                        System.out.print("Projektkuerzel: ");
                        projId = getUserInputString();
                        project = db.loadProject(projId);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Projekt fuer diesen Statusbericht nicht gefunden!");
                        continue;
                    }
                    try {
                        System.out.print("Statusbericht: ");
                        long contNumber = getUserInputLong();
                        StatusReport report = db.loadStatusReport(project, contNumber);
                        System.out.println("Inhalt: " + report.getContent());

                        System.out.print("Neuer Inhalt: ");
                        String content = getUserInputString();
                        report.setContent(content);
                        db.storeStatusReport(report);
                        System.out.println(report + " wurde aktualisiert.");

                    } catch (ObjectNotFoundException e) {
                        System.out.println("Statusbericht zum Aendern nicht gefunden!");
                    }
                    pressAnyKey();
                    break;

                case 0:
                    break;

                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        } while (menuChoice != 0);
    }

    private static void companyCarMenu() throws Exception {
        int menuChoice;
        Car car;

        do {
            System.out.println("*** Firmenwagen verwalten ***\n\n"
                    + "Was moechten Sie tun?\n\n"
                    + "1 Firmenwagen suchen (Ueber Modell)\n"
                    + "2 Firmenwagen neu anlegen\n"
                    + "3 Firmenwagen loeschen\n"
                    + "4 Modell anlegen \n"
                    + "5 Modell loeschen\n\n"
                    + "0 Zurueck");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                case 1:
                    System.out
                            .println("*** Firmenwagen suchen (ueber Modell) ***\n");

                    System.out.print("Modell eingeben (* am Ende moeglich): ");
                    String queryString = getUserInputString();
                    List<CompanyCar> result = db
                            .queryCompanyCarByModel(queryString);
                    for (CompanyCar c : result) {
                        System.out.println(c.toFullString());
                        System.out.println("---");
                    }
                    pressAnyKey();
                    break;

                case 2:
                    System.out.println("*** Firmenwagen neu anlegen ***\n");

                    System.out.print("Modell: ");
                    String modell = getUserInputString();

                    car = db.loadCar(modell);

                    System.out.print("Nummernschild: ");
                    String num = getUserInputString();

                    CompanyCar comCar = new CompanyCar(num, car);

                    db.storeCompanyCar(comCar);
                    System.out.println(comCar + " angelegt.");
                    pressAnyKey();
                    break;

                case 3:
                    System.out.println("*** Firmenwagen loeschen ***\n");

                    try {
                        System.out.print("Nummernschild: ");
                        num = getUserInputString();

                        CompanyCar companyCar = db.loadCompanyCar(num);
                        db.deleteCompanyCar(companyCar);
                        System.out.println(companyCar + " geloescht.");
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Wagen zum Loeschen nicht gefunden!");
                    }
                    pressAnyKey();
                    break;

                case 4:
                    System.out.println("*** Modell anlegen ***\n");
                    System.out.print("Marke: ");
                    String type = getUserInputString();

                    System.out.print("Modell: ");
                    modell = getUserInputString();

                    car = new Car(modell, type);
                    db.storeCar(car);
                    System.out.println(car + " angelegt.");
                    pressAnyKey();
                    break;

                case 5:
                    System.out.println("*** Modell loeschen ***\n");
                    try {
                        System.out.println("Modell: ");
                        type = getUserInputString();

                        car = db.loadCar(type);
                        db.deleteCar(car);
                        System.out.println(car + " geloescht.");
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Modell zum Loeschen nicht gefunden!");
                    }
                    pressAnyKey();
                    break;

                case 0:
                    break;

                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        } while (menuChoice != 0);
    }

    private static void departmentsMenu() throws Exception {
        int menuChoice;
        Department dep;
        do {
            System.out.println("*** Abteilungen verwalten ***\n\n"
                    + "Was moechten Sie tun? \n\n"
                    + "1 Abteilung suchen (ueber ID)\n"
                    + "2 Abteilung suchen (ueber Bezeichnung)\n"
                    + "3 Abteilung neu anlegen \n"
                    + "4 Abteilung Aendern \n"
                    + "5 Abteilung loeschen \n\n"
                    + "0 Zurueck");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                case 1:
                    System.out.println("*** Abteilung suchen (ueber ID) ***\n");

                    System.out.print("Abteilungnr. eingeben: ");
                    long depNr = getUserInputLong();

                    try {
                        dep = db.loadDepartment(depNr);
                        System.out.println(dep);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Abteilungsnummer existiert nicht!");
                    }
                    break;

                case 2:
                    System.out
                            .println("*** Abteilung suchen (ueber Bezeichnung) ***\n");

                    System.out
                            .print("Abteilungsbezeichnung eingeben (* am Ende moeglich): ");
                    String queryString = getUserInputString();
                    List<Department> result = db.queryDepartmentByName(queryString);
                    for (Department d : result) {
                        System.out.println(d);
                    }
                    pressAnyKey();
                    break;

                case 3:
                    System.out.println("*** Abteilung anlegen ***\n");

                    System.out.print("Abteilungnr.: ");
                    long id = getUserInputLong();

                    try {
                        db.loadDepartment(id);
                        System.out.println("Abteilungnr. bereits vergeben.");
                    } catch (ObjectNotFoundException e) {
                        System.out.print("Bezeichnung der Abteilung: ");
                        String name = getUserInputString();

                        dep = new Department(id, name);
                        db.storeDepartment(dep);
                        System.out.println(dep);
                    }
                    pressAnyKey();
                    break;

                case 4:
                    System.out.println("*** Abteilung Aendern ***\n");

                    System.out.println("Abteilungnummer eingeben: ");
                    depNr = getUserInputLong();

                    try {
                        dep = db.loadDepartment(depNr);
                        System.out.println("Abteilungname: " + dep.getName());
                        System.out.print("Neuen Abteilungname eingeben: ");
                        String name = getUserInputString();
                        dep.setName(name);
                        db.storeDepartment(dep);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Abteilung zum Aendern nicht gefunden!");
                    }
                    break;

                case 5:
                    System.out.println("*** Abteilung loeschen ***\n");

                    try {
                        System.out.println("Abteilunsnummer : ");
                        depNr = getUserInputLong();

                        dep = db.loadDepartment(depNr);
                        db.deleteDepartment(dep);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Abteilung zum Loeschen nicht gefunden!");
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        } while (menuChoice != 0);
    }


    private static void reportsMenu() throws Exception {
        int menuChoice;
        Exception dep;
        do {
            System.out.println("\n*** Berichte ***\n\n"
                    + "Was moechten Sie tun? \n\n"
                    + "1 Statistik\n"
                    + "2 Nicht ausgelastete Angestellte\n"
                    + "3 Projektuebersicht\n"
                    + "4 Organigramm\n\n"
                    + "0 Zurueck");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                case 1:
                    System.out.println("*** Statistik ***\n");

                    int mc = db.getNumberOfPersonnel();
                    System.out.println("Anzahl Mitarbeiter: " + mc);
                    int pc = db.getNumberOfProjects();
                    System.out.println("Anzahl Projekte:    " + pc);
                    break;

                case 2:
                    System.out.println("*** Nicht ausgelastete Angestellte ***\n");

                    List<Employee> list = db.getIdleEmployees();

                    if (!list.isEmpty()) {
                        for (Employee employee : list) {
                            System.out.println(employee.toString());
                            System.out.println(employee.getProjects());
                        }
                    } else {
                        System.out.println("Keine(n) nicht ausgelastete(n) Mitarbeiter gefunden!\n");
                    }
                    pressAnyKey();
                    break;
               
                case 3:
                	List<Project> projects = db.getProjectOverview();
                	processReportProjectOverview(projects);                	
                	break;
                
                case 4:
                	List<Personnel> personnelWOBoss =  db.getPersonnellWOBoss();
                	Map<Long, List<Personnel>> mapOrganigram = db.getPersonnelOrganigram();
                	processReportOrganigram(personnelWOBoss, mapOrganigram);
                	break;
                	
                case 0:
                    break;

                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        } while (menuChoice != 0);
    }

    /**
     * Helper methods.
     */
    private static void processReportProjectOverview(List<Project> projects){
    	System.out.println("*** Projektuebersicht ***\n");
    	String lastProjectId = "";
    	for (Project project : projects) {
    		if(lastProjectId != project.getProjectId()){
    			lastProjectId = project.getProjectId();
    			System.out.print("--------------------------------------------------------------------------");
    			System.out.println();
    			System.out.print("("+project.getProjectId()+") ");
    			System.out.print(project.getDescription());
    			System.out.println();
            }
            
            for(WorksOn worksOn : project.getEmployees()){
            	System.out.print("-");
            	System.out.print((worksOn.getJob()+": "));
            	System.out.print(worksOn.getEmployee().getFirstName()+" ");
            	System.out.print(worksOn.getEmployee().getLastName()+" ");
            	System.out.print("(Personalnummer: "+worksOn.getEmployee().getPersonnelNumber()+", ");
            	System.out.print(worksOn.getEmployee().getPosition()+")");
            	System.out.println();
            }
        }
    	
    }
    
    private static void processReportOrganigram(List<Personnel> personnelWOBoss, Map<Long, List<Personnel>> mapOrganigram){
    	
    	for(Personnel pWOBoss : personnelWOBoss){
    		printPersonnel(pWOBoss);
    		System.out.println();
    		printSubordinates(pWOBoss.getPersonnelNumber(), mapOrganigram, 0);
    	}
    	
    }
    
    private static void printPersonnel(Personnel personnel){
    	System.out.print(personnel.getPersonnelNumber()+" ");
    	System.out.print(personnel.getFirstName()+" "+personnel.getLastName());
    	System.out.print( " ("+((personnel.getDepartment()!=null)? personnel.getDepartment().getName() : "-")+", "+personnel.getPosition()+")");
    }
    
    private static void printSubordinates(long bossPersonnelNumber, Map<Long, List<Personnel>> mapOrganigram, int level){
    	
    	if(mapOrganigram.get(bossPersonnelNumber).size() > 0){
    		for(Personnel supordinate : mapOrganigram.get(bossPersonnelNumber)){
    			for(int i=0;i<=level;i++){System.out.print("\t");}
    			printPersonnel(supordinate);
    			System.out.println();
    			int currentLevel = level;
    			if(mapOrganigram.containsKey(supordinate.getPersonnelNumber()) ){    				
    				printSubordinates(supordinate.getPersonnelNumber(), mapOrganigram, ++currentLevel);
    			}
    		}
    	}
    }
    


    private static Date stringToDate(String bDate)
            throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy",
                Locale.GERMANY);
        Date date = format.parse(bDate);
        return date;
    }

    private static int getMenuChoice() throws Exception {
        System.out.print("Ihre Auswahl (Zahl): ");
        return (int) getUserInputLong();
    }

    private static String getUserInputString() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = null;

        // reading user input
        line = br.readLine();
        if (line != null)
            return line;
        else
            throw new IOException("Fehlerhafte Eingabe!");
    }

    private static long getUserInputLong() throws Exception {
        while (true) {
            try {
                return Long.valueOf(getUserInputString());
            } catch (NumberFormatException e) {
                System.out.print("Keine Zahl! Bitte ganze Zahl eingeben: ");
            }
        }
    }

    private static void pressAnyKey() throws Exception {
        System.out.print("\nTaste fuer weiter...");
        getUserInputString();
        System.out.println();
    }

    private static void credits() {

        System.out.println("\n" + NAME + " version " + VERSION + ", Copyright (C) 2011-13");
        System.out.println("CompanyDemo comes with ABSOLUTELY NO WARRANTY;");
        System.out.println("This is free software, and you are welcome ");
        System.out.println("to redistribute it under certain conditions;");
        System.out.println("See gpl2.0.txt for details.");
        System.out
                .println("\nFolgende Personen haben an diesem Projekt mitgearbeitet:");
        System.out.println(" - Marius Czardybon");
        System.out.println(" - Patrick Sturm");
        System.out.println(" - Markus Gumbel");
        System.out.println(" - Maximilian Naehrlich");
    }
}
