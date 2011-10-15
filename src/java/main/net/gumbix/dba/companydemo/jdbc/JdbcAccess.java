package net.gumbix.dba.companydemo.jdbc;

import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.domain.*;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */

public class JdbcAccess implements DBAccess {

    private AddressDAO adrDAO;
    private CarDAO carDAO;
    private CompanyCarDAO comCarDAO;
    private DepartmentDAO depDAO;
    private PersonnelDAO persDAO = new PersonnelDAO(this);
    private EmployeeDAO empDAO = new EmployeeDAO(this);
    private ProjectDAO projDAO;
    private StatusReportDAO statDAO;
    private WorkerDAO workDAO;
    private WorksOnDAO woOnDAO;

    public Connection connection;

    public JdbcAccess() throws Exception {
        this("jdbc:mysql://codd.ki.hs-mannheim.de:3306/firmenwelt",
                "firmenwelt", "firmenwelt10");
    }

    public JdbcAccess(String user, String pwd) throws Exception {
        this("jdbc:mysql://localhost:3306/firmenwelt",
                user, pwd);
    }

    public JdbcAccess(String url, String user, String pwd) throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, user, pwd);
        adrDAO = new AddressDAO(this);
        carDAO = new CarDAO(this);
        comCarDAO = new CompanyCarDAO(this);
        depDAO = new DepartmentDAO(this);
        projDAO = new ProjectDAO(this);
        statDAO = new StatusReportDAO(this);
        workDAO = new WorkerDAO(this);
        woOnDAO = new WorksOnDAO(this);
    }

    public Object load(Class clazz, long id) throws Exception {
        return null;
    }

    public void delete(Object object) throws Exception {
    }

    // Personnel
    public Personnel loadPersonnel(long persNr) throws Exception {
        return persDAO.load(persNr);
    }

    public List<Personnel> queryByName(String firstName, String lastName) throws Exception {
        return persDAO.queryByName(firstName, lastName);
    }

    public void storePersonnel(Personnel pers) throws Exception {
        if (pers instanceof Worker) {
            storeWorkers((Worker) pers);
        } else if (pers instanceof Employee) {
            storeEmployee((Employee) pers);
        } else {
            // TODO
        }
    }

    public void deletePersonnel(Personnel pers) throws Exception {
        if (pers instanceof Worker) {
            deleteWorkers((Worker) pers);
        } else if (pers instanceof Employee) {
            deleteEmployee((Employee) pers);
        } else {
            // TODO
        }
    }

    // Employees...
    public Employee loadEmployee(long persNr) throws Exception {
        // empDAO.load(persNr);
        return (Employee) persDAO.load(persNr);
    }

    public List<Employee> loadEmployee(String firstName, String lastName) throws Exception {
        return this.empDAO.load(firstName, lastName);
    }

    public void storeEmployee(Employee emp) throws Exception {
        this.empDAO.store(emp);
    }

    public void deleteEmployee(Employee emp) throws Exception {
        empDAO.delete(emp);
    }

    // Worker...
    public Worker loadWorkers(long persNr) throws Exception {
        return this.workDAO.load(persNr);
    }

    public List<Worker> loadWorkers(String firstName, String lastName) throws Exception {
        return this.workDAO.load(firstName, lastName);
    }

    public void storeWorkers(Worker work) throws Exception {
        this.workDAO.store(work);
    }

    public void deleteWorkers(Worker worker) throws Exception {
        this.workDAO.delete(worker);
    }

    // Address...
    public Address loadAddress(String zip) throws Exception {
        return this.adrDAO.load(zip);
    }

    public void storeAddress(Address adr) throws Exception {
        this.adrDAO.store(adr);
    }

    public void deleteAddress(Address adr) throws Exception {
        adrDAO.delete(adr);
    }

    // Cars...
    public Car loadCar(String modell) throws Exception {
        return this.carDAO.load(modell);
    }

    public void storeCar(Car car) throws Exception {
        this.carDAO.store(car);
    }

    public void deleteCar(Car car) throws Exception {
        this.carDAO.delete(car);
    }

    // CompanyCars...
    public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
        return comCarDAO.load(licensePlate);
    }

    public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
        return comCarDAO.queryByModel(model);
    }

    public void storeCompanyCar(CompanyCar car) throws Exception {
        this.comCarDAO.store(car);
    }

    public void deleteCompanyCar(CompanyCar car) throws Exception {
        this.comCarDAO.delete(car);
    }

    // Departments...
    public Department loadDepartment(long depNumber) throws Exception {
        return depDAO.load(depNumber);
    }

    public List<Department> queryDepartmentByName(String queryString) throws Exception {
        return depDAO.queryByName(queryString);
    }

    public void storeDepartment(Department department) throws Exception {
        this.depDAO.store(department);
    }

    public void deleteDepartment(Department department) throws Exception {
        this.depDAO.delete(department);
    }

    // Projects...
    public Project loadProject(long projNr) throws Exception {
        return projDAO.load(projNr);
    }

    public List<Project> queryProjectByDescription(String queryString) throws Exception {
        return projDAO.queryByDescription(queryString);
    }

    public void storeProject(Project proj) throws Exception {
        this.projDAO.store(proj);
    }

    public void deleteProject(Project proj) throws Exception {
        this.projDAO.delete(proj);
    }

    // StatusReports...
    public List<StatusReport> loadStatusReport(long projNr) throws Exception {
        return this.statDAO.load(projNr);
    }

    public void storeStatusReport(StatusReport rep) throws Exception {
        this.statDAO.store(rep);
    }

    public void deleteStatusReport(StatusReport rep) throws Exception {
        this.statDAO.delete(rep);
    }

    // ---
    public Set<WorksOn> loadWorksOn(Employee employee) throws Exception {
        return woOnDAO.load(employee);
    }

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception {
        return woOnDAO.load(proj);
    }

    public void storeWorksOn(WorksOn wo) throws Exception {
        woOnDAO.store(wo);
    }

    public void deleteWorksOn(WorksOn wo) throws Exception {
        woOnDAO.delete(wo);
    }
}

