/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011  the authors listed below.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.WorksOn;

import java.sql.PreparedStatement;
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
            wo.setProject(access.loadProject(rs.getString("projektId")));
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
                " where projektId = '" + proj.getProjectId() + "'");

        while (rs.next()) {
            wo = new WorksOn();
            wo.setEmployee(access.loadEmployee(rs.getLong("personalNr")));
            wo.setProject(proj);
            wo.setJob(rs.getString("taetigkeit"));
            wo.setPercentage(rs.getDouble("prozAnteil"));
            set.add(wo);
        }
        return set;
    }

    // Store or Update an WorksOn Object in Table "Projekt"
    public void store(WorksOn wo) throws Exception {

        PreparedStatement pstmt = prepareStatement("select * from MitarbeiterArbeitetAnProjekt" +
                " where personalNr = ? and projektId = ?");
        pstmt.setLong(1, wo.getEmployee().getPersonnelNumber());
        pstmt.setString(2, wo.getProject().getProjectId());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            // update
            // TODO undone
            throw new RuntimeException("Noch net fetisch!");
        } else {
            // new
            PreparedStatement pstmtInsert =
                    prepareStatement("insert into MitarbeiterArbeitetAnProjekt" +
                            " values (?, ?, ?, ?)");
            pstmtInsert.setLong(1, wo.getEmployee().getPersonnelNumber());
            pstmtInsert.setString(2, wo.getProject().getProjectId());
            pstmtInsert.setString(3, wo.getJob());
            pstmtInsert.setDouble(4, wo.getPercentage());
            pstmtInsert.execute();
        }
    }

    // Delete an WorksOn Object from Table "MitarbeiterArbeitetAnProjekt"
    public void delete(WorksOn wo) throws Exception {
        // TODO undone
        throw new RuntimeException("Noch net fetisch!");
    }
}
