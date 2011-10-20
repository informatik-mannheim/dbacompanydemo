package net.gumbix.dba.companydemo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class Project {

    private String projectId;
    private String description;
    private List<StatusReport> statusReports = new ArrayList<StatusReport>();
    private Set<WorksOn> employees = new HashSet<WorksOn>();
    // private long nextStatusReportNumber = 1;
    public long nextStatusReportNumber = 1;  // TODO, reflection does not work yet.

    public Project(String projectId, String description) {
        this.projectId = projectId;
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StatusReport> getStatusReports() {
        return statusReports;
    }

    /**
     * Add a status report.
     * @param statusReport
     * @return False if report was already added, true if not.
     */
    public boolean addStatusReport(StatusReport statusReport) {
        if (!statusReports.contains(statusReport)) {
            statusReports.add(statusReport);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeStatusReport(StatusReport statusReport) {
        return statusReports.remove(statusReport);
    }

    public Set<WorksOn> getEmployees() {
        return employees;
    }

    public boolean addEmployee(WorksOn worksOn) {
        return employees.add(worksOn);
    }

    public long getNextStatusReportNumber() {
        nextStatusReportNumber++;
        return nextStatusReportNumber;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof Project)) {
            return false;
        } else {
            Project otherObject = (Project) other;
            return getProjectId().equals(otherObject.getProjectId());
        }
    }

    public String toString() {
        return description + " (" + projectId + ")";
    }

    public String toFullString() {
        return toString();
    }
}
