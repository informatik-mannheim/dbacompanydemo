package net.gumbix.dba.compandydemo.test.db4o;

import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.db4o.Db4oAccess;
import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Car;
import net.gumbix.dba.companydemo.domain.CompanyCar;
import net.gumbix.dba.companydemo.domain.Employee;

import java.io.File;
import java.util.GregorianCalendar;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class ExampleData {

    public static void main(String[] args) throws Exception {
        new File("firmenwelt.db4o").delete();
        DBAccess db = new Db4oAccess();

        Car car = new Car("Touran", "VW");
        db.storeCar(car);
        car = new Car("Passat", "VW");
        db.storeCar(car);

        CompanyCar companyCar = new CompanyCar("MA-MA 1234", car);
        db.storeCompanyCar(companyCar);

        Employee employeeLohe = new Employee("Lohe", "Fransiska",
                new GregorianCalendar(),
                new Address("68113", "Mannheim"), "+49 621 23773");
        employeeLohe.setCar(companyCar);
        db.storeEmployee(employeeLohe);
    }
}
