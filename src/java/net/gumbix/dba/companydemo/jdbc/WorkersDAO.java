package net.gumbix.dba.companydemo.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Workers;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class WorkersDAO extends AbstractDAO {

    public WorkersDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an Workers Objects from Table "Arbeiter" reference to the personnel number
    // first loads the appropriate personal object and copies the values then the Workers object
    public Workers load(long persNr) throws Exception {
        return null;
    }

    // Loads an list of Workers Objects from Table "Mitarbeiter, Arbeiter, Ort" using the first name and last name
    public List<Workers> load(String firstName, String lastName) throws Exception {
        return null;
    }

    // Store or Update an Workers Object in Table "Arbeiter"
    public void store(Workers work) throws Exception {
    }

    // Delete an Workers Object from Table "Arbeiter"
    public void delete(Workers work) throws Exception {
    }
}
