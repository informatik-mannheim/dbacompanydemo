package net.gumbix.dba.companydemo.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.WeakHashMap;

import net.gumbix.dba.companydemo.application.process.ProjectStatusEnum;
import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.ProjectStatus;
import net.gumbix.dba.companydemo.domain.StatusReport;
import net.gumbix.dba.companydemo.domain.WorksOn;

public class ProjectStatusDAO extends AbstractDAO {
	
	public WeakHashMap<String, ProjectStatus> cache
    	= new WeakHashMap<String, ProjectStatus>();

	public ProjectStatusDAO(JdbcAccess access) {
		super(access);
	}

	public ProjectStatus load(ProjectStatusEnum projectStatus) throws Exception {
		String statusId = projectStatus.toString();
		ResultSet rs =
                executeSQLQuery("select * from ProjektStatus where statusId = '" + statusId + "'");

        if (rs.next()) {
        	ProjectStatus status = createAndCache(statusId, new ProjectStatus(statusId, rs.getString("beschreibung")));
            return status;
        } else {
            throw new ObjectNotFoundException(ProjectStatus.class, projectStatus + "");
        }
	}
	
	private ProjectStatus createAndCache(String statusId, ProjectStatus projectStatus) {
        cache.put(statusId, projectStatus);
        return projectStatus;
    }


}
