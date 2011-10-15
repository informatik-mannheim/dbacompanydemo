package net.gumbix.dba.companydemo.db4o;

import java.awt.image.DataBuffer;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.*;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 */
public class Db4oAccess implements DBAccess {

    private EmbeddedObjectContainer db = Db4oEmbedded.openFile("firmenwelt.db4o");

    public Db4oAccess() {
    }

    public Object load(Class clazz, long id) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void delete(Object object) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Personnel loadPersonnel(long persNr) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Personnel> queryByName(String firstName, String lastName) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storePersonnel(Personnel pers) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deletePersonnel(Personnel pers) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Employee loadEmployee(long persNr) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Employee> loadEmployee(String firstName, String lastName) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeEmployee(Employee emp) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteEmployee(Employee emp) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Worker loadWorkers(long persNr) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Worker> loadWorkers(String firstName, String lastName) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeWorkers(Worker worker) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteWorkers(Worker worker) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Department loadDepartment(long depNumber) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Department> queryDepartmentByName(String queryString) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeDepartment(Department department) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteDepartment(Department department) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Car loadCar(String model) throws Exception {
        ObjectSet<Car> set = db.queryByExample(new Car(model, null));

        if (set.hasNext()) {
            return set.next();
        } else {
            throw new ObjectNotFoundException(Car.class, model);
        }
    }

    public void storeCar(Car car) throws Exception {
        db.store(car);
    }

    public void deleteCar(Car car) throws Exception {
        db.delete(car);
    }

    public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
        ObjectSet<CompanyCar> set = db.queryByExample(new CompanyCar(licensePlate, null));

        if (set.hasNext()) {
            return set.next();
        } else {
            throw new ObjectNotFoundException(CompanyCar.class, licensePlate);
        }
    }

    public void storeCompanyCar(CompanyCar car) throws Exception {
        db.store(car);
    }

    public List<CompanyCar> queryCompanyCarByModel(final String model) throws Exception {
        ObjectSet<CompanyCar> carList = db.query(new Predicate<CompanyCar>() {
            public boolean match(CompanyCar car) {
                return car.getCar().getModel().equalsIgnoreCase(model);
            }
        });
        return Arrays.asList(carList.toArray(new CompanyCar[0]));
    }

    public void deleteCompanyCar(CompanyCar car) throws Exception {
        db.delete(car);
    }

    public Address loadAddress(String zip) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeAddress(Address adr) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteAddress(Address adr) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Project loadProject(long projNr) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Project> queryProjectByDescription(String queryString) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeProject(Project proj) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteProject(Project proj) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<StatusReport> loadStatusReport(Project project) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeStatusReport(StatusReport rep) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteStatusReport(StatusReport rep) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<WorksOn> loadWorksOn(Employee employee) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeWorksOn(WorksOn wo) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteWorksOn(WorksOn wo) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

