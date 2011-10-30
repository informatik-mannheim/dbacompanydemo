package net.gumbix.dba.companydemo.hibernate;

import net.gumbix.dba.companydemo.db.AbstractDBAccess;
import net.gumbix.dba.companydemo.domain.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class HibernateDBAccess extends AbstractDBAccess {

    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public HibernateDBAccess() {
    }

    public Personnel loadPersonnel(long persNr) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storePersonnel(Personnel pers) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deletePersonnel(Personnel pers) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Department loadDepartment(long depNumber) throws Exception {
        session.beginTransaction();
        Department dep = (Department) session.load(Department.class, depNumber);
        session.getTransaction().commit();
        return dep;
    }

    public List<Department> queryDepartmentByName(String queryString) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeDepartment(Department department) throws Exception {
        session.beginTransaction();
        session.save(department);
        session.getTransaction().commit();
    }

    public void deleteDepartment(Department department) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

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
        session.close();
    }
}
