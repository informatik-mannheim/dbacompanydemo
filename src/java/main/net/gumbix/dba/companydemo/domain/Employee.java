package net.gumbix.dba.companydemo.domain;

import com.db4o.collections.ActivatableHashSet;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class Employee extends Personnel {

	private String phoneNumber;
	private CompanyCar car;
	private Set<WorksOn> projects = new HashSet<WorksOn>();

	public Employee() {
	}

	public Employee(long personnelNumber, String lastName, String firstName,
                    GregorianCalendar birthDate, Address adr, String tel) {
		super(personnelNumber, lastName, firstName, birthDate, adr);
		this.phoneNumber = tel;
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

    public void addProject(WorksOn worksOn) {
        projects.add(worksOn);
    }

    public String toString() {
        return getFirstName() + " " + getLastName() +
                " (" + getPersonnelNumber() + " " + "Angestellter)";
    }

    public String toFullString() {
        String s =  super.toFullString() + "\n" +
               "Telefon:    " + phoneNumber;
        if (car != null) {
            s += "\nAuto:       " + car;
        }
        if (!projects.isEmpty()) {
            s += "\nProjekte:    ";
            for (WorksOn w : projects) {
                s += w.getProject() + "; ";
            }
        }
        return s;
    }
}
