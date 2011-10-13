package net.gumbix.dba.companydemo.jdbc;

import java.sql.*;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

abstract public class AbstractDAO {

    protected JdbcAccess access;

    public AbstractDAO(JdbcAccess access) {
        this.access = access;
    }

    public ResultSet executeSQLQuery(String sqlStatment) throws Exception {

        Statement s = access.connection.createStatement();
        s.execute(sqlStatment);

        return s.getResultSet();
    }

    public PreparedStatement prepareStatement(String prepSqlStatment) throws Exception {
        return access.connection.prepareStatement(prepSqlStatment);
    }
}
