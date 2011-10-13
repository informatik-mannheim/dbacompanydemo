package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Department;
import net.gumbix.dba.companydemo.domain.Personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Sturm
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class DepartmentDAO extends AbstractDAO {

    public DepartmentDAO(JdbcAccess access) {
        super(access);
    }

    public Department load(long depNumber) throws Exception {
        ResultSet rs =
                executeSQLQuery("select * from Abteilung where abteilungsNr = " + depNumber);
        if (rs.next()) {
            return new Department(rs.getInt("abteilungsNr"), rs.getString("bezeichnung"));
        } else {
            throw new ObjectNotFoundException(Department.class, depNumber + "");
        }
    }

    public List<Department> queryByName(String queryString) throws Exception {
        PreparedStatement prep = prepareStatement("select * from Abteilung" +
                " where bezeichnung like ?");
        prep.setString(1, queryString);
        ResultSet rs = prep.executeQuery();
        List<Department> list = new ArrayList<Department>();
        while (rs.next()) {
            // TODO Ouch!!! Very bad performance. Please revise.
            list.add(load(rs.getLong("abteilungsNr")));
        }
        return list;
    }

    // Store or Update an Department Object in Table "Abteilung"
    public void store(Department department) throws Exception {
        try {
            Department dep = load(department.getDepNumber());
            // update
            PreparedStatement pstmt = access.connection.
                    prepareStatement("update Abteilung set bezeichnung = ? where abteilungsNr = ?");
            pstmt.setString(1, department.getName());
            pstmt.setLong(2, department.getDepNumber());
            pstmt.execute();

        } catch (ObjectNotFoundException e) {
            // new record
            PreparedStatement pstmt = access.connection.
                    prepareStatement("insert into Abteilung values (?, ?)");
            pstmt.setLong(1, department.getDepNumber());
            pstmt.setString(2, department.getName());
            pstmt.execute();
        }
    }

    public void delete(Department department) throws Exception {
        executeSQLQuery("delete from Abteilung where abteilungsNr = " +
                "'" + department.getDepNumber() + "'");
    }
}
