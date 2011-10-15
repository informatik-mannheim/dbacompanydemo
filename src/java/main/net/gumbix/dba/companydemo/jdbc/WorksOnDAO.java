package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.WorksOn;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class WorksOnDAO extends AbstractDAO {

    public WorksOnDAO(JdbcAccess access) {
        super(access);
    }

    public Set<WorksOn> load(Employee employee) throws Exception {
        WorksOn wo;
        Set<WorksOn> set = new HashSet<WorksOn>();

        ResultSet rs = executeSQLQuery("select * from MitarbeiterArbeitetAnProjekt" +
                " where personalNr = " + employee.getPersonnelNumber());

        while (rs.next()) {
            wo = new WorksOn();
            wo.setEmployee(employee);
            wo.setProject(access.loadProject(rs.getLong("projektNr")));
            wo.setJob(rs.getString("taetigkeit"));
            wo.setPercentage(rs.getDouble("prozAnteil"));
            set.add(wo);
        }
        return set;
    }

    public Set<WorksOn> load(Project proj) throws Exception {
        WorksOn wo;
        Set<WorksOn> set = new HashSet<WorksOn>();

        ResultSet rs = executeSQLQuery("select * from MitarbeiterArbeitetAnProjekt" +
                " where projektNr = " + proj.getProjectNr());

        while (rs.next()) {
            wo = new WorksOn();
            wo.setEmployee((Employee) access.loadPersonnel(rs.getLong("personalNr")));
            wo.setProject(proj);
            wo.setJob(rs.getString("taetigkeit"));
            wo.setPercentage(rs.getDouble("prozAnteil"));
            set.add(wo);
        }
        return set;
    }

    // Store or Update an WorksOn Object in Table "Projekt"
    public void store(WorksOn wo) throws Exception {
    }

    // Delete an WorksOn Object from Table "MitarbeiterArbeitetAnProjekt"
    public void delete(WorksOn wo) throws Exception {
    }
}
