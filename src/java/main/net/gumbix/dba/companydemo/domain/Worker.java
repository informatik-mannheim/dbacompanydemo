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
import org.hibernate.jdbc.Work;

import java.util.Date;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class Worker extends Personnel {

    private String workspace;

    public Worker() {}

    public Worker(String lastName, String firstName,
                  Date birthDate, Address adr, String workspace) {
        this(IdGenerator.generator.getNextLong(Personnel.class),
                lastName, firstName, birthDate, adr, workspace);
    }

    public Worker(long personnelNumber, String lastName, String firstName,
                  Date birthDate, Address adr, String workspace) {
        super(personnelNumber, lastName, firstName, birthDate, adr);
        setWorkspace(workspace);
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String toString() {
        return getFirstName() + " " + getLastName() +
                " (" + getPersonnelNumber() + " " + "Arbeiter)";
    }

    public String toFullString() {
        return super.toFullString() + "\n" +
                "Arbeitsplatz: " + workspace;
    }
}
