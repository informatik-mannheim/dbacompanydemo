package net.gumbix.dba.companydemo.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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
            
            long contNumber = rs.getLong("fortlaufendeNr");
            String content = rs.getString("inhalt");
            Date ddate = rs.getDate("datum");
            StatusReport sta = new StatusReport(contNumber, ddate, content, project);
            staList.add(sta);
        }
        return staList;
    }

    public void store(StatusReport statusReport) throws Exception {
        // TODO
    }

    public void delete(StatusReport statusReport) throws Exception {
        // TODO
    }
}
