package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.domain.Department;
import net.gumbix.dba.companydemo.domain.Personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Patrick Sturm
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class DepartmentDAO extends AbstractDAO {

    public DepartmentDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an Department Objects from Table "Abteilung" reference to the personnel number
    public Department load(long depNumber) throws Exception {

        ResultSet rs = executeSQLQuery("select * from Abteilung where abteilungsNr = " + depNumber);
        Department department;

        if (rs.next()) {

            department = new Department(rs.getInt("abteilungsNr"), rs.getString("bezeichnung"));

        } else {

            department = new Department();
        }

        return department;

    }

    // Loads an Department Objects from Table "Abteilung" reference to the name
    public Department load(String name) throws Exception {

        ResultSet rs = executeSQLQuery("select * from Abteilung where bezeichnung = " + "'" + name + "'");
        Department department;

        if (rs.next()) {

            department = new Department(rs.getInt("abteilungsNr"), rs.getString("bezeichnung"));

        } else {

            department = new Department();
        }

        return department;

    }

    // Store or Update an Department Object in Table "Abteilung"
    public void store(Department department) throws Exception {

        Department dep = load(department.getDepNumber());

        if (dep.getDepNumber() != 0) {

            // update
            PreparedStatement pstmt = access.connection.prepareStatement("update Abteilung set bezeichnung = ? where abteilungsNr = ?");
            pstmt.setString(1, department.getName());
            pstmt.setLong(2, department.getDepNumber());
            pstmt.execute();

        } else {

            // new record
            PreparedStatement pstmt = access.connection.prepareStatement("insert into Abteilung values ( ?, ?)");
            pstmt.setLong(1, department.getDepNumber());
            pstmt.setString(2, department.getName());
            pstmt.execute();
        }
    }

    // Delete an CompanyCar Object from Table "Abteilung"
    public void delete(Department department) throws Exception {
    }
}
