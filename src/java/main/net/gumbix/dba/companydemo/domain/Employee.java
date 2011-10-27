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

import EDU.purdue.cs.bloat.reflect.Catch;
import net.gumbix.dba.companydemo.db.IdGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class Employee extends Personnel {

    private String phoneNumber;
    private CompanyCar car;
    private Set<WorksOn> projects = new HashSet<WorksOn>();

    public Employee(String lastName, String firstName,
                    Date birthDate, Address adr, String tel) {
        this(IdGenerator.generator.getNextLong(Personnel.class),
                lastName, firstName, birthDate, adr, tel);
    }

    public Employee(long personnelNumber, String lastName, String firstName,
                    Date birthDate, Address adr, String tel) {
        super(personnelNumber, lastName, firstName, birthDate, adr);
        this.phoneNumber = tel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CompanyCar getCar() {
        return car;
    }

    public void setCar(CompanyCar car) {
        this.car = car;
        if (car != null && car.getDriver() != this) {
            car.setDriver(this);
        }
    }

    public Set<WorksOn> getProjects() {
        return projects;
    }

    /**
     * Add a project.
     *
     * @param worksOn
     * @return False if project was already added, true if not.
     */
    public boolean addProject(WorksOn worksOn) {
        return projects.add(worksOn);
    }

    public String toString() {
        return getFirstName() + " " + getLastName() +
                " (" + getPersonnelNumber() + " " + "Angestellter)";
    }

    public String toFullString() {
        String s = super.toFullString() + "\n" +
                "Telefon:    " + phoneNumber;
        if (car != null) {
            s += "\nAuto:       " + car;
        }
        if (!projects.isEmpty()) {
            s += "\nProjekte:   ";
            for (WorksOn w : projects) {
                s += w.getProject() + "; ";
            }
        }
        return s;
    }
}
