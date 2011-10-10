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
    private EmployeeDAO empDAO = new EmployeeDAO(this);
    private ProjectDAO projDAO;
    private StatusReportDAO statDAO;
    private WorkerDAO workDAO;
    private WorksOnDAO woOnDAO;

    private WeakHashMap<Long, Employee> employeeCache
            = new WeakHashMap<Long, Employee>();

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
        empDAO = new EmployeeDAO(this);
        projDAO = new ProjectDAO(this);
        statDAO = new StatusReportDAO(this);
        workDAO = new WorkerDAO(this);
        woOnDAO = new WorksOnDAO(this);
    }

    // Employees...

    public Employee loadEmployee(long persNr) throws Exception {
        Employee e = employeeCache.get(persNr);
        if (e != null) {
            return e;
        } else {
            e = empDAO.load(persNr);
            employeeCache.put(persNr, e);
            return e;
        }
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
        return this.comCarDAO.load(licensePlate);
    }

    public List<CompanyCar> queryCompanyCar(String licensePlate) throws Exception {
        return comCarDAO.query(licensePlate);
    }

    public void storeCompanyCar(CompanyCar car) throws Exception {
        this.comCarDAO.store(car);
    }

    public void deleteCompanyCar(CompanyCar car) throws Exception {
        this.comCarDAO.delete(car);
    }

    // Departments...
    public Department loadDepartment(long depNumber) throws Exception {
        return this.depDAO.load(depNumber);
    }

    public Department loadDepartment(String name) throws Exception {
        return this.depDAO.load(name);
    }

    public void storeDepartment(Department department) throws Exception {
        this.depDAO.store(department);
    }

    public void deleteDepartment(Department department) throws Exception {
        this.depDAO.delete(department);
    }

    // Projects...
    public Project loadProject(long projNr) throws Exception {
        return this.projDAO.load(projNr);
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

    public void deleteWorksOn(WorksOn wo) throws Exception {
        this.woOnDAO.delete(wo);
    }

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception {
        return this.woOnDAO.load(proj);
    }

    public WorksOn loadWorksOn(long persNr, long projNr) throws Exception {
        // return this.woOnDAO.load(persNr, projNr);
        return null;
    }

    public void storeWorksOn(WorksOn wo) throws Exception {
        this.woOnDAO.store(wo);
    }
}

