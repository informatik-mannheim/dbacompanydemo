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
import java.util.List;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Worker;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class WorkerDAO extends PersonnelDAO {

    public WorkerDAO(JdbcAccess access) {
        super(access);
    }

    public void store(Worker worker) throws Exception {
        // First create an entry in table Mitarbeiter:
        super.store(worker);

        // Then also fill in the data for the worker:
        ResultSet rs =
                executeSQLQuery("select * from Arbeiter" +
                        " where personalNr = " + worker.getPersonnelNumber());

        if (rs.next()) {
            // update
            PreparedStatement pstmt =
                    prepareStatement("update Arbeiter set arbeitsplatz = ? " +
                            " where personalNr = ?");
            pstmt.setString(1, worker.getWorkspace());
            pstmt.setLong(2, worker.getPersonnelNumber());
            pstmt.execute();
            pstmt.close();
        } else {
            // new
            PreparedStatement pstmt =
                    prepareStatement("insert into Arbeiter values (?, ?)");
            pstmt.setLong(1, worker.getPersonnelNumber());
            pstmt.setString(2, worker.getWorkspace());
            pstmt.execute();
            pstmt.close();
        }
    }

    public void delete(Worker worker) throws Exception {
        PreparedStatement pstmt =
                prepareStatement("delete from Arbeiter where personalNr = ?");
        pstmt.setLong(1, worker.getPersonnelNumber());
        pstmt.execute();
        pstmt.close();

        super.delete(worker);
    }
}
