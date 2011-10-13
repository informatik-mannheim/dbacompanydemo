package net.gumbix.dba.companydemo.domain;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class Employee extends Personnel {

	private String phoneNumber;
	private CompanyCar car;
	private Set<WorksOn> projects;

	public Employee() {

	}

	public Employee(String name, String vorname, GregorianCalendar gebDatum, Address adr, String tel) {
		super(name, vorname, gebDatum, adr);
		this.phoneNumber = tel;
		this.projects = new HashSet<WorksOn>();

	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public CompanyCar getCar() {
		return car;
	}

	public void setCar(CompanyCar car) {
		this.car = car;
        if (car.getDriver() != this) {
            car.setDriver(this);
        }
	}

	public Set<WorksOn> getProjects() {
		return projects;
	}

	public void setProjects(Set<WorksOn> projects) {
		this.projects = projects;
	}

	public void setProjects(WorksOn projects) {
		this.projects.add(projects);
	}
}
