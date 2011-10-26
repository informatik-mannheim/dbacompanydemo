package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author Patrick Sturm
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

            if (rs.getLong("wPersNr") != 0) {
                String workplace = rs.getString("arbeitsplatz");
                Worker newWorker =
                        new Worker(personalNr, lastName, firstName, birthDate, adr, workplace);
                personnel = createAndCache(personalNr, newWorker);
            } else if (rs.getLong("aPersNr") != 0) {
                String telephone = rs.getString("telefonNr");
                Employee newEmployee =
                        new Employee(personalNr, lastName, firstName, birthDate, adr, telephone);
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

            long depNr = rs.getLong("abteilungsNr");
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

            // update TODO fields missing
            PreparedStatement pstmt =
                    prepareStatement("update Mitarbeiter set vorname = ?, " +
                            " set nachname = ?, set strasse = ?, " +
                            " set hausNr = ?, set plz = ?, " +
                            " set gebDatum = ?, set abteilungsNr = ?, " +
                            " funktion = ?, vorgesetzterNr = ? " +
                            " where personalNr = ?");
            pstmt.setString(1, pers.getFirstName());
            pstmt.setString(2, pers.getLastName());
            pstmt.setString(3, pers.getAddress().getStreet());
            pstmt.setString(4, pers.getAddress().getHouseNumber());
            pstmt.setString(5, pers.getAddress().getZip());
            pstmt.setDate(6, new java.sql.Date(pers.getBirthDate().getTime()));
            pstmt.setLong(7, pers.getDepartment().getDepNumber());
            pstmt.setString(8, pers.getPosition());
            pstmt.setLong(9, pers.getBoss().getPersonnelNumber());
            pstmt.execute();
        } catch (ObjectNotFoundException e) {
            // new
            PreparedStatement pstmt =
                    prepareStatement("insert into Mitarbeiter values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            pstmt.setLong(1, pers.getPersonnelNumber());
            pstmt.setString(2, pers.getFirstName());
            pstmt.setString(3, pers.getLastName());
            pstmt.setString(4, pers.getAddress().getStreet());
            pstmt.setString(5, pers.getAddress().getHouseNumber());
            pstmt.setString(6, pers.getAddress().getZip());
            pstmt.setDate(7, new java.sql.Date(pers.getBirthDate().getTime()));
            pstmt.setObject(8, pers.getDepartment());
            pstmt.setString(9, pers.getPosition());
            pstmt.setObject(10, pers.getBoss());
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
        long next = rs.getLong(1);
        rs.close();
        return next;
    }

    private Personnel createAndCache(long personalNr, Personnel personnel) {
        cache.put(personalNr, personnel);
        personnel.setPersonnelNumber(personalNr);
        return personnel;
    }
}
