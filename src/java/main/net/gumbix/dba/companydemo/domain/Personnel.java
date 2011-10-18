package net.gumbix.dba.companydemo.domain;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class Personnel {

    private long personnelNumber;
    private String lastName;
    private String firstName;
    private java.util.Date birthDate;
    private Address address;
    private Department department;
    private String position;
    private Personnel boss;


    public Personnel() {
    }

    public Personnel(long personnelNumber, String lastName, String firstName,
                     GregorianCalendar birthDate, Address adr) {
        this.personnelNumber = personnelNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        if (birthDate != null) {
            this.birthDate = birthDate.getTime();
        }
        this.address = adr;
    }

    public long getPersonnelNumber() {
        return personnelNumber;
    }

    public void setPersonnelNumber(long personnelNumber) {
        this.personnelNumber = personnelNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public GregorianCalendar getBirthDate() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(birthDate);
        return cal;
    }

    public void setBirthDate(GregorianCalendar birthDate) {
        this.birthDate = birthDate.getTime();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Personnel getBoss() {
        return boss;
    }

    public void setBoss(Personnel boss) {
        this.boss = boss;
    }

    public String toString() {
        return firstName + " " + lastName + " (" + personnelNumber + ")";
    }

    public String toFullString() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

        String base =
                "Name:       " + firstName + " " + lastName + "\n" +
                        // TODO db4o bug
                        // "Geb.-Datum: " + df.format(birthDate.getTime()) + "\n" +
                        "Adresse:    " + address;
        String depString = "";
        if (department != null) {
            depString = "\nAbt.:       " + department;
            depString += "\nFunktion:   " + position;
        }
        String bossString = "";
        if (boss != null) {
            bossString = "\nChef: " + boss;
        }
        return base + depString + bossString;
    }
}