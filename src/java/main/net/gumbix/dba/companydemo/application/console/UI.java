package net.gumbix.dba.companydemo.application.console;

import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.*;
import net.gumbix.dba.companydemo.jdbc.JdbcAccess;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Patrick Sturm
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */

public class UI {

    private static String PROMPT = "Ihre Eingabe (Zahl): ";
    private static String INVALID_INPUT = "Fehlerhafte Eingabe \n\n";
    private static DBAccess db;

    public static void main(String[] args) {

        try {
            selectDB();
        } catch (Exception e) {
            System.out.println("### Fehler verursacht: " + e.getMessage());
            e.printStackTrace();
            System.out.println("### Das Programm wird neu gestartet.\n\n");
            main(null);
        }
    }

    private static void selectDB() throws Exception {
        int menuChoice;

        do {
            System.out.println("*** Willkommen im Datenbank-Auswahl-Menu ***\n\n" +
                    "Welche Datenbankzugriffsart möchten sie nutzen? \n\n" +
                    "1 SQL-Datenbank der Hochschule Mannheim (über JDBC) \n" +
                    "2 SQL-Datenbank der Hochschule Mannheim (über Hibernate) \n" +
                    "3 eigene SQL-Datenbank (über JDBC) \n" +
                    "4 eigene SQL-Datenbank (über Hibernate) \n" +
                    "5 lokale DB4O-Datenbank \n\n" +
                    "0 Programm beenden");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                case 1:
                    db = new JdbcAccess();
                    menu();
                    break;

                case 2:
                    System.out.println("Noch nicht unterstützt.");
                    break;

                case 3:
                    db = new JdbcAccess("firmenwelt", "firmenwelt10");
                    menu();
                    break;

                case 4:
                    System.out.println("Noch nicht unterstützt.");
                    break;

                case 5:
                    // db = new Db4oAccess();
                    menu();
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


    private static void menu() throws Exception {
        int menuChoice;

        do {
            System.out.println("*** Willkommen im DBA-Firmenbeispiel ***\n\n" +
                    "Was möchten Sie tun?\n\n" +
                    "1 Personal verwalten\n" +
                    "2 Projekte verwalten\n" +
                    "3 Firmenwagen verwalten\n" +
                    "4 Abteilungen verwalten\n\n" +
                    "0 Zurück");

            menuChoice = getMenuChoice();

            switch (menuChoice) {
                //menu "1 Mitarbeiter verwalten"
                case 1:
                    goToPersonnelMenu();
                    break;

                //menu "2 Projekte verwalten"
                case 2:
                    gotoProjekteMenu();
                    break;

                //menu "3 Firmenwagen verwalten"
                case 3:
                    gotoFirmenwagenMenu();
                    break;

                //menu "4 Abteilungen verwalten"
                case 4:
                    gotoAbteilungenMenu();
                    break;

                //menu zurueck
                case 0:
                    break;

                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        }
        while (menuChoice != 0);
    }

    private static void goToPersonnelMenu() throws Exception {
        int menuChoice;

        do {
            System.out.println("*** Personal verwalten ***\n\n" +
                    "Was möchten Sie tun?\n\n" +
                    "1 Mitarbeiter suchen (Personalnr.)\n" +
                    "2 Mitarbeiter suchen (Name, Vorname)\n" +
                    "3 Mitarbeiter neu anlegen \n" +
                    "4 Mitarbeiter editieren \n" +
                    "5 Arbeiter neu anlegen \n" +
                    "6 Arbeiter editieren \n" +
                    "7 Angestellte neu anlegen \n" +
                    "8 Angestellte editieren \n" +
                    "9 Mitarbeiter löschen\n" +
                    "0 Zurück");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                // Mitarbeiter suchen
                case 1:
                    System.out.println("*** Mitarbeiter suchen (nach Personalnummer) ***\n");

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

                //menu "0 Zurück"
                case 0:
                    break;

                default:
                    System.out.println("Noch nicht implementiert.");
                    System.out.println(INVALID_INPUT);
                    break;
            }
        }
        while (menuChoice != 0);
    }


    private static void gotoArbeiterMenu() throws Exception {

        int menuChoice;
        Worker work;
        List<Worker> workList;

        //		WorkerDAO workDAO = new WorkerDAO();
        //		PersonnelDAO persDAO = new PersonnelDAO();

        do {
            System.out.println("*** Arbeiter verwalten ***\n\n" +
                    "Was möchten Sie tun?\n\n" +
                    "1 Arbeiter suchen (nach Personalnummer)\n" +
                    "2 Arbeiter suchen (nach Nachname, Vorname)\n" +
                    "3 Arbeiter neu anlegen\n" +
                    "4 Arbeiter editieren\n" +
                    "5 Arbeiter loeschen\n\n" +
                    "0 Zurück\n\n" +
                    "Ihre Eingabe (Zahl): ");

            menuChoice = getMenuChoice();

            switch (menuChoice) {
                //menu "Arbeiter suchen (nach Personalnummer)"
                case 1:
                    System.out.println("*** Arbeiter suchen (nach Personalnummer) ***\n");

                    System.out.println("Personalnummer: ");
                    String persNrStr = getUserInputString();
                    long persNr = Long.valueOf(persNrStr);

                    work = db.loadWorkers(persNr);
                    //				work = workDAO.load(persNr);

                    if (work.getPersonnelNumber() != 0) {

                        printWorker(work);


                    } else {
                        System.out.println("Personalnummer nicht vergeben!\n");
                    }


                    break;

                //menu "Arbeiter suchen (nach Nachname, Vorname)"
                case 2:
                    System.out.println("*** Arbeiter suchen (nach Nachname, Vorname) ***\n");

                    System.out.println("Nachname (* m\u00f6glich): ");
                    String lastName = getUserInputString();

                    System.out.println("Vorname (* m\u00f6glich): ");
                    String firstName = getUserInputString();

                    System.out.println("Input: " + firstName + " " + lastName + "\n");

                    workList = db.loadWorkers(firstName, lastName);
                    //				workList = workDAO.load(firstName, lastName);

                    if (!workList.isEmpty()) {
                        for (Worker w : workList) {
                            printWorker(w);

                        }
                    } else {
                        System.out.println("Keine(n) Mitarbeiter gefunden!\n");
                    }

                    break;

                //menu "Arbeiter neu anlegen"
                case 3:
                    System.out.println("*** Arbeiter anlegen ***\n");

                    /*System.out.println("Personalnummer eingeben: "); not used because of auto incremented value in database
                  persNrStr = getUserInputString();
                  persNr = Long.valueOf(persNrStr);*/
                    System.out.println("-------- Personalien -------- \n");
                    System.out.println("Nachname eingeben: ");
                    lastName = getUserInputString();

                    System.out.println("Vorname eingeben: ");
                    firstName = getUserInputString();

                    System.out.println("Geburtsdatum eingeben z.B. 30.11.1957 :");
                    String bDate = getUserInputString();

                    System.out.println("-------- Adresse -------- \n");
                    System.out.println("Strasse eingeben: ");
                    String street = getUserInputString();

                    System.out.println("Haus Nr. eingeben: ");
                    String houseNo = getUserInputString();

                    System.out.println("Postleitzahl eingeben: ");
                    String zip = getUserInputString();

                    System.out.println("Stadt eingeben: ");
                    String city = getUserInputString();

                    System.out.println("-------- Arbeitsplatz -------- \n");
                    System.out.println("Arbeitsplatz eingeben: ");
                    String workspace = getUserInputString();

                    //create new Address object
                    Address adr = new Address(zip, city);

                    //create new Worker object
                    Worker workerNew = new Worker(lastName, firstName, stringToGreg(bDate), adr, workspace);

                    workerNew.setStreet(street);
                    workerNew.setHouseNo(houseNo);

                    //store the new Worker object in the database
                    db.storeWorkers(workerNew);
                    //				workDAO.store(workerNew);

                    //let you see what was saved
                    printWorker(db.loadWorkers(firstName, lastName).get(0));
                    //				printWorker(workDAO.load(firstName, lastName).get(0));

                    break;

                //menu "Arbeiter editieren"
                case 4:
                    System.out.println("*** Arbeiter editieren ***\n");

                    System.out.println("Personalnummer des zu bearbeitenden Mitarbeiters eingeben: ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    Worker workEdit = db.loadWorkers(persNr);
                    //				Worker workEdit = workDAO.load(persNr);

                    printWorker(workEdit);

                    //check on "personnelEdit != null" did not work here
                    if (workEdit.getPersonnelNumber() != 0) {

                        System.out.println("Arbeitsplatz eingeben: ");
                        workspace = getUserInputString();

                        workEdit.setWorkspace(workspace);

                        //store the new Worker object in the database
                        db.storeWorkers(workEdit);
                        //					workDAO.store(workEdit);

                        //let you see what was saved
                        printWorker(db.loadWorkers(workEdit.getPersonnelNumber()));
                        //					printWorker(workDAO.load(workEdit.getPersonnelNumber()));

                    } else {

                        System.out.println("Personalnummer nicht vergeben!\n");

                    }

                    break;

                //menu "Arbeiter loeschen"
                case 5:

                    System.out.println("*** Arbeiter löschen ***\n");
                    System.out.println("Personalnummer des zu löschenden Arbeiters eingeben: ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    Worker personnelDelete = db.loadWorkers(persNr);
                    //				Personnel personnelDelete = persDAO.load(persNr);

                    //check on "personnelDelete != null" did not work here
                    if (personnelDelete.getPersonnelNumber() != 0) {
                        db.deleteWorkers(personnelDelete);
                    } else {

                        System.out.println("Personalnummer nicht vergeben! L\u00f6schen nicht m\u00f6glich! \n");

                    }


                    break;

                //menu "0 Zurück"
                case 0:
                    break;

                default:
                    System.out.println("Fehlerhafte Eingabe");
                    break;
            }
        }
        while (menuChoice != 0);
    }

    private static void gotoAngestellteMenu() throws Exception {

        int menuChoice;

        //		EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp = new Employee();

        List<Employee> empList;
        List<CompanyCar> comCarList;

        //		PersonnelDAO persDAO = new PersonnelDAO();

        //		CompanyCarDAO comCarDAO = new CompanyCarDAO();
        CompanyCar comCar = new CompanyCar();
        //		WorksOnDAO woDAO = new WorksOnDAO();
        WorksOn wo = new WorksOn();
        //		ProjectDAO projDAO = new ProjectDAO();
        Project proj = new Project();


        do {
            System.out.println("*** Angestellte verwalten ***\n\n" +
                    "Was möchten Sie tun?\n\n" +
                    "1 Angestellten suchen (nach Personalnummer)\n" +
                    "2 Angestellten suchen (nach Nachname, Vorname)\n" +
                    "3 Angestellten neu anlegen\n" +
                    "4 Angestellten editieren\n" +
                    "5 Angestellten löschen\n\n" +
                    "6 Firmenwagen einem Angestellten zuordnen\n" +
                    "7 Firmenwagen einem Angestellten entziehen\n\n" +
                    "8 Angestellten einem Projekt zuordnen\n" +
                    "9 Angestellten von einem Projekt abziehen\n\n" +
                    "0 Zurück\n\n" +
                    PROMPT);

            menuChoice = getMenuChoice();

            switch (menuChoice) {
                //menu "Angestellten suchen (nach Personalnummer)"
                case 1:
                    System.out.println("*** Angestellten suchen (nach Personalnummer) ***\n");

                    System.out.println("Personalnummer : ");
                    String persNrStr = getUserInputString();
                    long persNr = Long.valueOf(persNrStr);

                    emp = db.loadEmployee(persNr);
                    //				emp = empDAO.load(persNr);

                    if (emp.getPersonnelNumber() != 0) {

                        printEmployee(emp);

                    } else {
                        System.out.println("Personalnummer nicht vergeben!\n");
                    }


                    break;

                //menu "Angestellten suchen (nach Nachname, Vorname)"
                case 2:
                    System.out.println("*** Angestellten suchen (nach Nachname, Vorname) ***\n");

                    System.out.println("Nachname (* m\u00f6glich): ");
                    String lastName = getUserInputString();

                    System.out.println("Vorname (* m\u00f6glich): ");
                    String firstName = getUserInputString();

                    System.out.println("Input: " + firstName + " " + lastName + "\n");

                    empList = db.loadEmployee(firstName, lastName);
                    //				empList = empDAO.load(firstName, lastName);

                    if (!empList.isEmpty()) {
                        for (Employee e : empList) {
                            printEmployee(e);

                        }
                    } else {
                        System.out.println("Keine(n) Angestellten gefunden!\n");
                    }

                    break;
                //menu "Angestellten neu anlegen"
                case 3:
                    System.out.println("*** Angestellten anlegen ***\n");

                    /*System.out.println("Personalnummer eingeben: "); not used because of auto incremented value in database
                  persNrStr = getUserInputString();
                  persNr = Long.valueOf(persNrStr);*/
                    System.out.println("-------- Personalien -------- \n");
                    System.out.println("Nachname eingeben: ");
                    lastName = getUserInputString();

                    System.out.println("Vorname eingeben: ");
                    firstName = getUserInputString();

                    System.out.println("Geburtsdatum eingeben z.B. 30.11.1957 :");
                    String bDate = getUserInputString();

                    System.out.println("-------- Adresse -------- \n");
                    System.out.println("Strasse eingeben: ");
                    String street = getUserInputString();

                    System.out.println("Haus Nr. eingeben: ");
                    String houseNo = getUserInputString();

                    System.out.println("Postleitzahl eingeben: ");
                    String zip = getUserInputString();

                    System.out.println("Stadt eingeben: ");
                    String city = getUserInputString();

                    System.out.println("-------- TelefonNr -------- \n");
                    System.out.println("TelefonNr eingeben: ");
                    String telNr = getUserInputString();

                    //create new Address object
                    Address adr = new Address(zip, city);

                    //create new Employee object
                    Employee empNew = new Employee(lastName, firstName, stringToGreg(bDate), adr, telNr);

                    empNew.setStreet(street);
                    empNew.setHouseNo(houseNo);

                    //store the new Employee object in the database
                    db.storeEmployee(empNew);
                    //				empDAO.store(empNew);

                    //let you see what was saved
                    printEmployee(db.loadEmployee(firstName, lastName).get(0));
                    //				printEmployee(empDAO.load(firstName, lastName).get(0));

                    break;

                //menu "Angestellten editieren"
                case 4:

                    System.out.println("*** Angestellten editieren ***\n");

                    System.out.println("Personalnummer des zu bearbeitenden Angestellten eingeben: ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    emp = db.loadEmployee(persNr);
                    //				emp = empDAO.load(persNr);

                    printEmployee(emp);

                    System.out.println("Neue Tel.Nr. eingeben: ");
                    telNr = getUserInputString();

                    emp.setPhoneNumber(telNr);

                    db.storeEmployee(emp);
                    //				empDAO.store(emp);

                    break;

                //menu "Angestellten löschen"
                case 5:

                    System.out.println("*** Angestellten löschen ***\n");

                    System.out.println("Personalnummer des zu loeschenden Angestellten eingeben: ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    Employee personnelDelete = db.loadEmployee(persNr);
                    //				Personnel personnelDelete = persDAO.load(persNr);

                    //check on "personnelDelete != null" did not work here
                    if (personnelDelete.getPersonnelNumber() != 0) {
                        db.deleteEmployee(personnelDelete);
                    } else {
                        System.out.println("Personalnummer nicht vergeben! Löschen nicht möglich! \n");
                    }
                    break;

                //menu "Firmenwagen einem Angestelltem zuordnen"
                case 6:

                    System.out.println("*** Firmenwagen einem Angestelltem zuordnen ***\n");

                    // load and print the available cars
                    comCarList = db.queryCompanyCar("*");
                    //				comCarList = comCarDAO.load();
                    printCompanyCar(comCarList);

                    System.out.println("Personalnummer des Angestellten eingeben : ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    System.out.println("Nummernschild des Firmenwagen eingeben : ");
                    String licensePlate = getUserInputString();

                    comCar = db.loadCompanyCar(licensePlate);
                    //				comCar = comCarDAO.load(licensePlate);

                    emp.setPersonnelNumber(persNr);
                    emp.setCar(comCar);

                    // db.storeCompanyCar(emp);

                    break;

                //menu "Firmenwagen einem Angestelltem entziehen"
                case 7:

                    System.out.println("*** Firmenwagen einem Angestelltem entziehen ***\n");

                    System.out.println("Personalnummer des Angestellten eingeben : ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    emp = db.loadEmployee(persNr);
                    //				emp = empDAO.load(persNr);

                    empNew = new Employee();

                    empNew.setCar(emp.getCar());

                    db.storeCompanyCar(emp.getCar());
                    //				comCarDAO.store(empNew);


                    break;

                //menu "Angestellten einem Projekt zuordnen"
                case 8:

                    System.out.println("*** Angestellten einem Projekt zuordnen ***\n");

                    System.out.println("Personalnummer des Angestellten eingeben : ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    System.out.println("Projektnummer eingeben : ");
                    persNrStr = getUserInputString();
                    long projNr = Long.valueOf(persNrStr);

                    System.out.println("Taetigkeit eingeben : ");
                    String job = getUserInputString();

                    System.out.println("Arbeitszeit Anteil eingeben : ");
                    persNrStr = getUserInputString();
                    double percentage = Double.valueOf(persNrStr);

                    emp = db.loadEmployee(persNr);
                    //				emp = empDAO.load(persNr);
                    proj = db.loadProject(projNr);
                    //				proj = projDAO.load(projNr);

                    wo.setEmployee(emp);
                    wo.setProject(proj);
                    wo.setPercentage(percentage);
                    wo.setJob(job);

                    db.storeWorksOn(wo);
                    //				woDAO.store(wo);


                    break;

                //menu "Angestellten von einem Projekt abziehen"
                case 9:

                    System.out.println("*** Angestellten von einem Projekt abziehen ***\n");

                    System.out.println("Personalnummer des Angestellten eingeben : ");
                    persNrStr = getUserInputString();
                    persNr = Long.valueOf(persNrStr);

                    emp = db.loadEmployee(persNr);
                    //				emp = empDAO.load(persNr);
                    printEmployee(emp);

                    System.out.println("Projektnummer eingeben : ");
                    persNrStr = getUserInputString();
                    projNr = Long.valueOf(persNrStr);

                    proj = db.loadProject(projNr);
                    //				proj = projDAO.load(projNr);

                    wo.setEmployee(emp);
                    wo.setProject(proj);

                    db.deleteWorksOn(wo);
                    //				woDAO.delete(wo);

                    break;
                //menu "0 Zurück"
                case 0:
                    break;

                default:
                    System.out.println("Fehlerhafte Eingabe");
                    break;
            }
        }
        while (menuChoice != 0);


    }

    private static void gotoProjekteMenu() throws Exception {

        int menuChoice;
        //		ProjectDAO projDAO = new ProjectDAO();
        Project proj = new Project();

        //		StatusReportDAO statDAO = new StatusReportDAO();
        StatusReport stat = new StatusReport();
        List<StatusReport> statList;


        do {
            System.out.println("*** Projekte verwalten ***\n\n" +
                    "Was muechten Sie tun?\n\n" +
                    "1 Projekt ausgeben\n" +
                    "2 Projekt alnegen \n" +
                    "3 Projekt löschen\n\n" +
                    "4 Statusberichte ausgeben\n" +
                    "5 Statusbericht eingeben\n" +
                    "6 Statusbericht Aendern\n\n" +
                    "0 Zurück\n\n" +
                    "Ihre Eingabe (Zahl): ");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                //menu "Projekt ausgeben"
                case 1:
                    System.out.println("*** Projekt ausgeben ***\n");

                    System.out.println("ProjektNr : ");
                    String persNrStr = getUserInputString();
                    long projNr = Long.valueOf(persNrStr);

                    proj = db.loadProject(projNr);
                    //				proj = projDAO.load(projNr);

                    printProject(proj);

                    break;

                //menu "Projekt anlegen"
                case 2:
                    System.out.println("*** Projekt anlegen ***\n");

                    System.out.println("Projekt bezeichnung : ");
                    String description = getUserInputString();

                    proj.setDescription(description);

                    db.storeProject(proj);
                    //				projDAO.store(proj);

                    printProject(proj);

                    break;

                //menu "Projekt löschen"
                case 3:
                    System.out.println("*** Projekt löschen ***\n");

                    System.out.println("ProjektNr : ");
                    persNrStr = getUserInputString();
                    projNr = Long.valueOf(persNrStr);

                    // db.deleteProject(projNr);
                    //				projDAO.delete(projNr);

                    break;

                //menu "Statusberichte ausgeben"
                case 4:
                    System.out.println("*** Statusberichte ausgeben ***\n");

                    System.out.println("ProjektNr : ");
                    persNrStr = getUserInputString();
                    projNr = Long.valueOf(persNrStr);

                    statList = db.loadStatusReport(projNr);
                    //				statList = statDAO.load(projNr);

                    printStatusReport(statList);

                    break;

                //menu "Statusbericht eingeben"
                case 5:
                    System.out.println("*** Statusbericht eingeben ***\n");
                    stat = new StatusReport();
                    System.out.println("ProjektNr : ");
                    persNrStr = getUserInputString();
                    projNr = Long.valueOf(persNrStr);

                    System.out.println("Inhalt : ");
                    String content = getUserInputString();

                    proj.setProjectNr(projNr);
                    stat.setContent(content);

                    proj.setStatusReport(stat);

                    db.storeStatusReport(stat);
                    //				statDAO.store(proj);

                    break;

                //menu "Statusbericht Aendern"
                case 6:
                    System.out.println("*** Statusbericht Aendern ***\n");
                    stat = new StatusReport();
                    System.out.println("ProjektNr : ");
                    persNrStr = getUserInputString();
                    projNr = Long.valueOf(persNrStr);

                    System.out.println("BerichtNr : ");
                    persNrStr = getUserInputString();
                    Long repNr = Long.valueOf(persNrStr);

                    System.out.println("Inhalt : ");
                    content = getUserInputString();

                    proj.setProjectNr(projNr);
                    stat.setContinuouslyNr(repNr);
                    stat.setContent(content);

                    proj.setStatusReport(stat);

                    db.storeStatusReport(stat);
                    //				statDAO.store(proj);

                    break;

                case 0:
                    break;

                //invalide input
                default:
                    System.out.println("fehlerhafte Eingabe");
                    break;
            }
        }
        while (menuChoice != 0);


    }

    private static void gotoFirmenwagenMenu() throws Exception {
        int menuChoice;
        Car car;

        do {
            System.out.println("*** Firmenwagen verwalten ***\n\n" +
                    "Was möchten Sie tun?\n\n" +
                    "1 Firmenwagen anlegen\n" +
                    "2 Firmenwagen löschen\n" +
                    "3 Modell anlegen \n" +
                    "4 Modell löschen\n\n" +
                    "0 Zurück\n\n" +
                    PROMPT);

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                //menu "Firmenwagen anlegen"
                case 1:
                    System.out.println("*** Firmenwagen anlegen ***\n");

                    System.out.print("Modell: ");
                    String modell = getUserInputString();

                    car = db.loadCar(modell);

                    System.out.print("Nummernschild: ");
                    String num = getUserInputString();

                    CompanyCar comCar = new CompanyCar(num, car);

                    db.storeCompanyCar(comCar);
                    break;

                case 2:
                    System.out.println("*** Firmenwagen löschen ***\n");

                    System.out.print("Nummernschild: ");
                    num = getUserInputString();

                    CompanyCar companyCar = db.loadCompanyCar(num);
                    db.deleteCompanyCar(companyCar);
                    break;

                case 3:
                    System.out.println("*** Modell anlegen ***\n");
                    System.out.print("Marke: ");
                    String type = getUserInputString();

                    System.out.print("Modell: ");
                    modell = getUserInputString();

                    car = new Car(modell, type);
                    db.storeCar(car);
                    break;

                case 4:
                    System.out.println("*** Modell löschen ***\n");
                    System.out.println("Modell: ");
                    type = getUserInputString();

                    car = db.loadCar(type);
                    db.deleteCar(car);
                    break;

                case 0:
                    break;

                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        }
        while (menuChoice != 0);
    }

    private static void gotoAbteilungenMenu() throws Exception {
        int menuChoice;
        Department dep = new Department();

        do {
            System.out.println("*** Abteilungen verwalten ***\n\n" +
                    "Was möchten Sie tun? \n\n" +
                    "1 Abteilung suchen (nach ID)\n" +
                    "2 Abteilung suchen (nach Bezeichnung)\n" +
                    "3 Abteilung anlegen \n" +
                    "4 Abteilung editieren \n" +
                    "5 Abteilung löschen \n\n" +
                    "0 Zurück");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                case 1:
                    System.out.println("*** Abteilung suchen (nach ID) ***\n");

                    System.out.print("Abteilungnummer eingeben: ");
                    long depNr = getUserInputLong();

                    try {
                        dep = db.loadDepartment(depNr);
                        System.out.println(dep);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Abteilungsnummer existiert nicht!");
                    }
                    break;

                case 2:
                    System.out.println("*** Abteilung suchen (nach Bezeichnung) ***\n");

                    System.out.print("Abteilungsbezeichnung eingeben (% Wildcard): ");
                    String queryString = getUserInputString();
                    List<Department> result = db.queryDepartmentByName(queryString);
                    for (Department d : result) {
                        System.out.println(d);
                    }
                    pressAnyKey();
                    break;
                //menu "Abteilung anlegen"
                case 3:
                    System.out.println("*** Abteilung anlegen ***\n");

                    System.out.print("Abteilungnr: ");
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

                //menu "Abteilung editieren"
                case 4:
                    System.out.println("*** Abteilung editieren ***\n");

                    System.out.println("Abteilungnummer eingeben: ");
                    depNr = getUserInputLong();

                    System.out.println("Neuen Abteilungname eingeben: ");
                    String name = getUserInputString();

                    dep.setDepNumber(depNr);
                    dep.setName(name);

                    db.storeDepartment(dep);
                    break;

                //menu "Abteilung löschen"
                case 5:
                    System.out.println("*** Abteilung löschen ***\n");

                    System.out.println("Abteilunsnummer : ");
                    depNr = getUserInputLong();

                    dep = db.loadDepartment(depNr);
                    db.deleteDepartment(dep);
                    break;

                case 0:
                    break;

                //invalide input
                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        }
        while (menuChoice != 0);
    }

    /**
     * Hilfsmethoden
     */

    private static GregorianCalendar stringToGreg(String bDate) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        GregorianCalendar calendar = new GregorianCalendar();

        java.util.Date date = format.parse(bDate);
        calendar.setTime(date);

        return calendar;

    }

    private static void printPersonnel(Personnel pers) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

        System.out.println(
                "----------------------------- >> " + "Personalnummer : " + pers.getPersonnelNumber() + " << -----------------------------" + "\n" +
                        "Name          : " + pers.getFirstName() + " " + pers.getLastName() + "\n" +
                        "Anschrift     : " + pers.getStreet() + " " + pers.getHouseNo() + "\n" +
                        "                " + pers.getAddress().getZip() + " " + pers.getAddress().getCity() + " " + "\n" +
                        "Geburtsdatum  : " + df.format(pers.getBirthDate().getTime()) + "\n");

        if (pers.getPosition() != null) {
            System.out.println(
                    "Funktion      : " + pers.getPosition() + "\n");
        }

        if (pers.getDepartment() != null) {
            System.out.println(
                    "Abteilung     : " + pers.getDepartment().getName() + "\n" +
                            "AbteilungsNr. : " + pers.getDepartment().getDepNumber() + "\n");
        }

        if (pers.getBoss() != null) {

            System.out.println(
                    "Vorgesetzter  : " + pers.getBoss().getFirstName() + " " + pers.getBoss().getLastName() + "\n" +
                            "PersonalNr.   : " + pers.getBoss().getPersonnelNumber() + "\n");
        }
    }

    private static void printWorker(Worker work) {

        printPersonnel(work);

        System.out.println("Arbeitsplatz  : " + work.getWorkspace() + "\n");

    }

    private static void printEmployee(Employee emp) {

        printPersonnel(emp);

        System.out.println("tel.          : " + emp.getPhoneNumber() + "\n");

        if (emp.getCar().getLicensePlate() != null) {
            System.out.println("Firmenwagen   : " + emp.getCar().getCar().getType() + " " + emp.getCar().getCar().getModel() + "\n" +
                    "Nummernschild : " + emp.getCar().getLicensePlate() + "\n");

        }

        System.out.println("------------- Projekte ------------- \n");

        if (emp.getProjects() != null) {

            for (WorksOn e : emp.getProjects()) {

                System.out.println(
                        "ProjektNr     : " + e.getProject().getProjectNr() + "\n" +
                                "Bezeichnung   : " + e.getProject().getDescription() + "\n" +
                                "Taetigkeit    : " + e.getJob() + "\n" +
                                "ZeitAnteil    : " + e.getPercentage() + "\n\n");

            }
        }

    }

    private static void printProject(Project proj) {

        System.out.println("------ Projekt Nummer " + proj.getProjectNr() + " ------");
        System.out.println("Projektname : " + proj.getDescription() + "\n");

        if (!proj.getEmployees().isEmpty()) {

            System.out.println("------ Beteiligte ------\n");

            for (WorksOn wo : proj.getEmployees()) {

                System.out.println("Name        : " + wo.getEmployee() + "\nTaetigkeit   : " + wo.getJob() + "\n");
            }
        }
    }

    private static void printStatusReport(List<StatusReport> statList) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

        for (StatusReport stat : statList) {

            System.out.println("Bericht Nummer : " + stat.getContinuouslyNr() + "\n" +
                    "Erstellt am    : " + df.format(stat.getDate().getTime()) + "\n" +
                    "Inhalt         : " + stat.getContent() + "\n");
        }
    }

    private static void printCompanyCar(List<CompanyCar> comCarList) {

        System.out.println("Zur Verfügung stehende Autos : \n");

        for (CompanyCar comCar : comCarList) {

            System.out.println("Firmenwagen   : " + comCar.getCar().getType() + " " + comCar.getCar().getModel() + "\n" +
                    "Nummernschild : " + comCar.getLicensePlate() + "\n");
        }
    }

    private static void printDepartment(Department dep) {

        System.out.println("Abteilungsnummer : " + dep.getDepNumber() + "\n" +
                "Abteilungsname   : " + dep.getName() + "\n\n");
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
        return Long.valueOf(getUserInputString());
    }

    private static void pressAnyKey() throws Exception {
        System.out.print("\nTaste für weiter...");
        getUserInputString();
        System.out.println();
    }
}
