package net.gumbix.dba.companydemo.db;

import java.util.List;
import java.util.Set;

import net.gumbix.dba.companydemo.domain.*;


/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public interface DBAccess {

    public Object load(Class clazz, long id) throws Exception;

    public void delete(Object object) throws Exception;

    // Personnel
    public Personnel loadPersonnel(long persNr) throws Exception;

    public List<Personnel> queryByName(String firstName, String lastName) throws Exception;

    public void storePersonnel(Personnel pers) throws Exception;

    public void deletePersonnel(Personnel pers) throws Exception;

    // Employee
    public Employee loadEmployee(long persNr) throws Exception;

    public List<Employee> loadEmployee(String firstName, String lastName) throws Exception;

    public void storeEmployee(Employee emp) throws Exception;

    public void deleteEmployee(Employee emp) throws Exception;

    // Worker
    public Worker loadWorkers(long persNr) throws Exception;

    public List<Worker> loadWorkers(String firstName, String lastName) throws Exception;

    public void storeWorkers(Worker worker) throws Exception;

    public void deleteWorkers(Worker worker) throws Exception;

    // Department
    public Department loadDepartment(long depNumber) throws Exception;

    public List<Department> queryDepartmentByName(String queryString) throws Exception;

    public void storeDepartment(Department department) throws Exception;

    public void deleteDepartment(Department department) throws Exception;

    // Car
    public Car loadCar(final String modell) throws Exception;

    public void storeCar(Car car) throws Exception;

    public void deleteCar(Car car) throws Exception;

    // CompanyCar
    public CompanyCar loadCompanyCar(String licensePlate) throws Exception;

    public void storeCompanyCar(CompanyCar car) throws Exception;

    public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception;

    public void deleteCompanyCar(CompanyCar car) throws Exception;

    // Adress
    public Address loadAddress(String zip) throws Exception;

    public void storeAddress(Address adr) throws Exception;

    public void deleteAddress(Address adr) throws Exception;

    // Project
    public Project loadProject(long projNr) throws Exception;

    public List<Project> queryProjectByDescription(String queryString) throws Exception;

    public void storeProject(Project proj) throws Exception;

    public void deleteProject(Project proj) throws Exception;

    // StatusReport
    public List<StatusReport> loadStatusReport(Project project) throws Exception;

    public void storeStatusReport(StatusReport rep) throws Exception;

    public void deleteStatusReport(StatusReport rep) throws Exception;

    // ...
    public Set<WorksOn> loadWorksOn(Employee employee) throws Exception;

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception;

    public void storeWorksOn(WorksOn wo) throws Exception;

    public void deleteWorksOn(WorksOn wo) throws Exception;
}
