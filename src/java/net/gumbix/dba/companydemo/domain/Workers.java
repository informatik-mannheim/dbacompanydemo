package net.gumbix.dba.companydemo.domain;

import java.util.GregorianCalendar;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class Workers extends Personnel {

    private String workspace;

    public Workers() {

    }

    public Workers(String name, String vorname, GregorianCalendar gebDatum, Address adr, String workspace) {
        super(name, vorname, gebDatum, adr);
        setWorkspace(workspace);
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }


}
