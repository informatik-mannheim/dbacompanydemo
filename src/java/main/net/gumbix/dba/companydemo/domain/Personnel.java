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
package net.gumbix.dba.companydemo.domain;

import net.gumbix.dba.companydemo.db.IdGenerator;

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
    private Date birthDate;
    private Address address;
    private Department department;
    private String position;
    private Personnel boss;

    public Personnel(String lastName, String firstName, Date birthDate, Address adr) {
        this(IdGenerator.generator.getNextLong(Personnel.class),
                lastName, firstName, birthDate, adr);
    }

    public Personnel(long personnelNumber, String lastName, String firstName, Date birthDate, Address adr) {
        this.personnelNumber = personnelNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public boolean equals(Object other) {
        if (other == null || !(other instanceof Personnel)) {
            return false;
        } else {
            Personnel otherObject = (Personnel) other;
            return getPersonnelNumber() == otherObject.getPersonnelNumber();
        }
    }

    public String toString() {
        return firstName + " " + lastName + " (" + personnelNumber + ")";
    }

    public String toFullString() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

        String base =
                "Name:       " + firstName + " " + lastName + "\n" +
                        // TODO db4o bug
                        "Geb.-Datum: " + df.format(birthDate) + "\n" +
                        "Adresse:    " + address;
        String depString = "";
        if (department != null) {
            depString = "\nAbt.:       " + department;
            depString += "\nFunktion:   " + position;
        }
        String bossString = "";
        if (boss != null) {
            bossString = "\nChef:       " + boss;
        }
        return base + depString + bossString;
    }
}