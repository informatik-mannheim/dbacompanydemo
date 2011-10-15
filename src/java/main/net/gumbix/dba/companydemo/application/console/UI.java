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

                case 2:
                    System.out.println("*** Arbeiter suchen (nach Nachname, Vorname) ***\n");

                    System.out.println("Nachname (* möglich): ");
                    String lastName = getUserInputString();

                    System.out.println("Vorname (* möglich): ");
                    String firstName = getUserInputString();

                    System.out.println("Input: " + firstName + " " + lastName + "\n");

                    List<Personnel> list = db.queryByName(firstName, lastName);

                    if (!list.isEmpty()) {
                        for (Personnel p : list) {
                            System.out.println(p.toString());
                        }
                    } else {
                        System.out.println("Keine(n) Mitarbeiter gefunden!\n");
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


    private void createPersonnel(Personnel personnel) throws Exception {
        System.out.println("*** Arbeiter anlegen ***\n");

        /*System.out.println("Personalnummer eingeben: "); not used because of auto incremented value in database
      persNrStr = getUserInputString();
      persNr = Long.valueOf(persNrStr);*/
        System.out.println("-------- Personalien -------- \n");
        System.out.print("Nachname eingeben: ");
        String lastName = getUserInputString();

        System.out.print("Vorname eingeben: ");
        String firstName = getUserInputString();

        System.out.print("Geburtsdatum eingeben (z.B. 30.11.1957):");
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

        Address adr = new Address(zip, city);

        personnel.setLastName(lastName);
        personnel.setFirstName(firstName);
        personnel.setBirthDate(stringToGreg(bDate));
        personnel.setAddress(adr);
        personnel.setStreet(street);
        personnel.setHouseNo(houseNo);
    }

    private static void gotoProjekteMenu() throws Exception {

        int menuChoice;

        do {
            System.out.println("*** Projekte verwalten ***\n\n" +
                    "Was möchten Sie tun?\n\n" +
                    "1 Projekt suchen (nach Bezeichnung)\n" +
                    "2 Projekt anlegen \n" +
                    "3 Projekt löschen\n\n" +
                    "4 Statusberichte für Projekt ausgeben\n" +
                    "5 Statusbericht eingeben\n" +
                    "6 Statusbericht ändern\n\n" +
                    "0 Zurück");

            menuChoice = getMenuChoice();

            switch (menuChoice) {
                case 1:
                    System.out.println("*** Projekt suchen (nach Bezeichnung) ***\n");

                    System.out.print("Bezeichnung (* erlaubt): ");
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
                    System.out.println("*** Projekt anlegen ***\n");

                    System.out.print("Projektbezeichnung: ");
                    description = getUserInputString();

                    Project project = new Project();
                    project.setDescription(description);
                    db.storeProject(project);
                    break;

                case 3:
                    System.out.println("*** Projekt löschen ***\n");

                    System.out.print("Projektnr : ");
                    long projNumber = getUserInputLong();
                    try {
                        project = db.loadProject(projNumber);
                        db.deleteProject(project);
                    } catch (ObjectNotFoundException e) {
                        System.out.println("Projekt kann nicht gelöscht werden, da nicht vorhanden.");
                    }
                    break;

                case 4:
                    System.out.println("*** Statusberichte für Projekt ausgeben ***\n");

                    System.out.print("Projektnr: ");
                    long projNr = getUserInputLong();

                    project = db.loadProject(projNr);
                    System.out.println(project);
                    for (StatusReport statusReport : project.getStatusReport()) {
                        System.out.println(statusReport.toFullString());
                    }
                    System.out.println("---");
                    pressAnyKey();
                    break;

                case 5:
                    System.out.println("*** Statusbericht eingeben ***\n");
                    StatusReport statusReport = new StatusReport();
                    System.out.println("Projektnr : ");
                    projNr = getUserInputLong();

                    System.out.println("Inhalt: ");
                    String content = getUserInputString();

                    /*
                    project.setProjectNr(projNr);
                    stat.setContent(content);

                    proj.setStatusReport(statusReport);

                    db.storeStatusReport(stat);
                    */
                    break;

                case 6:
                    System.out.println("*** Statusbericht ändern ***\n");
                    statusReport = new StatusReport();
                    System.out.print("Projektnr : ");
                    projNr = getUserInputLong();

                    System.out.print("Berichtnr: ");
                    Long repNr = getUserInputLong();

                    System.out.print("Neuer Inhalt: ");
                    content = getUserInputString();

                    /*
                    proj.setProjectNr(projNr);
                    stat.setContinuousNumber(repNr);
                    stat.setContent(content);

                    proj.setStatusReport(stat);

                    db.storeStatusReport(stat);
                    */
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
                    "1 Firmenwagen suchen (nach Modell)\n" +
                    "2 Firmenwagen anlegen\n" +
                    "3 Firmenwagen löschen\n" +
                    "4 Modell anlegen \n" +
                    "5 Modell löschen\n\n" +
                    "0 Zurück");

            menuChoice = getMenuChoice();

            switch (menuChoice) {

                case 1:
                    System.out.println("*** Firmenwagen suchen (nach Modell) ***\n");

                    System.out.print("Modell eingeben (% Wildcard): ");
                    String queryString = getUserInputString();
                    List<CompanyCar> result = db.queryCompanyCarByModel(queryString);
                    for (CompanyCar c : result) {
                        System.out.println(c);
                    }
                    pressAnyKey();
                    break;
                //menu "Firmenwagen anlegen"
                case 2:
                    System.out.println("*** Firmenwagen anlegen ***\n");

                    System.out.print("Modell: ");
                    String modell = getUserInputString();

                    car = db.loadCar(modell);

                    System.out.print("Nummernschild: ");
                    String num = getUserInputString();

                    CompanyCar comCar = new CompanyCar(num, car);

                    db.storeCompanyCar(comCar);
                    break;

                case 3:
                    System.out.println("*** Firmenwagen löschen ***\n");

                    System.out.print("Nummernschild: ");
                    num = getUserInputString();

                    CompanyCar companyCar = db.loadCompanyCar(num);
                    db.deleteCompanyCar(companyCar);
                    break;

                case 4:
                    System.out.println("*** Modell anlegen ***\n");
                    System.out.print("Marke: ");
                    String type = getUserInputString();

                    System.out.print("Modell: ");
                    modell = getUserInputString();

                    car = new Car(modell, type);
                    db.storeCar(car);
                    break;

                case 5:
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

            System.out.println("Bericht Nummer : " + stat.getContinuousNumber() + "\n" +
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
