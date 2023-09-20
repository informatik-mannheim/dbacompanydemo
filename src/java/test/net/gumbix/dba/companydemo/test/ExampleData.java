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
package net.gumbix.dba.companydemo.test;

import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.db4o.Db4oAccess;
import net.gumbix.dba.companydemo.domain.*;
import net.gumbix.dba.companydemo.hibernate.HibernateDBAccess;
import net.gumbix.dba.companydemo.jdbc.JdbcAccess;
import net.gumbix.dba.companydemo.mongodb.MongoDbAccess;
import org.junit.Test;

import java.io.File;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Create example data via the Java API. This is the only way for db4o (in
 * contrast to SQL insert-statements).
 *
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class ExampleData {

	@Test
	public void testLoadExampleData() throws Exception {
		ExampleData data = importData();
		assertEquals(21, data.access.getNumberOfPersonnel());
		assertEquals(4, data.access.getNumberOfProjects());
		// Important! Otherwise the generated ids won't be updated.
		data.access.close();
	}

	public static void main(String[] args) throws Exception {
		ExampleData data = importData();
		// Important! Otherwise the generated ids won't be updated.
		data.access.close();
	}

	public static ExampleData importData() throws Exception {
		ExampleData data = new ExampleData();
		// data.db4oEmbedded();
		data.jdbcLocal();
		// data.hibernateLocal();
		// data.mongoLocal();
		return data;
	}

	public DBAccess access;

	public void jdbcLocal() throws Exception {
		access = new JdbcAccess("firmenwelt", "firmenwelt10");
		createData();
	}

	public void jdbc() throws Exception {
		access = new JdbcAccess(); // oracle.informatik.hs-mannheim.de
		createData();
	}

	public void db4oEmbedded() throws Exception {
		new File("firmenwelt.yap").delete();
		access = new Db4oAccess("firmenwelt.yap");
		createData();
	}

	public void db4oServer() throws Exception {
		access = new Db4oAccess();
		createData();
	}

	public void hibernateLocal() throws Exception {
		access = new HibernateDBAccess();
		createData();
	}

	public void mongoLocal() throws Exception {
		access = new MongoDbAccess();
		createData();
	}

	private void createData() throws Exception {
		// Create some car types:
		Car touran = new Car("Touran", "VW");
		access.storeCar(touran);
		Car passat = new Car("Passat", "VW");
		access.storeCar(passat);
		Car sklasse = new Car("S-Klasse", "Mercedes");
		access.storeCar(sklasse);
		Car a5 = new Car("A5", "Audi");
		access.storeCar(a5);

		// Create some company cars:
		CompanyCar companyCar1234 = new CompanyCar("MA-MA 1234", sklasse);
		access.storeCompanyCar(companyCar1234);
		CompanyCar companyCar1235 = new CompanyCar("MA-MA 1235", passat);
		access.storeCompanyCar(companyCar1235);
		CompanyCar companyCar1236 = new CompanyCar("MA-MA 1236", touran);
		access.storeCompanyCar(companyCar1236);
		CompanyCar companyCar1237 = new CompanyCar("MA-MA 1237", passat);
		access.storeCompanyCar(companyCar1237);
		CompanyCar companyCar1240 = new CompanyCar("MA-MA 1240", passat);
		access.storeCompanyCar(companyCar1240);
		CompanyCar companyCar1241 = new CompanyCar("MA-MA 1241", passat);
		access.storeCompanyCar(companyCar1241);

		// Pool-Car:
		CompanyCar companyCar1238 = new CompanyCar("MA-MA 1238", touran);
		access.storeCompanyCar(companyCar1238);

		// Create some departments:
		Department management = new Department(1, "Management");
		access.storeDepartment(management);
		Department einkauf = new Department(2, "Einkauf");
		access.storeDepartment(einkauf);
		Department verkauf = new Department(3, "Verkauf & Marketing");
		access.storeDepartment(verkauf);
		Department it = new Department(4, "IT");
		access.storeDepartment(it);
		Department entwicklung = new Department(5, "Forschung & Entwicklung");
		access.storeDepartment(entwicklung);
		Department produktion = new Department(6, "Produktion");
		access.storeDepartment(produktion);
		Department buchhaltung = new Department(7, "Buchhaltung");
		access.storeDepartment(buchhaltung);
		Department kundendienst = new Department(8, "Kundendienst");
		access.storeDepartment(kundendienst);
		Department qualitaetssicherung = new Department(9, "Qualitätssicherung");
		access.storeDepartment(qualitaetssicherung);

		// Create personnel:

		// Management:
		Employee employeeLohe = addEmployee("Lohe", "Fransiska", 1967, 12, 01, 15000.0, "Chefstraße", "1a", "68305",
				"Mannheim", "+49 621 12345-100", management, "Vorstand", null, companyCar1234);

		Employee employeeLindemann = addEmployee("Lindemann", "Hans", 1968, 1, 21, 4200.5, "Pappelallee", "1a", "10437",
				"Berlin", "+49 621 12345-110", management, "Personalreferent", employeeLohe, null);

		// Einkauf:
		Employee employeeGaenzler = addEmployee("Gänzler", "Bernd", 1964, 1, 5, 5320.0, "Hauptstraße", "110b",
				"68163", "Mannheim", "+49 621 12345-200", einkauf, "Einkäufer", employeeLohe, null);

		// Verkauf:
		Employee employeeRichter = addEmployee("Richter", "Simone", 1971, 6, 6, 6100.0, "Ahornweg", "2", "68163",
				"Mannheim", "+49 621 12345-300", verkauf, "Verkaufsleitung", employeeLohe, companyCar1237);

		Employee employeeReinhard = addEmployee("Reinhard", "Marcus", 1973, 5, 20, 4210.1, "Hauptstraße", "11",
				"68163", "Mannheim", "+49 621 12345-310", verkauf, "Verkäufer", employeeRichter, null);

		Employee employeeUhl = addEmployee("Uhl", "Paul", 1982, 4, 20, 4210.1, "Langestraße", "1", "68163", "Mannheim",
				"+49 621 12345-320", verkauf, "Verkäufer", employeeRichter, null);

		// Kundendienst:
		Employee employeeSimon = addEmployee("Simon", "Frank", 1971, 10, 20, 5900.0, "Holzweg", "23", "68163",
				"Mannheim", "+49 621 12345-330", kundendienst, "Kundendienstleitung", employeeLohe, companyCar1240);

		Employee employeeNix = addEmployee("Nix", "Karl", 1961, 9, 12, 3280.0, "Ritterstraße", "12", "68163",
				"Mannheim", "+49 621 12345-340", kundendienst, "Service-Mitarbeiter", employeeSimon, companyCar1241);

		// IT
		Employee employeeZiegler = addEmployee("Ziegler", "Peter", 1967, 01, 13, 7100.0, "Ulmenweg", "34", "69115",
				"Heidelberg", "+49 621 12345-400", it, "IT-Leiter", employeeLohe, null);

		Employee employeeBauer = addEmployee("Bauer", "Thomas", 1985, 02, 24, 4100.0, "Dorfstraße", "1a", "68309",
				"Mannheim", "+49 621 12345-410", it, "Sys.-Admin", employeeZiegler, null);

		// Produktion:
		Employee employeeMueller = addEmployee("Müller", "Walter", 1949, 02, 11, 5000.0, "Flussweg", "23", "68113",
				"Mannheim", "+49 621 12345-500", produktion, "Produktionsleiter", employeeLohe, companyCar1235);

		Worker workerKleinschmidt = addWorker("Kleinschmidt", "August", 1955, 7, 23, 3800.0, "Wasserturmstraße", "29",
				"69214", "Eppelheim", "Halle A/Platz 30", produktion, "Nachfüller", employeeMueller);

		Worker workerZiegler = addWorker("Ziegler", "Peter", 1961, 11, 15, 3600.0, "Wasserweg", "4", "69115",
				"Heidelberg", "Halle A/Platz 31", produktion, "Auffüller", employeeMueller);

		Worker workerSchmidt = addWorker("Schmidt", "Hanna", 1977, 10, 29, 3550.0, "Wasserweg", "16", "69115",
				"Heidelberg", "Halle A/Platz 32", produktion, "Auffüller", employeeMueller);

		Worker workerAlbrecht = addWorker("Albrecht", "Justin", 1991, 9, 9, 1200.0, "Liesgewann", "6", "69115",
				"Heidelberg", "Halle A/Platz 33", produktion, "Azubi", workerSchmidt);

		// F&E:

		// Dr. cannot be assigned to a field. Bad!
		Employee employeeFischer = addEmployee("Fischer, Dr.", "Jan", 1968, 04, 10, 6900.0, "Untere straße", "2",
				"68163", "Mannheim", "+49 621 12345-600", entwicklung, "F&E-Leiter", employeeLohe, companyCar1236);
		// TODO car was also modified...
		// access.storeCompanyCar(companyCar1236);

		Employee employeeWalther = addEmployee("Walther, Dr.", "Sabrina", 1978, 07, 16, 5990.0, "Hansaweg", "22",
				"68163", "Mannheim", "+49 621 12345-610", entwicklung, "CAD-Experte", employeeFischer, null);

		Employee employeeThorn = addEmployee("Thorn", "Max", 1956, 01, 30, 5800.0, "Hauptstraße", "110a", "68163",
				"Mannheim", "+49 621 12345-620", entwicklung, "Ingenieur", employeeFischer, null);

		// Buchhaltung:
		Employee employeeFischer2 = addEmployee("Fischer", "Lutz", 1959, 5, 7, 4900.0, "Ulmenweg", "18", "68163",
				"Mannheim", "+49 621 12345-700", buchhaltung, "Chefbuchhalter", employeeLohe, null);

		Employee employeeKlein = addEmployee("Klein", "Jennifer", 1979, 1, 29, 3850.0, "Lindenweg", "12", "68305",
				"Mannheim", "+49 621 12345-710", buchhaltung, "Buchhalter", employeeFischer2, null);

		// Berater:
		Employee employeeWeiss = addEmployee("Weiß", "Gisela", 1959, 8, 10, 6280.0, "Unter den Linden", "141", "12487",
				"Berlin", "+49 621 12345-599", null, "Berater", employeeLohe, null);

		// --------------------- Projects ---------------------------

		// Leute einstellen:
		Project hirePeople = new Project("LES", "Personal einstellen");
		access.storeProject(hirePeople);
		WorksOn hirePeopleLohe = new WorksOn(employeeLohe, hirePeople, 10, "Verträge ausstellen");
		access.storeWorksOn(hirePeopleLohe);

		StatusReport hirePeopleReport1 = new StatusReport(new GregorianCalendar(2011, 10, 17).getTime(),
				"Das ist der erste Statusbericht", hirePeople);
		access.storeStatusReport(hirePeopleReport1);
		StatusReport hirePeopleReport2 = new StatusReport(new GregorianCalendar(2011, 10, 18).getTime(),
				"Das ist noch ein Statusbericht", hirePeople);
		access.storeStatusReport(hirePeopleReport2);

		// Neues Produkt entwickeln:
		Project research = new Project("FOP", "Neues Produkt entwickeln");
		access.storeProject(research);
		WorksOn researchWalther = new WorksOn(employeeWalther, research, 50, "Modelle entwerfen");
		access.storeWorksOn(researchWalther);

		WorksOn researchThorn = new WorksOn(employeeThorn, research, 100, "Thermodynamik berechnen");
		access.storeWorksOn(researchThorn);

		StatusReport researchReport1 = new StatusReport(new GregorianCalendar(2012, 8, 17).getTime(),
				"Das ist der erste Statusbericht", research);
		access.storeStatusReport(researchReport1);
		StatusReport researchReport2 = new StatusReport(new GregorianCalendar(2012, 8, 28).getTime(),
				"Fortschritte beim Modell", research);
		access.storeStatusReport(researchReport2);

		// DB portieren:
		Project dbPort = new Project("DBP", "DB portieren");
		access.storeProject(dbPort);
		WorksOn dbPortZiegler = new WorksOn(employeeZiegler, dbPort, 20, "Architektur entwerfen");
		access.storeWorksOn(dbPortZiegler);

		WorksOn dbPortBauer = new WorksOn(employeeBauer, dbPort, 70, "Skripte schreiben");
		access.storeWorksOn(dbPortBauer);

		// Security-Konzept:
		Project securityConcept = new Project("SEC", "Security-Konzept für Firma");
		access.storeProject(securityConcept);
		WorksOn securityConceptZiegler = new WorksOn(employeeZiegler, securityConcept, 40,
				"Security-Konzept entwerfen");
		access.storeWorksOn(securityConceptZiegler);

		WorksOn securityConceptBauer = new WorksOn(employeeBauer, securityConcept, 40, "Hacking");
		access.storeWorksOn(securityConceptBauer);

		WorksOn securityConceptWeiss = new WorksOn(employeeWeiss, securityConcept, 100, "SQL-Code-Injection-Beratung");
		access.storeWorksOn(securityConceptWeiss);

	}

	/**
	 * Helper method to simplify creating an employee.
	 *
	 * @param lastName
	 * @param firstName
	 * @param year
	 * @param month
	 * @param day
	 * @param street
	 * @param houseNumber
	 * @param zip
	 * @param city
	 * @param phone
	 * @param dep
	 * @param position
	 * @param boss
	 * @param car
	 * @return
	 * @throws Exception
	 */
	private Employee addEmployee(String lastName, String firstName, int year, int month, int day, double salary,
			String street, String houseNumber, String zip, String city, String phone, Department dep, String position,
			Personnel boss, CompanyCar car) throws Exception {
		Employee employee = new Employee(lastName, firstName, new GregorianCalendar(year, month, day).getTime(),
				new Address(street, houseNumber, zip, city), phone);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setBirthDate(new GregorianCalendar(year, month, day).getTime());

		employee.setSalary(salary);
		employee.setCar(car);
		employee.setDepartment(dep);
		employee.setPosition(position);
		employee.setBoss(boss);
		employee.setAddress(new Address(street, houseNumber, zip, city));
		access.storePersonnel(employee);
		return employee;
	}

	/**
	 * Helper method to simplify creating a worker.
	 *
	 * @param lastName
	 * @param firstName
	 * @param year
	 * @param month
	 * @param day
	 * @param street
	 * @param houseNumber
	 * @param zip
	 * @param city
	 * @param workplace
	 * @param dep
	 * @param position
	 * @param boss
	 * @return
	 * @throws Exception
	 */
	private Worker addWorker(String lastName, String firstName, int year, int month, int day, double salary,
			String street, String houseNumber, String zip, String city, String workplace, Department dep,
			String position, Personnel boss) throws Exception {
		Worker worker = new Worker(lastName, firstName, new GregorianCalendar(year, month, day).getTime(),
				new Address(street, houseNumber, zip, city), workplace);
		worker.setFirstName(firstName);
		worker.setLastName(lastName);
		worker.setBirthDate(new GregorianCalendar(year, month, day).getTime());
		worker.setSalary(salary);
		worker.setDepartment(dep);
		worker.setPosition(position);
		worker.setBoss(boss);
		worker.setAddress(new Address(street, houseNumber, zip, city));
		access.storePersonnel(worker);
		return worker;
	}
}
