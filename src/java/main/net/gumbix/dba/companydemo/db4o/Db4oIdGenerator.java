package net.gumbix.dba.companydemo.db4o;

import net.gumbix.dba.companydemo.db.IdGenerator;

public class Db4oIdGenerator extends IdGenerator {

	static {
		generator = new Db4oIdGenerator();
	}

	protected long next = 0;

	public Db4oIdGenerator() {
	}

	public long getNextLong(Class clazz) {
		next++;
		return next;
	}
}
