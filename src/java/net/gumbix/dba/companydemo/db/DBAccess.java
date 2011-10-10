package net.gumbix.dba.companydemo.db;

import java.util.List;
import java.util.Set;

import net.gumbix.dba.companydemo.domain.*;


/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public interface DBAccess {

    public Employee loadEmployee(long persNr) throws Exception;

    public List<Employee> loadEmployee(String firstName, String lastName) throws Exception;

    public void storeEmployee(Employee emp) throws Exception;

    public void deleteEmployee(Employee emp) throws Exception;

    public Workers loadWorkers(long persNr) throws Exception;

    public List<Workers> loadWorkers(String firstName, String lastName) throws Exception;

    public void storeWorkers(Workers worker) throws Exception;

    public void deleteWorkers(Workers worker) throws Exception;

    public Department loadDepartment(long depNumber) throws Exception;

    public Department loadDepartment(String name) throws Exception;

    public void storeDepartment(Department department) throws Exception;

    public void deleteDepartment(Department department) throws Exception;

    public Car loadCar(final String modell) throws Exception;

    public void storeCar(Car car) throws Exception;

    public void deleteCar(Car car) throws Exception;

    public Address loadAddress(String zip) throws Exception;

    public void storeAddress(Address adr) throws Exception;

    public void deleteAddress(Address adr) throws Exception;

    public CompanyCar loadCompanyCar(String licensePlate) throws Exception;

    public void storeCompanyCar(CompanyCar car) throws Exception;

    public List<CompanyCar> queryCompanyCar(String licensePlate) throws Exception;

    public void deleteCompanyCar(CompanyCar car) throws Exception;

    public Project loadProject(final long projNr) throws Exception;

    public void storeProject(Project proj) throws Exception;

    public void deleteProject(Project proj) throws Exception;

    public List<StatusReport> loadStatusReport(long projNr) throws Exception;

    public void storeStatusReport(StatusReport rep) throws Exception;

    public void deleteStatusReport(StatusReport rep) throws Exception;

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception;

    public WorksOn loadWorksOn(long persNr, long projNr) throws Exception;

    public void storeWorksOn(WorksOn wo) throws Exception;

    public void deleteWorksOn(WorksOn wo) throws Exception;
}
