package net.gumbix.dba.companydemo.domain;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class WorksOn {
	private Personnel employee;
	//    private Employee employee;
	private Project project;
	private double percentage;
	private String job;


	public WorksOn() {
	}

	public WorksOn(Personnel employee, Project project, double percentage, String job) {
		this.employee = employee;
		this.project = project;
		this.percentage = percentage;
		this.job = job;
	}

	public Personnel getEmployee() {
		return employee;
	}

	public void setEmployee(Personnel employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}


}
