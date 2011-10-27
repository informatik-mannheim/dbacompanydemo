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
package net.gumbix.dba.companydemo.test;

import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.db4o.Db4oAccess;
import net.gumbix.dba.companydemo.domain.*;
import net.gumbix.dba.companydemo.jdbc.JdbcAccess;

import java.io.File;
import java.util.GregorianCalendar;

/**
 * Create example data via the Java API. This is the only way for
 * db4o (in contrast to SQL insert-statements).
 *
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class ExampleData {

    public static void main(String[] args) throws Exception {
        ExampleData data = new ExampleData();
        // data.db4oEmbedded();
        data.jdbcLocal();
    }

    private DBAccess access;

    public void jdbcLocal() throws Exception {
        access = new JdbcAccess("firmenwelt", "firmenwelt10");
        createData();
    }

    public void jdbc() throws Exception {
        access = new JdbcAccess(); // codd.hs-mannheim.de
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

    private void createData() throws Exception {
        // Create some car types:
        Car touran = new Car("Touran", "VW");
        access.storeCar(touran);
        Car passat = new Car("Passat", "VW");
        access.storeCar(passat);
        Car sklasse = new Car("S-Klasse", "Mercedes");
        access.storeCar(sklasse);

        // Create some company cars:
        CompanyCar companyCar1234 = new CompanyCar("MA-MA 1234", sklasse);
        access.storeCompanyCar(companyCar1234);
        CompanyCar companyCar1235 = new CompanyCar("MA-MA 1235", passat);
        access.storeCompanyCar(companyCar1235);

        // Create some departments:
        Department management = new Department(1, "Management");
        access.storeDepartment(management);
        Department verwaltung = new Department(2, "Verwaltung");
        access.storeDepartment(verwaltung);
        Department einkauf = new Department(3, "Einkauf");
        access.storeDepartment(einkauf);
        Department verkauf = new Department(4, "Verkauf & Marketing");
        access.storeDepartment(verkauf);
        Department it = new Department(5, "IT");
        access.storeDepartment(it);
        Department entwicklung = new Department(6, "Forschung & Entwicklung");
        access.storeDepartment(entwicklung);
        Department produktion = new Department(7, "Produktion");
        access.storeDepartment(produktion);
        Department qualität = new Department(8, "Qualitätssicherung");
        access.storeDepartment(qualität);
        Department buchhaltung = new Department(9, "Buchhaltung");
        access.storeDepartment(buchhaltung);
        Department kundendienst = new Department(10, "Kundendienst");
        access.storeDepartment(kundendienst);

        // Create personnel:
        Employee employeeLohe = addEmployee("Lohe", "Fransiska",
                1967, 12, 01, "Chefstraße", "1a", "68305", "Mannheim",
                "+49 621 12345-100", management, "Vorstand", null, companyCar1234);

        Employee employeeMüller = addEmployee("Müller", "Walter", 1949, 02, 11,
                "Flussweg", "23", "68113", "Mannheim", "+49 621 12345-200",
                produktion, "Produktionsleiter", employeeLohe, companyCar1235);

        Employee employeeZiegler = addEmployee("Ziegler", "Peter",
                1967, 01, 13, "Ulmenweg", "34", "69115", "Heidelberg",
                "+49 621 12345-300", it, "IT-Leiter", employeeLohe, null);

        Worker workerKleinschmidt = addWorker("Kleinschmidt", "August",
                1955, 7, 23, "Wasserturmstraße", "29", "69214", "Eppelheim",
                "Halle A/Platz 30", produktion, "Nachfüller", employeeMüller);

        Worker workerZiegler = addWorker("Ziegler", "Peter",
                1961, 11, 15, "Wasserweg", "4", "69115", "Heidelberg",
                "Halle A/Platz 31", produktion, "Auffüller", employeeMüller);

        // Projects
        Project hirePeople = new Project("LES", "Leute einstellen.");
        access.storeProject(hirePeople);
        WorksOn hirePeopleLohe = new WorksOn(employeeLohe, hirePeople, 10,
                "Verträge ausstellen.");
        access.storeWorksOn(hirePeopleLohe);
        hirePeople.addEmployee(hirePeopleLohe);
        access.storeProject(hirePeople);

        StatusReport hirePeopleReport1 = new StatusReport(
                new GregorianCalendar(2011, 10, 17).getTime(),
                "Das ist ein Statusbericht", hirePeople);
        access.storeStatusReport(hirePeopleReport1);
        StatusReport hirePeopleReport2 = new StatusReport(
                new GregorianCalendar(2011, 10, 18).getTime(),
                "Das ist noch ein Statusbericht", hirePeople);
        access.storeStatusReport(hirePeopleReport2);

        Project research = new Project("FOP", "Neues Produkt entwickeln.");
        access.storeProject(research);

        // Important! Otherwise the generated ids won't be updated.
        access.close();
        System.out.println("Beispieldaten erzeugt.");
    }

    private Employee addEmployee(String lastName, String firstName,
                                 int year, int month, int day,
                                 String street, String houseNumber,
                                 String zip, String city, String phone,
                                 Department dep, String position, Personnel boss,
                                 CompanyCar car) throws Exception {
        Employee employee = new Employee(lastName, firstName,
                new GregorianCalendar(year, month, day).getTime(),
                new Address(street, houseNumber, zip, city), phone);
        employee.setCar(car);
        employee.setDepartment(dep);
        employee.setPosition(position);
        employee.setBoss(boss);
        access.storePersonnel(employee);
        return employee;
    }

    private Worker addWorker(String lastName, String firstName,
                             int year, int month, int day,
                             String street, String houseNumber,
                             String zip, String city, String workplace,
                             Department dep, String position, Personnel boss) throws Exception {
        Worker worker = new Worker(lastName, firstName,
                new GregorianCalendar(year, month, day).getTime(),
                new Address(street, houseNumber, zip, city), workplace);
        worker.setDepartment(dep);
        worker.setPosition(position);
        worker.setBoss(boss);
        access.storePersonnel(worker);
        return worker;
    }
}
