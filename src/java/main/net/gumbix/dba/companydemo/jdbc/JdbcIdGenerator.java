/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011-2023 the authors listed below.

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

import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.domain.Personnel;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class JdbcIdGenerator extends IdGenerator {

    private JdbcAccess access;

    public JdbcIdGenerator(JdbcAccess access) {
        this.access = access;
    }

    public long getNextLong(Class clazz) {
        try {
            if (clazz.equals(Personnel.class)) {
                return access.nextPersonnelId();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Unknown class: " + clazz);
    }
}
