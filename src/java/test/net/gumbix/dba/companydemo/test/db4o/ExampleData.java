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
package net.gumbix.dba.companydemo.test.db4o;

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
        // db4oEmbedded();
        jdbc();
    }

    private static void jdbc() throws Exception {
        createData(new JdbcAccess("firmenwelt", "firmenwelt10"));
    }

    private static void db4oEmbedded() throws Exception {
        new File("firmenwelt.yap").delete();
        createData(new Db4oAccess("firmenwelt.yap"));
    }

    private static void db4oServer() throws Exception {
        createData(new Db4oAccess());
    }

    private static void createData(DBAccess access) throws Exception {
        // Create some car types:
        Car touran = new Car("Touran", "VW");
        access.storeCar(touran);
        Car passat = new Car("Passat", "VW");
        access.storeCar(passat);
        Car sklasse = new Car("S-Klasse", "Mercedes");
        access.storeCar(sklasse);

        // Create some company cars:
        CompanyCar companyCar1234 = new CompanyCar("MA-MA 1234", touran);
        access.storeCompanyCar(companyCar1234);

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
        Employee employeeLohe = new Employee("Lohe", "Fransiska",
                new GregorianCalendar(1967, 12, 01).getTime(), new Address("Chefstraße",
                "1a", "68113", "Mannheim"), "+49 621 12345-100");
        employeeLohe.setCar(companyCar1234);
        employeeLohe.setDepartment(management);
        employeeLohe.setPosition("Vorstands-Chefin");
        access.storePersonnel(employeeLohe);

        Employee employeeMüller = new Employee("Müller", "Walter",
                new GregorianCalendar(1949, 02, 11).getTime(), new Address("Flussweg",
                "23", "68113", "Mannheim"), "+49 621 12345-200");
        employeeLohe.setCar(companyCar1234);
        employeeMüller.setDepartment(produktion);
        employeeMüller.setBoss(employeeLohe);
        access.storePersonnel(employeeMüller);

        Worker workerKleinschmidt = new Worker("Kleinschmidt", "August",
                new GregorianCalendar(1955, 7, 23).getTime(), new Address(
                "Wasserturmstraße", "29", "69214", "Eppelheim"),
                "Halle A/Platz 30");
        workerKleinschmidt.setBoss(employeeMüller);
        workerKleinschmidt.setDepartment(produktion);
        workerKleinschmidt.setPosition("Nachfüller");
        access.storePersonnel(workerKleinschmidt);

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
}
