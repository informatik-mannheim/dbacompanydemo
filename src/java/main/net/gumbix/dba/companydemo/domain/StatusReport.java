/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011-2023 the authors listed below.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package net.gumbix.dba.companydemo.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @auhtor Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class StatusReport implements Serializable {

    private long continuousNumber;
    private Date date;
    private String content;
    private Project project;

    public StatusReport() {}

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

    // TODO Hibernate
    public void setContinuousNumber(long number) {
        continuousNumber = number;
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

    // TODO Hibernate
    public void setProject(Project project) {
        this.project = project;
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
