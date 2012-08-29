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

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class Department {

    private long depNumber;
    private String name;

    // Required by Hibernate only.
    // TODO must be public for Hibernate Javassist
    public Department() {}

    public Department(long depNumber, String name) {
        this.depNumber = depNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDepNumber() {
        return depNumber;
    }

    // Required by Hibernate. Private to avoid any modifications.
    private void setDepNumber(long number) {
        this.depNumber = number;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof Department)) {
            return false;
        } else {
            Department otherObject = (Department) other;
            return getDepNumber() == otherObject.getDepNumber();
        }
    }

    public String toString() {
        return depNumber + ": " + name;
    }
}
