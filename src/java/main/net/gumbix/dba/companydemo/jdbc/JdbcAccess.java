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

    private AddressDAO adrDAO = new AddressDAO(this);
    private CarDAO carDAO = new CarDAO(this);
    private CompanyCarDAO comCarDAO = new CompanyCarDAO(this);
    private DepartmentDAO depDAO = new DepartmentDAO(this);
    private PersonnelDAO persDAO = new PersonnelDAO(this);
    private EmployeeDAO empDAO = new EmployeeDAO(this);
    private ProjectDAO projDAO = new ProjectDAO(this);
    private StatusReportDAO statDAO = new StatusReportDAO(this);
    private WorkerDAO workDAO = new WorkerDAO(this);
    private WorksOnDAO woOnDAO = new WorksOnDAO(this);

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
    }

    public Object load(Class clazz, long id) throws Exception {
        return null;
    }

    public void delete(Object object) throws Exception {
        // TODO
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
        return empDAO.load(firstName, lastName);
    }

    public void storeEmployee(Employee emp) throws Exception {
        empDAO.store(emp);
    }

    public void deleteEmployee(Employee emp) throws Exception {
        empDAO.delete(emp);
    }

    // Worker...
    public Worker loadWorkers(long persNr) throws Exception {
        return workDAO.load(persNr);
    }

    public List<Worker> loadWorkers(String firstName, String lastName) throws Exception {
        return workDAO.load(firstName, lastName);
    }

    public void storeWorkers(Worker work) throws Exception {
        workDAO.store(work);
    }

    public void deleteWorkers(Worker worker) throws Exception {
        workDAO.delete(worker);
    }

    // Address...
    public Address loadAddress(String zip) throws Exception {
        return adrDAO.load(zip);
    }

    public void storeAddress(Address adr) throws Exception {
        adrDAO.store(adr);
    }

    public void deleteAddress(Address adr) throws Exception {
        adrDAO.delete(adr);
    }

    // Cars...
    public Car loadCar(String modell) throws Exception {
        return carDAO.load(modell);
    }

    public void storeCar(Car car) throws Exception {
        carDAO.store(car);
    }

    public void deleteCar(Car car) throws Exception {
        carDAO.delete(car);
    }

    // CompanyCars...
    public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
        return comCarDAO.load(licensePlate);
    }

    public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
        return comCarDAO.queryByModel(model);
    }

    public void storeCompanyCar(CompanyCar car) throws Exception {
        comCarDAO.store(car);
    }

    public void deleteCompanyCar(CompanyCar car) throws Exception {
        comCarDAO.delete(car);
    }

    // Departments...
    public Department loadDepartment(long depNumber) throws Exception {
        return depDAO.load(depNumber);
    }

    public List<Department> queryDepartmentByName(String queryString) throws Exception {
        return depDAO.queryByName(queryString);
    }

    public void storeDepartment(Department department) throws Exception {
        depDAO.store(department);
    }

    public void deleteDepartment(Department department) throws Exception {
        depDAO.delete(department);
    }

    // Projects...
    public Project loadProject(long projNr) throws Exception {
        return projDAO.load(projNr);
    }

    public List<Project> queryProjectByDescription(String queryString) throws Exception {
        return projDAO.queryByDescription(queryString);
    }

    public void storeProject(Project proj) throws Exception {
        projDAO.store(proj);
    }

    public void deleteProject(Project proj) throws Exception {
        projDAO.delete(proj);
    }

    // StatusReports...
    public List<StatusReport> loadStatusReport(Project project) throws Exception {
        return statDAO.load(project);
    }

    public void storeStatusReport(StatusReport rep) throws Exception {
        statDAO.store(rep);
    }

    public void deleteStatusReport(StatusReport rep) throws Exception {
        statDAO.delete(rep);
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

