package net.gumbix.dba.companydemo.domain;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 */
public class WorksOn {
    private Employee employee;
    private Project project;
    private double percentage;
    private String job;


    public WorksOn() {
    }

    public WorksOn(Employee employee, Project project, double percentage, String job) {
        setEmployee(employee);
        setProject(project);
        this.percentage = percentage;
        this.job = job;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        employee.addProject(this);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        project.addEmployee(this);
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

    public String toString() {
        return employee + " arbeitet an " + project;
    }

    public String toFullString() {
        return toString();
    }
}
