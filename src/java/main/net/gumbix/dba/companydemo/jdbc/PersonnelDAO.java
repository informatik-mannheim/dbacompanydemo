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

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author Patrick Sturm (patrick-sturm@gmx.net)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class PersonnelDAO extends AbstractDAO {

    public WeakHashMap<Long, Personnel> cache
            = new WeakHashMap<Long, Personnel>();

    public PersonnelDAO(JdbcAccess access) {
        super(access);
    }

    public Personnel load(long personalNr) throws Exception {
        // Try to find it in the cache first:
        Personnel e = cache.get(personalNr);
        if (e != null) {
            return e;
        }

        ResultSet rs = executeSQLQuery("select * from MitarbeiterAlleKlassen p" +
                " where p.personalNr = " + personalNr);

        Personnel personnel;

        if (rs.next()) {
            String lastName = rs.getString("nachname");
            String firstName = rs.getString("vorname");
            Address adr = new Address();
            adr.setStreet(rs.getString("strasse"));
            adr.setHouseNumber(rs.getString("hausNr"));
            adr.setZip(rs.getString("plz"));
            adr.setCity(rs.getString("ortsname"));
            Date birthDate = rs.getDate("gebDatum");
            double salary = rs.getDouble("gehalt");

            if (rs.getLong("wPersNr") != 0) {
                String workplace = rs.getString("arbeitsplatz");
                Worker newWorker =
                        new Worker(personalNr, lastName, firstName, birthDate, adr, workplace);
                newWorker.setSalary(salary);
                personnel = createAndCache(personalNr, newWorker);
            } else if (rs.getLong("aPersNr") != 0) {
                String telephone = rs.getString("telefonNr");
                Employee newEmployee =
                        new Employee(personalNr, lastName, firstName, birthDate, adr, telephone);
                newEmployee.setSalary(salary);
                personnel = createAndCache(personalNr, newEmployee);
                // Try to assign a company car:
                ResultSet rsC = executeSQLQuery("select * from Firmenwagen f" +
                        " where f.personalNr = " + personalNr);
                if (rsC.next()) {
                    CompanyCar car = access.loadCompanyCar(rsC.getString("nummernschild"));
                    newEmployee.setCar(car);
                }
                rsC.close();
                // Try to load projects:
                Set<WorksOn> wOns = access.loadWorksOn(newEmployee);
                for (WorksOn wOn : wOns) {
                    newEmployee.addProject(wOn);
                }
            } else {
                Personnel newPersonnel =
                        new Personnel(personalNr, lastName, firstName, birthDate, adr);
                personnel = createAndCache(personalNr, newPersonnel);
            }
            personnel.setPosition(rs.getString("funktion"));

            long depNr = rs.getLong("abteilungsId");
            if (depNr != 0) {
                personnel.setDepartment(access.loadDepartment(depNr));
            }

            long bossNr = rs.getLong("vorgesetzterNr");
            if (bossNr != 0) {
                // Note: we would get an infinite loop if a personnel
                // is its own boss.
                personnel.setBoss(access.loadPersonnel(bossNr));
            }
            personnel.setAddress(adr);
        } else {
            throw new ObjectNotFoundException(Personnel.class, personalNr + "");
        }

        rs.close();
        return personnel;
    }

    public List<Personnel> queryByName(String firstName, String lastName) throws Exception {

        firstName = firstName.replace('*', '%');
        lastName = lastName.replace('*', '%');

        ResultSet rs = executeSQLQuery("select * from Mitarbeiter" +
                " where vorname like " + "'" + firstName + "'" +
                " and nachname like " + "'" + lastName + "'");

        List<Personnel> persons = new ArrayList<Personnel>();

        while (rs.next()) {
            // TODO Never do that, please revise!
            persons.add(load(rs.getLong("personalNr")));
        }

        return persons;
    }

    public void store(Personnel pers) throws Exception {

        // TODO consider cache!

        try {
            Personnel dep = load(pers.getPersonnelNumber());

            // update
            createZip(pers.getAddress());
            PreparedStatement pstmt =
                    prepareStatement("update Mitarbeiter set vorname = ?, " +
                            " set nachname = ?, set strasse = ?, " +
                            " set hausNr = ?, set plz = ?, " +
                            " set gebDatum = ?, set gehalt = ?, " +
                            " set abteilungsNr = ?, " +
                            " funktion = ?, vorgesetzterNr = ? " +
                            " where personalNr = ?");
            pstmt.setString(1, pers.getFirstName());
            pstmt.setString(2, pers.getLastName());
            pstmt.setString(3, pers.getAddress().getStreet());
            pstmt.setString(4, pers.getAddress().getHouseNumber());
            pstmt.setString(5, pers.getAddress().getZip());
            pstmt.setDate(6, new java.sql.Date(pers.getBirthDate().getTime()));
            pstmt.setDouble(7, pers.getSalary());
            if (pers.getDepartment() != null) {
                pstmt.setLong(8, pers.getDepartment().getDepNumber());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            pstmt.setString(9, pers.getPosition());
            if (pers.getBoss() != null) {
                pstmt.setLong(10, pers.getBoss().getPersonnelNumber());
            } else {
                pstmt.setNull(10, Types.INTEGER);
            }
            pstmt.execute();
        } catch (ObjectNotFoundException e) {
            // new
            createZip(pers.getAddress());
            PreparedStatement pstmt =
                    prepareStatement("insert into Mitarbeiter values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            pstmt.setLong(1, pers.getPersonnelNumber());
            pstmt.setString(2, pers.getFirstName());
            pstmt.setString(3, pers.getLastName());
            pstmt.setString(4, pers.getAddress().getStreet());
            pstmt.setString(5, pers.getAddress().getHouseNumber());
            pstmt.setString(6, pers.getAddress().getZip());
            pstmt.setDate(7, new java.sql.Date(pers.getBirthDate().getTime()));
            pstmt.setDouble(8, pers.getSalary());
            if (pers.getDepartment() != null) {
                pstmt.setLong(9, pers.getDepartment().getDepNumber());
            } else {
                pstmt.setNull(9, Types.INTEGER);
            }
            pstmt.setString(10, pers.getPosition());
            if (pers.getBoss() != null) {
                pstmt.setLong(11, pers.getBoss().getPersonnelNumber());
            } else {
                pstmt.setNull(11, Types.INTEGER);
            }
            pstmt.execute();
        }
    }

    public void delete(Personnel personnel) throws Exception {
        PreparedStatement pstmt =
                prepareStatement("delete from Mitarbeiter where personalNr = ?");
        pstmt.setLong(1, personnel.getPersonnelNumber());
        pstmt.execute();
        pstmt.close();
        cache.remove(personnel.getPersonnelNumber());
    }

    public long nextId() throws Exception {
        ResultSet rs = executeSQLQuery("select max(personalNr) from Mitarbeiter");
        rs.next();
        long next = rs.getLong(1) + 1;
        rs.close();
        return next;
    }

    private Personnel createAndCache(long personalNr, Personnel personnel) {
        cache.put(personalNr, personnel);
        personnel.setPersonnelNumber(personalNr);
        return personnel;
    }

    /**
     * Ensure to have a valid zip code.
     *
     * @param adr
     * @throws Exception
     */
    private void createZip(Address adr) throws Exception {
        ResultSet rs = executeSQLQuery("select * from Ort " +
                "where plz = '" + adr.getZip() + "'");
        if (!rs.next()) {
            ResultSet rs2 = executeSQLQuery("insert into Ort " +
                    "values ('" + adr.getZip() +
                    "', '" + adr.getCity() + "')");
            if (rs2 != null) rs2.close();
        }
        rs.close();
    }
}
