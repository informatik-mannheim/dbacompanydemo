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

import net.gumbix.dba.companydemo.application.process.ProjectStatusEnum;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.ProjectStatus;
import net.gumbix.dba.companydemo.domain.StatusReport;
import net.gumbix.dba.companydemo.domain.WorksOn;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class ProjectDAO extends AbstractDAO {

    public WeakHashMap<String, Project> cache
            = new WeakHashMap<String, Project>();

    public ProjectDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an Project Objects from Table "Projekt" reference to the personnel number
    public Project load(String projectId) throws Exception {
        ResultSet rs =
                executeSQLQuery("select * from Projekt where projektId = '" + projectId + "'");

        if (rs.next()) {
            Project proj = createAndCache(projectId, new Project(rs.getString("projektId"), rs.getString("bezeichnung")));
            for (StatusReport statusReport : access.loadStatusReport(proj)) {
                proj.addStatusReport(statusReport);
            }
            for (WorksOn worksOn : access.loadWorksOn(proj)) {
                proj.addEmployee(worksOn);
            }
            ProjectStatus status = access.loadProjectStatus(ProjectStatusEnum.valueOf(rs.getString("statusId")));
            proj.setStatus(status);
            Field nextReportNumber = Project.class.getField("nextStatusReportNumber");
            nextReportNumber.setAccessible(true);
            nextReportNumber.setLong(proj, rs.getLong("naechsteStatusNummer"));
            return proj;
        } else {
            throw new ObjectNotFoundException(Project.class, projectId + "");
        }
    }

    public List<Project> queryByDescription(String queryString) throws Exception {
        String s = queryString.replace('*', '%');

        ResultSet rs = executeSQLQuery("select * from Projekt" +
                " where bezeichnung like " + "'" + s + "'");

        List<Project> projects = new ArrayList<Project>();

        while (rs.next()) {
            // TODO Never do that, please revise!
            projects.add(load(rs.getString("projektId")));
        }

        return projects;
    }

    public void store(Project proj) throws Exception {
        PreparedStatement pstmt;
        try {
            Project project = load(proj.getProjectId());
            // update
            pstmt = prepareStatement("update Projekt set bezeichnung = ?, " +
                    " naechsteStatusNummer = ? where projektId = ?");
            pstmt.setString(1, proj.getDescription());
            pstmt.setLong(2, proj.getNextStatusReportNumber());
            pstmt.setString(3, proj.getProjectId());
            pstmt.execute();
        } catch (ObjectNotFoundException e) {
            // new record
            pstmt = prepareStatement("insert into Projekt values (?, ?, ?)");
            pstmt.setString(1, proj.getProjectId());
            pstmt.setString(2, proj.getDescription());
            pstmt.setLong(3, proj.getNextStatusReportNumber());
            //TODO set initial status New
            //pstmt.setString(3, ProjectStatus.New.toString());
            pstmt.execute();
        }
    }

    public void delete(Project proj) throws Exception {
        PreparedStatement pstmt =
                access.connection.prepareStatement("delete from Projekt where projektId = ?");
        pstmt.setString(1, proj.getProjectId());
        pstmt.execute();
    }

    private Project createAndCache(String projectId, Project project) {
        cache.put(projectId, project);
        return project;
    }
}
