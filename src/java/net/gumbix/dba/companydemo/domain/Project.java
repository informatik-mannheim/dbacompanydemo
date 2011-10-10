package net.gumbix.dba.companydemo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class Project {

	private long projectNr;
	private String description;
	private List<StatusReport> StatusReport;
	private Set<WorksOn> employees;

	public Project() {
		this.StatusReport = new ArrayList<StatusReport>();
	}

	public Project(long projectNr, String description) {
		this.StatusReport = new ArrayList<StatusReport>();
		this.employees = new HashSet<WorksOn>();
		this.projectNr = projectNr;
		this.description = description;
	}

	public long getProjectNr() {
		return projectNr;
	}

	public void setProjectNr(long projectNr) {
		this.projectNr = projectNr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<StatusReport> getStatusReport() {
		return StatusReport;
	}

	public void setStatusReport(List<StatusReport> statusReport) {
		this.StatusReport = statusReport;
	}

	public void setStatusReport(StatusReport statusReport) {
		this.StatusReport.add(statusReport);
	}

	public Set<WorksOn> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<WorksOn> employees) {
		this.employees = employees;
	}

	public void setEmployees(WorksOn employees) {
		this.employees.add(employees);
	}

}
