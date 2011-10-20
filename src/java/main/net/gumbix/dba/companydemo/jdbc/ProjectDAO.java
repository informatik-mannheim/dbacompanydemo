package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.StatusReport;
import net.gumbix.dba.companydemo.domain.WorksOn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            Project.class.getField("nextStatusReportNumber")
                    .setLong(proj, rs.getLong("naechsteStatusNummer"));
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
