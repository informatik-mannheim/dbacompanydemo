package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.WorksOn;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class WorksOnDAO extends AbstractDAO {

    public WorksOnDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an set of WorksOn Objects from Table "MitarbeiterArbeitetAnProjekt, Angestellter, Projekt" using the personnel number
    public Set<WorksOn> load(Personnel pers) throws Exception {
        return null;
    }

    // Loads an set of WorksOn Objects from Table "MitarbeiterArbeitetAnProjekt, Angestellter, Projekt" using the project number
    public Set<WorksOn> load(Project proj) throws Exception {
        return null;
    }

    // Store or Update an WorksOn Object in Table "Projekt"
    public void store(WorksOn wo) throws Exception {
    }

    // Delete an WorksOn Object from Table "MitarbeiterArbeitetAnProjekt"
    public void delete(WorksOn wo) throws Exception {
    }
}
