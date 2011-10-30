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
    public Car loadCar(String modell) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeCar(Car car) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteCar(Car car) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeCompanyCar(CompanyCar car) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteCompanyCar(CompanyCar car) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Project loadProject(String projId) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Project> queryProjectByDescription(String queryString) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeProject(Project proj) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteProject(Project proj) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public StatusReport loadStatusReport(Project project, long continuousNumber) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<StatusReport> loadStatusReport(Project project) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeStatusReport(StatusReport rep) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteStatusReport(StatusReport rep) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
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
