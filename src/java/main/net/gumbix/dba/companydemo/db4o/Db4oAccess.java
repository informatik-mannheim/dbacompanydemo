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
package net.gumbix.dba.companydemo.db4o;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;

import net.gumbix.dba.companydemo.db.AbstractDBAccess;
import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Car;
import net.gumbix.dba.companydemo.domain.CompanyCar;
import net.gumbix.dba.companydemo.domain.Department;
import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.StatusReport;
import net.gumbix.dba.companydemo.domain.Worker;
import net.gumbix.dba.companydemo.domain.WorksOn;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.cs.Db4oClientServer;
import com.db4o.query.Predicate;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class Db4oAccess extends AbstractDBAccess {

    public ObjectContainer db;

    public Db4oAccess() {
        this("localhost", 8732, "firmenwelt", "firmenwelt10");
    }

    public Db4oAccess(String filename) {
        db = Db4oEmbedded.openFile(filename);
        init();
    }

    public Db4oAccess(String host, int port, String user, String pwd) {
        // ServerConfiguration config =
        // Db4oClientServer.newServerConfiguration();
        db = Db4oClientServer.openClient(host, 8732, user, pwd);
        init();
    }

    private void init() {
        db.ext().configure().activationDepth(5);
        db.ext().configure().updateDepth(5);
        // db.ext().configure().objectClass(StatusReport.class).persistStaticFieldValues();

        // Load the auto-increment ids:
        ObjectSet<IdGenerator> ids = db.queryByExample(new Db4oIdGenerator());
        if (ids.hasNext()) {
            IdGenerator.generator = ids.next();
        } else {
            System.out.println("IDs fangen bei 1 an.");
            IdGenerator.generator = new Db4oIdGenerator();
            db.store(IdGenerator.generator);
        }
    }

    public void close() {
        db.store(IdGenerator.generator);
        db.commit();
        db.close();
    }

    public Personnel loadPersonnel(long persNr) throws Exception {
        ObjectSet<Personnel> set = db.queryByExample(new Personnel(persNr,
                null, null, null, null));

        if (set.hasNext()) {
            return set.next();
        } else {
            throw new ObjectNotFoundException(Personnel.class, persNr + "");
        }
    }

    public List<Personnel> queryPersonnelByName(final String firstName,
                                                final String lastName) throws Exception {
        // If query string contains a *, we remove the * and use startsWith:
        final String firstNameStartsWith;
        if (firstName.endsWith("*")) {
            firstNameStartsWith = firstName
                    .substring(0, firstName.length() - 1);
        } else {
            firstNameStartsWith = null;
        }
        final String lastNameStartsWith;
        if (lastName.endsWith("*")) {
            lastNameStartsWith = lastName.substring(0, lastName.length() - 1);
        } else {
            lastNameStartsWith = null;
        }

        ObjectSet<Personnel> personnelList = db.query(new Predicate<Personnel>() {
            public boolean match(Personnel personnel) {
                boolean first = false;
                if (firstNameStartsWith != null) {
                    first = personnel.getFirstName().startsWith(
                            firstNameStartsWith);
                } else {
                    first = personnel.getFirstName().equals(firstName);
                }
                boolean last = false;
                if (lastNameStartsWith != null) {
                    last = personnel.getLastName().startsWith(
                            lastNameStartsWith);
                } else {
                    last = personnel.getLastName().equals(lastName);
                }
                return first && last;
            }
        });
        return personnelList;
    }

    public void storePersonnel(Personnel personnel) throws Exception {
        db.store(personnel);
    }

    public void deletePersonnel(Personnel personnel) throws Exception {
        db.delete(personnel);
    }

    public Department loadDepartment(long depNumber) throws Exception {
        ObjectSet<Department> set = db.queryByExample(new Department(depNumber,
                null));

        if (set.hasNext()) {
            return set.next();
        } else {
            throw new ObjectNotFoundException(Department.class, depNumber + "");
        }
    }

    public List<Department> queryDepartmentByName(final String queryString)
            throws Exception {
        // If query string contains a *, we remove the * and use startsWith:
        final String startsWith;
        if (queryString.endsWith("*")) {
            startsWith = queryString.substring(0, queryString.length() - 1);
        } else {
            startsWith = null;
        }

        ObjectSet<Department> depList = db.query(new Predicate<Department>() {
            public boolean match(Department department) {
                if (startsWith != null) {
                    return department.getName().startsWith(startsWith);
                } else {
                    return department.getName().equals(queryString);
                }
            }
        });
        return depList;
    }

    public void storeDepartment(Department department) throws Exception {
        db.store(department);
    }

    public void deleteDepartment(Department department) throws Exception {
        db.delete(department);
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
        ObjectSet<CompanyCar> set = db.queryByExample(new CompanyCar(
                licensePlate, null));

        if (set.hasNext()) {
            return set.next();
        } else {
            throw new ObjectNotFoundException(CompanyCar.class, licensePlate);
        }
    }

    public void storeCompanyCar(CompanyCar car) throws Exception {
        db.store(car);
    }

    public List<CompanyCar> queryCompanyCarByModel(final String model)
            throws Exception {
        ObjectSet<CompanyCar> carList = db.query(new Predicate<CompanyCar>() {
            public boolean match(CompanyCar car) {
                return car.getCar().getModel().equalsIgnoreCase(model);
            }
        });
        return carList;
    }

    public void deleteCompanyCar(CompanyCar car) throws Exception {
        db.delete(car);
    }

    public Project loadProject(String projectNumber) throws Exception {
        ObjectSet<Project> set = db.queryByExample(new Project(projectNumber, null));

        if (set.hasNext()) {
            return set.next();
        } else {
            throw new ObjectNotFoundException(Project.class, projectNumber + "");
        }
    }

    public List<Project> queryProjectByDescription(final String queryString)
            throws Exception {
        // If query string contains a *, we remove the * and use startsWith:
        final String startsWith;
        if (queryString.endsWith("*")) {
            startsWith = queryString.substring(0, queryString.length() - 1);
        } else {
            startsWith = null;
        }

        ObjectSet<Project> projects = db.query(new Predicate<Project>() {
            public boolean match(Project project) {
                if (startsWith != null) {
                    // TODO startsWith does not work
                    return project.getDescription().startsWith(startsWith);
                } else {
                    return project.getDescription().equalsIgnoreCase(
                            queryString);
                }
            }
        });
        return projects;
    }

    public void storeProject(Project proj) throws Exception {
        db.store(proj);
    }

    public void deleteProject(Project proj) throws Exception {
        db.delete(proj);
    }

    // StatusReport
    public StatusReport loadStatusReport(final Project project, final long continuousNumber)
            throws Exception {
        ObjectSet<StatusReport> reports = db
                .query(new Predicate<StatusReport>() {
                    public boolean match(StatusReport report) {
                        return report.getProject().equals(project) &&
                                report.getContinuousNumber() == continuousNumber;
                    }
                });
        if (reports.hasNext()) {
            return reports.next();
        } else {
            throw new ObjectNotFoundException(StatusReport.class,
                    project.getProjectId() + "." + continuousNumber + "");
        }
    }

    public List<StatusReport> loadStatusReport(final Project project)
            throws Exception {
        ObjectSet<StatusReport> reports = db
                .query(new Predicate<StatusReport>() {
                    public boolean match(StatusReport report) {
                        return report.getProject().equals(project);
                    }
                });
        return reports;
    }

    public void storeStatusReport(StatusReport report) throws Exception {
        db.store(report);
    }

    public void deleteStatusReport(StatusReport report) throws Exception {
        db.delete(report);
    }

    // WorksOn...
    public Set<WorksOn> loadWorksOn(final Employee employee) throws Exception {
        ObjectSet<WorksOn> worksOns = db.query(new Predicate<WorksOn>() {
            public boolean match(WorksOn worksOn) {
                return worksOn.getEmployee().equals(employee);
            }
        });
        return new HashSet(Arrays.asList(worksOns.toArray(new Project[0])));
    }

    public Set<WorksOn> loadWorksOn(final Project proj) throws Exception {
        ObjectSet<WorksOn> worksOns = db.query(new Predicate<WorksOn>() {
            public boolean match(WorksOn worksOn) {
                return worksOn.getProject().equals(proj);
            }
        });
        return new HashSet(Arrays.asList(worksOns.toArray(new Project[0])));
    }

    public void storeWorksOn(WorksOn wo) throws Exception {
        db.store(wo);
    }

    public void deleteWorksOn(WorksOn wo) throws Exception {
        db.delete(wo);
    }

    // Queries
    public int getNumberOfPersonnel() throws Exception {
        return db.queryByExample(new Personnel()).size();
    }

    public int getNumberOfProjects() throws Exception {
        return db.queryByExample(new Project()).size();
    }

    public List<Employee> getIdleEmployees() throws Exception {
        // TODO
        throw new RuntimeException("Method not yet implemented");
    }

	@Override
	public List<Project> getProjectOverview() throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
}
