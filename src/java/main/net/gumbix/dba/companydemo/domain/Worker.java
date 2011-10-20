package net.gumbix.dba.companydemo.domain;

import java.util.Date;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class Worker extends Personnel {

    private String workspace;

    public Worker() {
    }

    public Worker(long personnelNumber, String lastName, String firstName,
                  Date birthDate, Address adr, String workspace) {
        super(personnelNumber, lastName, firstName, birthDate, adr);
        setWorkspace(workspace);
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String toString() {
        return getFirstName() + " " + getLastName() +
                " (" + getPersonnelNumber() + " " + "Arbeiter)";
    }

    public String toFullString() {
        return super.toFullString() + "\n" +
                "Arbeitsplatz: " + workspace;
    }
}
