package net.gumbix.dba.companydemo.mongodb;

import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.domain.Personnel;

public class MongoDbIdGerator extends IdGenerator {

	static {
		generator = new MongoDbIdGerator();
	}

	private long nextPersonnel = 0;

	public MongoDbIdGerator() {
	}

	@Override
	public long getNextLong(Class clazz) {

		nextPersonnel++;
		return nextPersonnel;

		// if (clazz.equals(Personnel.class)) {
		// nextPersonnel++;
		// return nextPersonnel;
		// }
		// throw new IllegalArgumentException("Unknown class: " + clazz);
	}

}
