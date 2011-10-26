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

import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;

/**
 * Start a db4o server for the company demo.
 *
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class DB4OServer {
    public static void main(String[] args) {
        while (true) {
            try {
                ServerConfiguration config = Db4oClientServer
                        .newServerConfiguration();
                ObjectServer server = Db4oClientServer.openServer(config,
                        "firmenwelt.yap", 8732);
                server.grantAccess("firmenwelt", "firmenwelt10");

                // Thread.currentThread.wait() does not work:
                System.out.println("Restartable Server started.");
                while (true) {
                    Thread.sleep(9999999);
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Exception. Restarting server.");
            }
        }
    }
}