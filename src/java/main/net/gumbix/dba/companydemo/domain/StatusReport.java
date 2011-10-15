package net.gumbix.dba.companydemo.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @auhtor Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class StatusReport {

    private long continuousNumber;
    private GregorianCalendar date;
    private String content;

    public StatusReport() {
        this.date = new GregorianCalendar();
    }

    public StatusReport(long continuousNumber, String content) {
        this.continuousNumber = continuousNumber;
        this.content = content;
    }

    public long getContinuousNumber() {
        return continuousNumber;
    }

    public void setContinuousNumber(long continuousNumber) {
        this.continuousNumber = continuousNumber;
    }


    public GregorianCalendar getDate() {
        return date;
    }


    public void setDate(GregorianCalendar date) {
        this.date = date;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return continuousNumber + "";
    }

    public String toFullString() {
        return "Nummer: " + continuousNumber +
                "\nDatum:  " + date.get(Calendar.DAY_OF_MONTH) + "." +
                date.get(Calendar.MONTH) + "." +
                date.get(Calendar.YEAR) +
                "\nInhalt: " + content;
    }
}
