package net.gumbix.dba.companydemo.domain;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @auhtor Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class StatusReport {

    private long continuousNumber;
    private Date date;
    private String content;
    private Project project;

    /**
     * Create a new status report. A unique ID is assigned automatically.
     * Today is used as the report's date.
     *
     * @param content
     * @param project
     */
    public StatusReport(String content, Project project) {
        this(new Date(), content, project);
    }

    /**
     * Create a new status report. A unique ID is assigned automatically.
     *
     * @param date
     * @param content
     * @param project
     */
    public StatusReport(Date date, String content, Project project) {
        this(project.getNextStatusReportNumber(), date, content, project);
    }

    public StatusReport(long continuousNumber, Date date, String content,
                        Project project) {
        this.continuousNumber = continuousNumber;
        this.date = date;
        this.content = content;
        this.project = project;

        project.addStatusReport(this);
    }

    public long getContinuousNumber() {
        return continuousNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Project getProject() {
        return project;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof StatusReport)) {
            return false;
        } else {
            StatusReport otherObject = (StatusReport) other;
            return getProject().equals(otherObject.getProject()) &&
                    continuousNumber == otherObject.getContinuousNumber();
        }
    }

    public String toString() {
        return "Statusreport " + project.getProjectId() + "." + continuousNumber;
    }

    public String toFullString() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

        return "Nummer:  " + project.getProjectId() + "." + continuousNumber +
                "\nDatum:   " + df.format(date)
                + "\nInhalt:  " + content + "\nProjekt: " + project;
    }
}
