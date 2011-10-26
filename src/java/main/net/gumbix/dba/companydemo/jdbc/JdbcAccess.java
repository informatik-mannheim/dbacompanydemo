package net.gumbix.dba.companydemo.jdbc;

import java.sql.*;
import java.util.List;
import java.util.Set;

import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.domain.*;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class JdbcAccess implements DBAccess {

    private CarDAO carDAO = new CarDAO(this);
    private CompanyCarDAO comCarDAO = new CompanyCarDAO(this);
    private DepartmentDAO depDAO = new DepartmentDAO(this);
    private PersonnelDAO persDAO = new PersonnelDAO(this);
    private EmployeeDAO emplDAO = new EmployeeDAO(this);
    private ProjectDAO projDAO = new ProjectDAO(this);
    private StatusReportDAO statDAO = new StatusReportDAO(this);
    private WorkerDAO workerDAO = new WorkerDAO(this);
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
        JdbcIdGenerator.generator = new JdbcIdGenerator(this);
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

    public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception {
        return persDAO.queryByName(firstName, lastName);
    }

    public void storePersonnel(Personnel pers) throws Exception {
        if (pers instanceof Worker) {
            workerDAO.store((Worker) pers);
        } else if (pers instanceof Employee) {
            emplDAO.store((Employee) pers);
        } else {
            persDAO.store(pers);
        }
    }

    public void deletePersonnel(Personnel pers) throws Exception {
        if (pers instanceof Worker) {
            workerDAO.delete((Worker) pers);
        } else if (pers instanceof Employee) {
            emplDAO.delete((Employee) pers);
        } else {
            persDAO.delete(pers);
        }
    }

    public long nextPersonnelId() throws Exception{
        return persDAO.nextId();
    }

    // Employees...
    public Employee loadEmployee(long persNr) throws Exception {
        return emplDAO.load(persNr);
    }

    // Worker...
    public Worker loadWorker(long persNr) throws Exception {
        return workerDAO.load(persNr);
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
    public Project loadProject(String projectId) throws Exception {
        return projDAO.load(projectId);
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
    public StatusReport loadStatusReport(long continuousNumber) throws Exception {
    	return null; // TODO
    }
    
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

