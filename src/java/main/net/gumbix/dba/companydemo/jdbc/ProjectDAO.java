package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Project;

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

    public WeakHashMap<Long, Project> cache
            = new WeakHashMap<Long, Project>();

    public ProjectDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an Project Objects from Table "Projekt" reference to the personnel number
    public Project load(long projNr) throws Exception {
        ResultSet rs =
                executeSQLQuery("select * from Projekt where projektNr = " + projNr);

        // StatusReportDAO statDAO = new StatusReportDAO();
        // WorksOnDAO woDAO = new WorksOnDAO();

        if (rs.next()) {
            Project proj = createAndCache(projNr, new Project(rs.getLong("projektNr"), rs.getString("bezeichnung")));
            proj.setStatusReport(access.loadStatusReport(proj));
            proj.setEmployees(access.loadWorksOn(proj));
            return proj;
        } else {
            throw new ObjectNotFoundException(Project.class, projNr + "");
        }
    }

    public List<Project> queryByDescription(String queryString) throws Exception {
        String s = queryString.replace('*', '%');

        ResultSet rs = executeSQLQuery("select * from Projekt" +
                " where bezeichnung like " + "'" + s + "'");

        List<Project> projects = new ArrayList<Project>();

        while (rs.next()) {
            // TODO Never do that, please revise!
            projects.add(load(rs.getLong("projektNr")));
        }

        return projects;
    }

    public void store(Project proj) throws Exception {
    }

    public void delete(Project proj) throws Exception {
        PreparedStatement pstmt =
                access.connection.prepareStatement("delete from Projekt where projektNr = ?");
        pstmt.setLong(1, proj.getProjectNr());
        pstmt.execute();
    }

    private Project createAndCache(long projNumber, Project project) {
        cache.put(projNumber, project);
        return project;
    }
}
