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
package net.gumbix.dba.companydemo.db;

/**
 * This interface provides new unique IDs for a specific class.
 * Such a generator is required by DB4O for instance
 * within the <code>Personnel</code>
 * class as this class does not have a public key.
 * (c) 2012 by Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Markus Gumbel
 */
public abstract class IdGenerator {

    /**
     * Singleton method.
     */
	public static IdGenerator generator ;

    /**
     * Get the next available ID for the given class.
     * @param clazz
     * @return
     */
	public abstract long getNextLong(Class clazz);
}
