package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.StatusReport;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 */

public class StatusReportDAO extends AbstractDAO {

    public StatusReportDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an list of StatusReport Objects from Table "Statusbericht" using the Project number
    public List<StatusReport> load(long projNr) throws Exception {

        List<StatusReport> staList = new ArrayList<StatusReport>();
        GregorianCalendar date;
        StatusReport sta;

        ResultSet rs = executeSQLQuery("SELECT * FROM Statusbericht WHERE projektNr = " + projNr);

        while (rs.next()) {

            date = new GregorianCalendar();
            sta = new StatusReport();

            sta.setContinuouslyNr(rs.getLong("fortlaufendeNr"));
            sta.setContent(rs.getString("inhalt"));

            date.setTime(rs.getDate("datum"));
            sta.setDate(date);

            staList.add(sta);

        }

        return staList;

    }

    public void store(StatusReport statusReport) throws Exception {

    }

    public void delete(StatusReport statusReport) throws Exception {
    }
}
