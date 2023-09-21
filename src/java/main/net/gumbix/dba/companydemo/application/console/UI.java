/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011-2023 the authors listed below.

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
import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.db4o.Db4oAccess;
import net.gumbix.dba.companydemo.domain.*;
import net.gumbix.dba.companydemo.hibernate.HibernateDBAccess;
import net.gumbix.dba.companydemo.jdbc.JdbcAccess;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;

// Mongo-DB

import net.gumbix.dba.companydemo.mongodb.*;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Patrick Sturm (patrick-sturm@gmx.net)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class UI {

	private static String NAME = "CompanyDemo";
	private static String VERSION = "0.3.1";
	private static String PROMPT = "Ihre Eingabe (Zahl): ";
	private static String INVALID_INPUT = "Fehlerhafte Eingabe \n\n";
	private static DBAccess db;

	public static void main(String[] args) {
		try {
			selectDB();
			System.out.println("\nVielen Dank, dass Du " + NAME + " genutzt hast.");
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
			System.out.println("*** Willkommen zu " + NAME + " " + VERSION + " ***\n\n"
					+ "Welche Datenbankzugriffsart möchtest Du nutzen? \n\n"
					+ "1 SQL-Datenbank der Hochschule Mannheim (über JDBC) \n"
					+ "2 SQL-Datenbank der Hochschule Mannheim (über Hibernate) \n"
					+ "3 eigene SQL-Datenbank (über JDBC) \n" + "4 eigene SQL-Datenbank (über Hibernate) \n"
					+ "5 eigene DB4O-Datenbank (Servermode; vorher startDb4oServer.* starten) \n"
					+ "6 lokale DB4O-Datenbank \n" + "7 lokale MongoDB-Datenbank\n\n" + "9 Credits \n\n"
					+ "0 Programm beenden");
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
				db = new HibernateDBAccess();
				menu();
				break;

			case 5:
				db = new Db4oAccess("localhost", 8732, "firmenwelt", "firmenwelt10");
				menu();
				break;

			case 6:
				db = new Db4oAccess("firmenwelt.yap");
				menu();
				break;
			case 7:
				db = new MongoDbAccess();
				menu();
				break;

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

	private static void menu() throws Exception {
		int menuChoice;

		do {
			System.out.println("*** DBA-Firmenbeispiel-Hauptmenu ***\n\n" + "Was möchtest Du tun?\n\n"
					+ "1 Personal verwalten\n" + "2 Projekte verwalten\n" + "3 Firmenwagen verwalten\n"
					+ "4 Abteilungen verwalten\n" + "5 Berichte\n\n" + "0 Zurück");

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
			System.out.println("*** Personal verwalten ***\n\n" + "Was möchtest Du tun?\n\n"
					+ "1 Mitarbeiter/Arbeiter/Angestellter suchen (Ueber Personalnr.)\n"
					+ "2 Mitarbeiter/Arbeiter/Angestellter suchen (Ueber Name, Vorname)\n"
					+ "3 Mitarbeiter neu anlegen \n" + "4 Mitarbeiter editieren \n" + "5 Arbeiter neu anlegen \n"
					+ "6 Arbeiter editieren \n" + "7 Angestellte neu anlegen \n" + "8 Angestellte editieren \n"
					+ "9 Mitarbeiter/Arbeiter/Angestellter löschen\n" + "0 Zurück");

			menuChoice = getMenuChoice();

			switch (menuChoice) {
			case 1:
				System.out.println("*** Mitarbeiter suchen (Ueber Personalnummer) ***\n");
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
				System.out.println("*** Mitarbeiter suchen (Ueber Nachname, Vorname) ***\n");
				System.out.print("Nachname (* am Ende möglich): ");
				String lastName = getUserInputString();
				System.out.print("Vorname (* am Ende möglich): ");
				String firstName = getUserInputString();
				List<Personnel> list = db.queryPersonnelByName(firstName, lastName);
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
				System.out.println("*** Mitarbeiter neu anlegen ***\n");
				Personnel personnel = new Personnel(null, null, null, null);
				createPersonnel(personnel);
				db.storePersonnel(personnel);
				System.out.println(personnel + " angelegt.");
				break;

			case 5:
				System.out.println("*** Arbeiter neu anlegen ***\n");
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
				System.out.println("*** Angestellten neu anlegen ***\n");
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
				System.out.println("*** Mitarbeiter/Arbeiter/Angestellten löschen ***\n");
				System.out.print("Personalnr.: ");
				long personalNumber = getUserInputLong();
				try {
					personnel = db.loadPersonnel(personalNumber);
					System.out.println(personnel.toString());
					db.deletePersonnel(personnel);
					System.out.println(personnel + " gelöscht.");
				} catch (ObjectNotFoundException e) {
					System.out.println("Mitarbeiter zum Löschen nicht gefunden!\n");
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
			System.out.println("*** Projekte verwalten ***\n\n" + "Was möchtest Du tun?\n\n"
					+ "1 Projekt suchen (nach Bezeichnung)\n" + "2 Projekt neu anlegen \n" + "3 Projekt löschen\n\n"
					+ "4 Statusberichte für Projekt ausgeben\n" + "5 Statusbericht eingeben\n"
					+ "6 Statusbericht ändern\n\n" + "0 Zurück");
			menuChoice = getMenuChoice();

			switch (menuChoice) {
			case 1:
				System.out.println("*** Projekt suchen (Ueber Bezeichnung) ***\n");
				System.out.print("Bezeichnung (* am Ende möglich): ");
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
				System.out.print("Projektkürzel: ");
				String projId = getUserInputString();
				System.out.print("Projektbezeichnung: ");
				description = getUserInputString();

				Project project = new Project(projId, description);
				db.storeProject(project);
				break;

			case 3:
				System.out.println("*** Projekt löschen ***\n");
				System.out.print("Projektkürzel : ");
				projId = getUserInputString();
				try {
					project = db.loadProject(projId);
					db.deleteProject(project);
					System.out.println(project + " gelöscht.");
				} catch (ObjectNotFoundException e) {
					System.out.println("Kein Projekt zum Löschen gefunden.");
				}
				pressAnyKey();
				break;

			case 4:
				System.out.println("*** Statusberichte für Projekt ausgeben ***\n");

				System.out.print("Projektkürzel: ");
				projId = getUserInputString();

				try {
					project = db.loadProject(projId);
					for (StatusReport statusReport : project.getStatusReports()) {
						System.out.println(statusReport.toFullString());
					}
					System.out.println("---");

				} catch (ObjectNotFoundException e) {
					System.out.println("Projekt (für Statusbericht) nicht gefunden!");
				}
				pressAnyKey();
				break;

			case 5:
				System.out.println("*** Statusbericht eingeben ***\n");

				try {
					System.out.print("Projektkürzel: ");
					projId = getUserInputString();
					project = db.loadProject(projId);
					System.out.print("Inhalt: ");
					String content = getUserInputString();
					StatusReport newReport = new StatusReport(content, project);
					db.storeStatusReport(newReport);
					System.out.println(newReport + " angelegt.");

				} catch (ObjectNotFoundException e) {
					System.out.println("Projekt für diesen Statusbericht nicht gefunden!");
				}
				pressAnyKey();
				break;

			case 6:
				System.out.println("*** Statusbericht ändern ***\n");
				try {
					System.out.print("Projektkürzel: ");
					projId = getUserInputString();
					project = db.loadProject(projId);
				} catch (ObjectNotFoundException e) {
					System.out.println("Projekt für diesen Statusbericht nicht gefunden!");
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
					System.out.println("Statusbericht zum Ändern nicht gefunden!");
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
			System.out.println("*** Firmenwagen verwalten ***\n\n" + "Was möchtest Du tun?\n\n"
					+ "1 Firmenwagen suchen (Ueber Modell)\n" + "2 Firmenwagen neu anlegen\n"
					+ "3 Firmenwagen löschen\n" + "4 Modell anlegen \n" + "5 Modell löschen\n\n" + "0 Zurueck");
			menuChoice = getMenuChoice();

			switch (menuChoice) {

			case 1:
				System.out.println("*** Firmenwagen suchen (Ueber Modell) ***\n");
				System.out.print("Modell eingeben (* am Ende möglich): ");
				String queryString = getUserInputString();
				List<CompanyCar> result = db.queryCompanyCarByModel(queryString);
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
				System.out.println("*** Firmenwagen löschen ***\n");
				try {
					System.out.print("Nummernschild: ");
					num = getUserInputString();
					CompanyCar companyCar = db.loadCompanyCar(num);
					db.deleteCompanyCar(companyCar);
					System.out.println(companyCar + " gelöscht.");
				} catch (ObjectNotFoundException e) {
					System.out.println("Wagen zum Löschen nicht gefunden!");
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
				System.out.println("*** Modell löschen ***\n");
				try {
					System.out.println("Modell: ");
					type = getUserInputString();
					car = db.loadCar(type);
					db.deleteCar(car);
					System.out.println(car + " gelöscht.");
				} catch (ObjectNotFoundException e) {
					System.out.println("Modell zum Löschen nicht gefunden!");
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
		Department dep = null;
		do {
			System.out.println("*** Abteilungen verwalten ***\n\n" + "Was möchtest Du tun? \n\n"
					+ "1 Abteilung suchen (über ID)\n" + "2 Abteilung suchen (über Bezeichnung)\n"
					+ "3 Abteilung neu anlegen \n" + "4 Abteilung ändern \n" + "5 Abteilung löschen \n\n"
					+ "0 Zurück");
			menuChoice = getMenuChoice();

			switch (menuChoice) {

			case 1:
				System.out.println("*** Abteilung suchen (Ueber ID) ***\n");

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
				System.out.println("*** Abteilung suchen (Ueber Bezeichnung) ***\n");

				System.out.print("Abteilungsbezeichnung eingeben (* am Ende möglich): ");
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
					System.out.print("Bezeichnung der Abteilung: ");
					String name = getUserInputString();
					dep = new Department(id, name);
					db.storeDepartment(dep);
					System.out.println(dep);
					db.loadDepartment(id);
				} catch (ObjectNotFoundException e) {
				}
				pressAnyKey();
				break;

			case 4:
				System.out.println("*** Abteilung ändern ***\n");
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
					System.out.println("Abteilung zum Ändern nicht gefunden!");
				}
				break;

			case 5:
				System.out.println("*** Abteilung löschen ***\n");

				try {
					System.out.println("Abteilunsnummer : ");
					depNr = getUserInputLong();
					dep = db.loadDepartment(depNr);
					db.deleteDepartment(dep);
				} catch (ObjectNotFoundException e) {
					System.out.println("Abteilung zum Löschen nicht gefunden!");
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
			System.out.println("\n*** Berichte ***\n\n" + "Was möchtest Du tun? \n\n" + "1 Statistik\n"
					+ "2 Nicht ausgelastete Angestellte\n\n" + "0 Zurück");
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

	private static Date stringToDate(String bDate) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
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
		System.out.print("\nTaste für weiter...");
		getUserInputString();
		System.out.println();
	}

	private static void credits() {
		System.out.println("\n" + NAME + " version " + VERSION + ", Copyright (C) 2011-23");
		System.out.println("CompanyDemo comes with ABSOLUTELY NO WARRANTY;");
		System.out.println("This is free software, and you are welcome ");
		System.out.println("to redistribute it under certain conditions;");
		System.out.println("See gpl2.0.txt for details.");
		System.out.println("\nFolgende Personen haben an diesem Projekt mitgearbeitet:");
		System.out.println(" - Patrick Sturm");
		System.out.println(" - Sven Haag");
		System.out.println(" - Marius Czardybon");
		System.out.println(" - Eyuep Ünlü");
		System.out.println(" - Mike Möck");
		System.out.println(" - Markus Gumbel");
	}
}
