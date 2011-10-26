package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class EmployeeDAO extends PersonnelDAO {

    public EmployeeDAO(JdbcAccess access) {
        super(access);
    }

    public Employee load(long persNr) throws Exception {
        // Note: An ObjectNotFound exception can be thrown anyway:
        Personnel personnel = access.loadPersonnel(persNr);
        if (personnel instanceof Employee) {
            return (Employee) personnel;
        } else {
            throw new ObjectNotFoundException(personnel.getClass(),
                    "Personnelnumber " +
                            persNr + " refers to an object of type " +
                            personnel.getClass().getName());
        }
    }

    public void store(Employee employee) throws Exception {
        // First create an entry in table Mitarbeiter:
        super.store(employee);

        // Then also fill in the data for the employee:
        try {
            Personnel personnel = load(employee.getPersonnelNumber());

            // update
            PreparedStatement pstmt =
                    prepareStatement("update Angestellter set telefonNr = ?, " +
                            " where personalNr = ?");
            pstmt.setString(1, employee.getPhoneNumber());
            pstmt.setLong(2, employee.getPersonnelNumber());
            pstmt.execute();
            pstmt.close();
        } catch (ObjectNotFoundException e) {
            // new
            PreparedStatement pstmt =
                    prepareStatement("insert into Angestellter values (?, ?)");
            pstmt.setLong(1, employee.getPersonnelNumber());
            pstmt.setString(2, employee.getPhoneNumber());
            pstmt.execute();
            pstmt.close();
        }
    }

    public void delete(Employee employee) throws Exception {
        PreparedStatement pstmt =
                prepareStatement("delete from Angestellter where personalNr = ?");
        pstmt.setLong(1, employee.getPersonnelNumber());
        pstmt.execute();
        pstmt.close();

        super.delete(employee);
    }
}
