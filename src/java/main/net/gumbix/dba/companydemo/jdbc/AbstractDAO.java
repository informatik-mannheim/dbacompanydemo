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
