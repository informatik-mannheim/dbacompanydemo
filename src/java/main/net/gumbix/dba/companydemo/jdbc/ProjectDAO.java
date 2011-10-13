package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.gumbix.dba.companydemo.domain.Project;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class ProjectDAO extends AbstractDAO {

    public ProjectDAO(JdbcAccess access) {
        super(access);
    }

	// Loads an Project Objects from Table "Projekt" reference to the personnel number
	public Project load(long projNr) throws Exception {

		return null;
	}

	// Store or Update an Project Object in Table "Projekt"
	public void store(Project proj)throws Exception{
	}

	// Delete an Project Object from Table "Projekt"
	public void delete(Project proj)throws Exception{
	}
}
