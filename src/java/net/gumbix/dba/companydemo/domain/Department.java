package net.gumbix.dba.companydemo.domain;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class Department {

	private long depNumber;
	private String name;

	public Department() {

	}

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

	public void setDepNumber(long depNumber) {
		this.depNumber = depNumber;
	}

	public String toString() {
		return depNumber + " : " + name;
	}
}
