package net.gumbix.dba.companydemo.hibernate;

import net.gumbix.dba.companydemo.application.process.ProjectStatusEnum;
import net.gumbix.dba.companydemo.db.AbstractDBAccess;
import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

/**
 * The DB access interface that uses Hibernate (3.x) internally.
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class HibernateDBAccess extends AbstractDBAccess {

    private Session session = HibernateUtil.getSessionFactory().openSession();

    public HibernateDBAccess() {
        IdGenerator.generator = new HibernateIdGenerator(session);
    }

    // Personnels
    public Personnel loadPersonnel(long persNr) throws Exception {
        session.beginTransaction();
        Personnel personnel = (Personnel) session.get(Personnel.class, persNr);
        session.getTransaction().commit();
        if (personnel == null) {
            throw new ObjectNotFoundException(Personnel.class, persNr + "");
        }
        return personnel;
    }

    public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception {
        session.beginTransaction();
        firstName = firstName.replace('*', '%');
        lastName = lastName.replace('*', '%');
        List result = session.createQuery("from Personnel"
                + " where firstName like '" + firstName + "'"
                + " and lastName like '" + lastName + "'").list();
        session.getTransaction().commit();
        return result;
    }

    public void storePersonnel(Personnel pers) throws Exception {
        save(pers);
    }

    public void deletePersonnel(Personnel pers) throws Exception {
        delete(pers);
    }

    // Departments...
    public Department loadDepartment(long depNumber) throws Exception {
        session.beginTransaction();
        Department dep = (Department) session.get(Department.class, depNumber);
        session.getTransaction().commit();
        if (dep == null) {
            throw new ObjectNotFoundException(Department.class, depNumber + "");
        }
        return dep;
    }

    public List<Department> queryDepartmentByName(String queryString) throws Exception {
        session.beginTransaction();
        queryString = queryString.replace('*', '%');
        List result = session.createQuery("from Department"
                + " where name like '" + queryString + "'").list();
        session.getTransaction().commit();
        return result;
    }

    public void storeDepartment(Department department) throws Exception {
        save(department);
    }

    public void deleteDepartment(Department department) throws Exception {
        delete(department);
    }

    // Cars
    public Car loadCar(String model) throws Exception {
        session.beginTransaction();
        Car car = (Car) session.get(Car.class, model);
        session.getTransaction().commit();
        if (car == null) {
            throw new ObjectNotFoundException(Personnel.class, model);
        }
        return car;
    }

    public void storeCar(Car car) throws Exception {
        save(car);
    }

    public void deleteCar(Car car) throws Exception {
        delete(car);
    }

    // CompanyCars...
    public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
        session.beginTransaction();
        CompanyCar car = (CompanyCar) session.get(CompanyCar.class, licensePlate);
        session.getTransaction().commit();
        if (car == null) {
            throw new ObjectNotFoundException(Personnel.class, licensePlate);
        }
        return car;
    }

    public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
        session.beginTransaction();
        model = model.replace('*', '%');
        List result = session.createQuery("from CompanyCar as c"
                + " where c.car.model like '" + model + "'").list();
        session.getTransaction().commit();
        return result;
    }

    public void storeCompanyCar(CompanyCar car) throws Exception {
        save(car);
    }

    public void deleteCompanyCar(CompanyCar car) throws Exception {
        delete(car);
    }

    // Projects...
    public Project loadProject(String projId) throws Exception {
        session.beginTransaction();
        Project project = (Project) session.get(Project.class, projId);
        session.getTransaction().commit();
        if (project == null) {
            throw new ObjectNotFoundException(Project.class, projId);
        }
        return project;
    }

    public List<Project> queryProjectByDescription(String queryString) throws Exception {
        session.beginTransaction();
        queryString = queryString.replace('*', '%');
        List result = session.createQuery("from Project "
                + " where description like '" + queryString + "'").list();
        session.getTransaction().commit();
        return result;
    }

    public void storeProject(Project proj) throws Exception {
        save(proj);
    }

    public void deleteProject(Project proj) throws Exception {
        delete(proj);
    }

    // StatusReports...
    public StatusReport loadStatusReport(Project project, long continuousNumber) throws Exception {
        session.beginTransaction();
        List result = session.createQuery("from StatusReport "
                + " where project.projectId = " + project.getProjectId()
                + " and continuousNumber =" + continuousNumber).list();
        if (result.isEmpty()) {
            throw new ObjectNotFoundException(StatusReport.class,
                    project.getProjectId() + "." + continuousNumber);
        } else if (result.size() == 1) {
            return (StatusReport) result.get(0);
        } else {
            throw new RuntimeException("More than one object!");
        }
    }

    public List<StatusReport> loadStatusReport(Project project) throws Exception {
        // TODO
        throw new RuntimeException("Not implemented!");
    }

    public void storeStatusReport(StatusReport rep) throws Exception {
        save(rep);
    }

    public void deleteStatusReport(StatusReport rep) throws Exception {
        delete(rep);
    }

    public Set<WorksOn> loadWorksOn(Employee employee) throws Exception {
        // TODO
        throw new RuntimeException("Not implemented!");
    }

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception {
        // TODO
        throw new RuntimeException("Not implemented!");
    }

    public void storeWorksOn(WorksOn wo) throws Exception {
        save(wo);
    }

    public void deleteWorksOn(WorksOn wo) throws Exception {
        delete(wo);
    }

    // Queries
    public int getNumberOfPersonnel() throws Exception {
        session.beginTransaction();
        Long c = (Long) session.createQuery("select count(*) from Personnel")
                .uniqueResult();
        session.getTransaction().commit();
        return c.intValue();
    }

    public int getNumberOfProjects() throws Exception {
        session.beginTransaction();
        Long c = (Long) session.createQuery("select count(*) from Project")
                .uniqueResult();
        session.getTransaction().commit();
        return c.intValue();
    }

    public List<Employee> getIdleEmployees() throws Exception {
        // TODO
        throw new RuntimeException("Method not yet implemented");
    }

    public void close() {
        session.close();
    }

    /**
     * Save or update the given object. The session is cleared afterwards.
     * @param o
     */
    private void save(Object o) {
        session.beginTransaction();
        session.saveOrUpdate(o);
        session.flush();
        session.getTransaction().commit();
        session.clear();
    }

    /**
     * Delete the given object. The session is cleared afterwards.
     * @param o
     */
    private void delete(Object o) {
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.clear();
    }

	@Override
	public List<Project> getProjectOverview() throws OperationNotSupportedException {
		throw new RuntimeException("Method not yet implemented");
	}

	@Override
	public Map<Long, List<Personnel>> getPersonnelOrganigram() throws Exception {
		throw new RuntimeException("Method not yet implemented");
	}

	@Override
	public List<Personnel> getPersonnellWOBoss() throws Exception {
		throw new RuntimeException("Method not yet implemented");
	}

	@Override
	public ProjectStatus loadProjectStatus(ProjectStatusEnum projectStatus)
			throws Exception {
		// TODO
        throw new RuntimeException("Not implemented!");
	}
}
