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
package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.AbstractDBAccess;
import net.gumbix.dba.companydemo.domain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class JdbcAccess extends AbstractDBAccess {

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
        this("jdbc:mysql://oracle.informatik.hs-mannheim.de:3306/firmenwelt",
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

    public long nextPersonnelId() throws Exception {
        return persDAO.nextId();
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
    public StatusReport loadStatusReport(Project project, long continuousNumber) throws Exception {
        return statDAO.load(project, continuousNumber);
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

    // Queries
    public int getNumberOfPersonnel() throws Exception {
        Statement query = connection.createStatement();
        ResultSet rs = query.executeQuery("select count(*) from Mitarbeiter");
        rs.next();
        int result = rs.getInt(1);
        rs.close();
        query.close();
        return result;
    }

    public int getNumberOfProjects() throws Exception {
        Statement query = connection.createStatement();
        ResultSet rs = query.executeQuery("select count(*) from Projekt");
        rs.next();
        int result = rs.getInt(1);
        rs.close();
        query.close();
        return result;
    }

    public List<Employee> getIdleEmployees() throws Exception {
        String queryString = "select personalNr, sum(prozAnteil) " +
                "from Mitarbeiter natural join MitarbeiterArbeitetAnProjekt " +
                "group by Mitarbeiter.personalNr having sum(prozAnteil) < 50 " +
                "order by Mitarbeiter.nachname";
        Statement query = connection.createStatement();
        ResultSet rs = query.executeQuery(queryString);
        List<Employee> result = new ArrayList();
        while (rs.next()) {
            int personnelNumber = rs.getInt(1);
            result.add((Employee) emplDAO.load(personnelNumber));
        }
        rs.close();
        query.close();
        return result;
    }

    public void close() {
        // TODO what to close?
    }
}

