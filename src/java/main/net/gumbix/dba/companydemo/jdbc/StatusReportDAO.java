/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011-2023 the authors listed below.

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.StatusReport;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class StatusReportDAO extends AbstractDAO {

    public StatusReportDAO(JdbcAccess access) {
        super(access);
    }

    public StatusReport load(Project project, long continuousNumber) throws Exception {
        ResultSet rs = executeSQLQuery("select * from Statusbericht " +
                " where projektId = '" + project.getProjectId() + "' " +
                " and fortlaufendeNr = " + continuousNumber);

        if (rs.next()) {
            Date date = rs.getDate("datum");
            String content = rs.getString("inhalt");
            return new StatusReport(continuousNumber, date, content, project);
        } else {
            throw new ObjectNotFoundException(StatusReport.class,
                    project.getProjectId() + "." + continuousNumber);
        }
    }

    public List<StatusReport> load(Project project) throws Exception {

        List<StatusReport> staList = new ArrayList<StatusReport>();

        ResultSet rs = executeSQLQuery("select * from Statusbericht " +
                " where projektId = '" + project.getProjectId() + "'");

        while (rs.next()) {
            long contNumber = rs.getLong("fortlaufendeNr");
            String content = rs.getString("inhalt");
            Date date = rs.getDate("datum");
            StatusReport sta = new StatusReport(contNumber, date, content, project);
            staList.add(sta);
        }
        return staList;
    }

    public void store(StatusReport statusReport) throws Exception {
        // Also store project because of the next status number:
        access.storeProject(statusReport.getProject());
        PreparedStatement pstmt;
        try {
            StatusReport report =
                    load(statusReport.getProject(), statusReport.getContinuousNumber());
            // update
            pstmt = prepareStatement("update Statusbericht set datum = ?, " +
                    " inhalt = ? where projektNr = ? and fortlaufendeNr = ?");
            pstmt.setDate(1, new java.sql.Date(statusReport.getDate().getTime()));
            pstmt.setString(2, statusReport.getContent());
            pstmt.setString(3, statusReport.getProject().getProjectId());
            pstmt.setLong(4, statusReport.getContinuousNumber());
            pstmt.execute();
        } catch (ObjectNotFoundException e) {
            // new record
            pstmt = prepareStatement("insert into Statusbericht values (?, ?, ?, ? )");
            pstmt.setString(1, statusReport.getProject().getProjectId());
            pstmt.setLong(2, statusReport.getContinuousNumber());
            pstmt.setDate(3, new java.sql.Date(statusReport.getDate().getTime()));
            pstmt.setString(4, statusReport.getContent());
            pstmt.execute();
        }
    }

    public void delete(StatusReport statusReport) throws Exception {
        // TODO undone
        throw new RuntimeException("Noch net fetisch!");
    }
}
