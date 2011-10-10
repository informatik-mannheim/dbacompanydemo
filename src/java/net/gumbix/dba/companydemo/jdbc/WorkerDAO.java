package net.gumbix.dba.companydemo.jdbc;

import java.util.List;

import net.gumbix.dba.companydemo.domain.Worker;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class WorkerDAO extends AbstractDAO {

    public WorkerDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an Worker Objects from Table "Arbeiter" reference to the personnel number
    // first loads the appropriate personal object and copies the values then the Worker object
    public Worker load(long persNr) throws Exception {
        return null;
    }

    // Loads an list of Worker Objects from Table "Mitarbeiter, Arbeiter, Ort" using the first name and last name
    public List<Worker> load(String firstName, String lastName) throws Exception {
        return null;
    }

    // Store or Update an Worker Object in Table "Arbeiter"
    public void store(Worker work) throws Exception {
    }

    // Delete an Worker Object from Table "Arbeiter"
    public void delete(Worker work) throws Exception {
    }
}
