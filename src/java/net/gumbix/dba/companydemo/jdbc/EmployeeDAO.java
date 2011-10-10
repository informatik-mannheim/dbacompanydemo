package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class EmployeeDAO extends AbstractDAO {

    public EmployeeDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an Employee Objects from Table "Angestellter" reference to the personnel number
    // first loads the appropriate personal object and copies the values then the employee object
    public Employee load(long persNr) throws Exception {
        return null;
    }

    // Loads an list of Employee Objects from Table "Mitarbeiter, Angestellter, Ort" using the first name and last name
    public List<Employee> load(String firstName, String lastName) throws Exception {
        return null;
    }

    // Store or Update an Employee Object in Table "Angestellter"
    public void store(Employee emp) throws Exception {
    }

    // Delete an Employee Object from Table "Angestellter"
    public void delete(Employee employee) throws Exception {
    }
}
