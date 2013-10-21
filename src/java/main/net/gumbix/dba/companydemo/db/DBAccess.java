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
package net.gumbix.dba.companydemo.db;

import net.gumbix.dba.companydemo.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface contains all methods to interact with the database.
 * Each database implementation/technology implements this interface.
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Maximilian Nährlich (maximilian.naehrlich@stud.hs-mannheim.de)
 */
public interface DBAccess {

    // Personnel
    public Personnel loadPersonnel(long persNr) throws Exception;

    public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception;

    public void storePersonnel(Personnel pers) throws Exception;

    public void deletePersonnel(Personnel pers) throws Exception;

    // Employee
    public Employee loadEmployee(long persNr) throws Exception;

    // Worker
    public Worker loadWorker(long persNr) throws Exception;

    // Department
    public Department loadDepartment(long depNumber) throws Exception;

    public List<Department> queryDepartmentByName(String queryString) throws Exception;

    public void storeDepartment(Department department) throws Exception;

    public void deleteDepartment(Department department) throws Exception;

    // Car
    public Car loadCar(String modell) throws Exception;

    public void storeCar(Car car) throws Exception;

    public void deleteCar(Car car) throws Exception;

    // CompanyCar
    public CompanyCar loadCompanyCar(String licensePlate) throws Exception;

    public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception;

    public void storeCompanyCar(CompanyCar car) throws Exception;

    public void deleteCompanyCar(CompanyCar car) throws Exception;

    // Project
    public Project loadProject(String projId) throws Exception;

    public List<Project> queryProjectByDescription(String queryString) throws Exception;

    public void storeProject(Project proj) throws Exception;

    public void deleteProject(Project proj) throws Exception;

    // StatusReport
    public StatusReport loadStatusReport(Project project, long continuousNumber) throws Exception;
    
    public List<StatusReport> loadStatusReport(Project project) throws Exception;

    public void storeStatusReport(StatusReport rep) throws Exception;

    public void deleteStatusReport(StatusReport rep) throws Exception;

    // Relationship (n:m) between employees and projects.
    public Set<WorksOn> loadWorksOn(Employee employee) throws Exception;

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception;

    public void storeWorksOn(WorksOn wo) throws Exception;

    public void deleteWorksOn(WorksOn wo) throws Exception;

    // Queries
    public int getNumberOfPersonnel() throws Exception;
    public int getNumberOfProjects() throws Exception;
    public List<Employee> getIdleEmployees() throws Exception;
    public List<Project> getProjectOverview() throws Exception;
   
    public List<Personnel> getPersonnellWOBoss() throws Exception;
    public Map<Long, List<Personnel>> getPersonnelOrganigram() throws Exception;

    public void close();
}
