package net.gumbix.dba.companydemo.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.StatusReport;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class StatusReportDAO extends AbstractDAO {

    public StatusReportDAO(JdbcAccess access) {
        super(access);
    }

    public List<StatusReport> load(Project project) throws Exception {

        List<StatusReport> staList = new ArrayList<StatusReport>();

        ResultSet rs = executeSQLQuery("select * from Statusbericht " +
                " where projektNr = " + project.getProjectNr());

        while (rs.next()) {
            GregorianCalendar date = new GregorianCalendar();
            StatusReport sta = new StatusReport();
            sta.setContinuousNumber(rs.getLong("fortlaufendeNr"));
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
