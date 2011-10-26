package net.gumbix.dba.companydemo.domain;

import net.gumbix.dba.companydemo.db.IdGenerator;

import java.util.Date;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class Worker extends Personnel {

    private String workspace;

    public Worker(String lastName, String firstName,
                  Date birthDate, Address adr, String workspace) {
        this(IdGenerator.generator.getNextLong(Personnel.class),
                lastName, firstName, birthDate, adr, workspace);
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
