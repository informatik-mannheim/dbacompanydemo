package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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

        ResultSet rs = executeSQLQuery("select * from Personnel p" +
                " where p.personalNr = " + personalNr);

        Personnel personnel;
        if (rs.next()) {
            if (rs.getLong("wPersNr") != 0) {
                personnel = createAndCache(personalNr, new Worker());
                ((Worker) personnel).setWorkspace(rs.getString("arbeitsplatz"));
            } else if (rs.getLong("aPersNr") != 0) {
                personnel = createAndCache(personalNr, new Employee());
                Employee employee = (Employee) personnel;
                employee.setPhoneNumber(rs.getString("telefonNr"));
                // Try to assign a company car:
                ResultSet rsC = executeSQLQuery("select * from Firmenwagen f" +
                        " where f.personalNr = " + personalNr);
                if (rsC.next()) {
                    CompanyCar car = access.loadCompanyCar(rsC.getString("nummernschild"));
                    employee.setCar(car);
                }
                rsC.close();
                // Try to load projects:
                Set<WorksOn> wOns = access.loadWorksOn(employee);
                employee.setProjects(wOns);
            } else {
                personnel = createAndCache(personalNr, new Personnel());
            }
            personnel.setLastName(rs.getString("nachname"));
            personnel.setFirstName(rs.getString("vorname"));
            personnel.setPersonnelNumber(rs.getLong("personalNr"));
            personnel.setPosition(rs.getString("funktion"));

            Address adr = new Address();
            adr.setStreet(rs.getString("strasse"));
            adr.setHouseNumber(rs.getString("hausNr"));
            adr.setZip(rs.getString("plz"));
            adr.setCity(rs.getString("ortsname"));
            personnel.setBirthDate(rs.getDate("gebDatum"));

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

    // Store or Update an Personnel Object in Table "Mitarbeiter"
    public void store(Personnel pers) throws Exception {

        // TODO consider cache!

        /*
        PreparedStatement pstmt;

        if (pers.getPersonnelNumber() == 0) {

            // new record
            pstmt = connection.prepareStatement("insert into Mitarbeiter values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            pstmt.setLong(1, pers.getPersonnelNumber());
            pstmt.setString(2, pers.getFirstName());
            pstmt.setString(3, pers.getLastName());
            pstmt.setString(4, pers.getStreet());
            pstmt.setString(5, pers.getHouseNo());
            pstmt.setString(6, pers.getAddress().getZip());
            pstmt.setDate(7, new java.sql.Date(pers.getBirthDate().getTimeInMillis()));
            pstmt.setObject(8, pers.getDepartment());
            pstmt.setString(9, pers.getPosition());
            pstmt.setObject(10, pers.getBoss());
            pstmt.execute();

        } else {

            // update
            pstmt = connection.prepareStatement("update Mitarbeiter set abteilungsNr = ?, funktion = ?, vorgesetzterNr = ? where personalNr = ?");
            pstmt.setLong(1, pers.getDepartment().getDepNumber());
            pstmt.setString(2, pers.getPosition());
            pstmt.setLong(3, pers.getBoss().getPersonnelNumber());
            pstmt.setLong(4, pers.getPersonnelNumber());
            pstmt.execute();

        }
        */
    }

    // Delete an Personnel Object from Table "Mitarbeiter"
    public void delete(Personnel personnel) throws Exception {
        PreparedStatement pstmt =
                access.connection.prepareStatement("delete from Mitarbeiter where personalNr = ?");
        pstmt.setLong(1, personnel.getPersonnelNumber());
        pstmt.execute();
    }

    private Personnel createAndCache(long personalNr, Personnel personnel) {
        cache.put(personalNr, personnel);
        personnel.setPersonnelNumber(personalNr);
        return personnel;
    }
}
