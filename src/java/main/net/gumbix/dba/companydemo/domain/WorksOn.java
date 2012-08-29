/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011  the authors listed below.

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

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 */
public class WorksOn implements Serializable {
    private Employee employee;
    private Project project;
    private double percentage;
    private String job;

    public WorksOn() {}

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

    public boolean equals(Object other) {
        if (other == null || !(other instanceof WorksOn)) {
            return false;
        } else {
            WorksOn otherObject = (WorksOn) other;
            return employee.equals(otherObject.getEmployee()) &&
                    project.equals(otherObject.getProject());
        }
    }

    public String toString() {
        return employee + " arbeitet mit " + percentage + "% an " + project;
    }

    public String toFullString() {
        return toString();
    }
}
