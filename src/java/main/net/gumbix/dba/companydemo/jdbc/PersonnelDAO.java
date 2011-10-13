package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Worker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Patrick Sturm
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class PersonnelDAO extends AbstractDAO {

    public PersonnelDAO(JdbcAccess access) {
        super(access);
    }

    public Personnel load(long personalNr) throws Exception {

        ResultSet rs = executeSQLQuery("select * from Personnel p" +
                " where p.personalNr = " + personalNr);

        Personnel personnel;
        Address adr = new Address();
        GregorianCalendar birthDate = new GregorianCalendar();

        if (rs.next()) {

            if (rs.getLong("wPersNr") != 0) {
                personnel = new Worker();
                ((Worker) personnel).setWorkspace(rs.getString("arbeitsplatz"));
            } else if (rs.getLong("aPersNr") != 0) {
                personnel = new Employee();
                ((Employee) personnel).setPhoneNumber(rs.getString("telefonNr"));
            } else {
                personnel = new Personnel();
            }
            personnel.setLastName(rs.getString("nachname"));
            personnel.setFirstName(rs.getString("vorname"));
            personnel.setPersonnelNumber(rs.getLong("personalNr"));
            personnel.setPosition(rs.getString("funktion"));
            personnel.setStreet(rs.getString("strasse"));
            personnel.setHouseNo(rs.getString("hausNr"));
            adr.setZip(rs.getString("plz"));
            adr.setCity(rs.getString("ortsname"));
            birthDate.setTime(rs.getDate("gebDatum"));
            personnel.setBirthDate(birthDate);

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
        return personnel;
    }

    // Loads an list of Personnel Objects from Table "Mitarbeiter, Ort" using the first name and last name
    public List<Personnel> load(String firstName, String lastName) throws Exception {

        /*
        firstName = firstName.replace('*', '%');
        lastName = lastName.replace('*', '%');

        ResultSet rs = executeSQLQuery("SELECT * FROM Mitarbeiter,Ort WHERE Mitarbeiter.plz = Ort.plz and vorname like " + "'" +
                firstName + "'" + " AND nachname like " + "'" + lastName + "'");


        List<Personnel> persons = new ArrayList<Personnel>();

        while (rs.next()) {

            Personnel p = new Personnel();
            Address adr = new Address();
            GregorianCalendar birthDate = new GregorianCalendar();

            p.setLastName(rs.getString("nachname"));
            p.setFirstName(rs.getString("vorname"));
            p.setPersonnelNumber(rs.getLong("personalNr"));
            p.setPosition(rs.getString("funktion"));
            p.setStreet(rs.getString("strasse"));
            p.setHouseNo(rs.getString("hausNr"));
            adr.setZip(rs.getString("plz"));
            adr.setCity(rs.getString("ortsname"));
            birthDate.setTime(rs.getDate("gebDatum"));
            p.setBirthDate(birthDate);

            if (rs.getLong("abteilungsNr") != 0) {
                p.setDepartment(new DepartmentDAO().load(rs.getLong("abteilungsNr")));
            }

            if (rs.getLong("vorgesetzterNr") != 0) {
                p.setBoss(new PersonnelDAO().load(rs.getLong("vorgesetzterNr")));
            }

            p.setAddress(adr);
            persons.add(p);

        }

        return persons;
        */
        return null;
    }

    // Store or Update an Personnel Object in Table "Mitarbeiter"
    public void store(Personnel pers) throws Exception {

        /*
        PreparedStatement pstmt;

        AddressDAO adrDAO = new AddressDAO();
        adrDAO.store(pers.getAddress());

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
    public void delete(long personalNr) throws Exception {
        PreparedStatement pstmt =
                access.connection.prepareStatement("delete from Mitarbeiter where personalNr = ?");
        pstmt.setLong(1, personalNr);
        pstmt.execute();
    }
}
