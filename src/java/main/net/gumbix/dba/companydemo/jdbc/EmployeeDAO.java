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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class EmployeeDAO extends PersonnelDAO {

    public EmployeeDAO(JdbcAccess access) {
        super(access);
    }

    public void store(Employee employee) throws Exception {
        // First create an entry in table Mitarbeiter:
        super.store(employee);

        ResultSet rs =
                executeSQLQuery("select * from Angestellter" +
                        " where personalNr = " + employee.getPersonnelNumber());
        if (rs.next()) {
            // update
            PreparedStatement pstmt =
                    prepareStatement("update Angestellter set telefonNr = ?" +
                            " where personalNr = ?");
            pstmt.setString(1, employee.getPhoneNumber());
            pstmt.setLong(2, employee.getPersonnelNumber());
            pstmt.execute();
            pstmt.close();
        } else {
            // new
            PreparedStatement pstmt =
                    prepareStatement("insert into Angestellter values (?, ?)");
            pstmt.setLong(1, employee.getPersonnelNumber());
            pstmt.setString(2, employee.getPhoneNumber());
            pstmt.execute();
            pstmt.close();
        }
    }

    public void delete(Employee employee) throws Exception {
        PreparedStatement pstmt =
                prepareStatement("delete from Angestellter where personalNr = ?");
        pstmt.setLong(1, employee.getPersonnelNumber());
        pstmt.execute();
        pstmt.close();

        super.delete(employee);
    }
}
