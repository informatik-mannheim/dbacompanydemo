package net.gumbix.dba.companydemo.hibernate;

import net.gumbix.dba.companydemo.db.AbstractDBAccess;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class HibernateDBAccess extends AbstractDBAccess {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public HibernateDBAccess() {
    }

    // Personnels
    public Personnel loadPersonnel(long persNr) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Personnel personnel = (Personnel) session.get(Personnel.class, persNr);
        session.getTransaction().commit();
        if (personnel == null) {
            throw new ObjectNotFoundException(Personnel.class, persNr + "");
        }
        return personnel;
    }

    public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Department dep = (Department) session.get(Department.class, depNumber);
        session.getTransaction().commit();
        if (dep == null) {
            throw new ObjectNotFoundException(Department.class, depNumber + "");
        }
        return dep;
    }

    public List<Department> queryDepartmentByName(String queryString) throws Exception {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CompanyCar car = (CompanyCar) session.get(CompanyCar.class, licensePlate);
        session.getTransaction().commit();
        if (car == null) {
            throw new ObjectNotFoundException(Personnel.class, licensePlate);
        }
        return car;
    }

    public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project project = (Project) session.get(Project.class, projId);
        session.getTransaction().commit();
        if (project == null) {
            throw new ObjectNotFoundException(Project.class, projId);
        }
        return project;
    }

    public List<Project> queryProjectByDescription(String queryString) throws Exception {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeStatusReport(StatusReport rep) throws Exception {
        save(rep);
    }

    public void deleteStatusReport(StatusReport rep) throws Exception {
        delete(rep);
    }

    public Set<WorksOn> loadWorksOn(Employee employee) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<WorksOn> loadWorksOn(Project proj) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeWorksOn(WorksOn wo) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteWorksOn(WorksOn wo) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    // Queries
    public int getNumberOfPersonnel() throws Exception {
        // TODO
        throw new RuntimeException("Method not yet implemented");
    }

    // Queries
    public int getNumberOfProjects() throws Exception {
        // TODO
        throw new RuntimeException("Method not yet implemented");
    }

    public List<Employee> getIdleEmployees() throws Exception {
        // TODO
        throw new RuntimeException("Method not yet implemented");
    }

    public void close() {
        sessionFactory.close();
    }

    private void save(Object o) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
    }

    private void delete(Object o) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
    }
}
