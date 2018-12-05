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
package net.gumbix.dba.companydemo.db4o;

import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.domain.Personnel;

public class Db4oIdGenerator extends IdGenerator {

    static {
        generator = new Db4oIdGenerator();
    }

    private long nextPersonnel = 0;

    public Db4oIdGenerator() {
    }

    public long getNextLong(Class clazz) {
    	System.out.println("EEEEEEEE44444444444EEEEEEEEEEEEEEEEEEE");
        if (clazz.equals(Personnel.class)) {
            nextPersonnel++;
            return nextPersonnel;
        }
        throw new IllegalArgumentException("Unknown class: " + clazz);
    }
}
