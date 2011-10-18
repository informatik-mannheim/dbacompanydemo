package net.gumbix.dba.companydemo.db4o;

import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;

/**
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
				System.out.println("Exception. Restarting server.");
			}
		}
	}
}