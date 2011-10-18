package net.gumbix.dba.companydemo.db;

public abstract class IdGenerator {

	public static IdGenerator generator ;
	
	public abstract long getNextLong(Class clazz);
}
