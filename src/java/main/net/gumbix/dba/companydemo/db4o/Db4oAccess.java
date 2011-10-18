package net.gumbix.dba.companydemo.db4o;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class Db4oAccess implements DBAccess {

	public ObjectContainer db;

	public Db4oAccess() {
		// ServerConfiguration config =
		// Db4oClientServer.newServerConfiguration();
		db = Db4oClientServer.openClient("localhost", 8732, "firmenwelt",
				"firmenwelt10");

		db.ext().configure().activationDepth(5);
		db.ext().configure().updateDepth(5);
		// db.ext().configure().objectClass(StatusReport.class).persistStaticFieldValues();
		
		ObjectSet<IdGenerator> ids = db.queryByExample(new Db4oIdGenerator());
		if (ids.hasNext()) {
			IdGenerator.generator = ids.next();
		} else {
			System.out.println("Neue IDs");
			IdGenerator.generator = new Db4oIdGenerator();
			db.store(IdGenerator.generator);
		}
	}
	
	public void close() {
		db.store(IdGenerator.generator);
		db.commit();
		db.close();
	}

	public Object load(Class clazz, long id) throws Exception {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	public void delete(Object object) throws Exception {
		// To change body of implemented methods use File | Settings | File
		// Templates.
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

		ObjectSet<Personnel> carList = db.query(new Predicate<Personnel>() {
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
		return Arrays.asList(carList.toArray(new Personnel[0]));
	}

	public void storePersonnel(Personnel personnel) throws Exception {
		db.store(personnel);
	}

	public void deletePersonnel(Personnel personnel) throws Exception {
		db.delete(personnel);
	}

	public Employee loadEmployee(long persNr) throws Exception {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	public List<Employee> loadEmployee(String firstName, String lastName)
			throws Exception {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	public Worker loadWorker(long persNr) throws Exception {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	public List<Worker> queryWorkerByName(String firstName, String lastName)
			throws Exception {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
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
		return Arrays.asList(depList.toArray(new Department[0]));
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
		return Arrays.asList(carList.toArray(new CompanyCar[0]));
	}

	public void deleteCompanyCar(CompanyCar car) throws Exception {
		db.delete(car);
	}

	public Project loadProject(long projNr) throws Exception {
		ObjectSet<Project> set = db.queryByExample(new Project(projNr, null));

		if (set.hasNext()) {
			return set.next();
		} else {
			throw new ObjectNotFoundException(Project.class, projNr + "");
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
					return project.getDescription().startsWith(startsWith);
				} else {
					return project.getDescription().equalsIgnoreCase(
							queryString);
				}
			}
		});
		return Arrays.asList(projects.toArray(new Project[0]));
	}

	public void storeProject(Project proj) throws Exception {
		db.store(proj);
	}

	public void deleteProject(Project proj) throws Exception {
		db.delete(proj);
	}

	// StatusReport
	public StatusReport loadStatusReport(final long continuousNumber)
			throws Exception {
		ObjectSet<StatusReport> reports = db
				.query(new Predicate<StatusReport>() {
					public boolean match(StatusReport report) {
						return report.getContinuousNumber() == continuousNumber;
					}
				});
		if (reports.hasNext()) {
			return reports.next();
		} else {
			throw new ObjectNotFoundException(StatusReport.class,
					continuousNumber + "");
		}
	}

	public List<StatusReport> loadStatusReport(final Project project)
			throws Exception {
		ObjectSet<StatusReport> reports = db
				.query(new Predicate<StatusReport>() {
					public boolean match(StatusReport report) {
						// TODO better via equals()!
						return report.getProject().getProjectNr() == project
								.getProjectNr();
					}
				});
		return Arrays.asList(reports.toArray(new StatusReport[0]));
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
				return worksOn.getEmployee().getPersonnelNumber() == employee
						.getPersonnelNumber();
			}
		});
		return new HashSet(Arrays.asList(worksOns.toArray(new Project[0])));
	}

	public Set<WorksOn> loadWorksOn(final Project proj) throws Exception {
		ObjectSet<WorksOn> worksOns = db.query(new Predicate<WorksOn>() {
			public boolean match(WorksOn worksOn) {
				return worksOn.getProject().getProjectNr() == proj
						.getProjectNr();
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
}
