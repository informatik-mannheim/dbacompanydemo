package net.gumbix.dba.companydemo.domain;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.gumbix.dba.companydemo.db.IdGenerator;

import com.db4o.internal.IDGenerator;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @auhtor Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class StatusReport {

	private long continuousNumber;
	private Date date;
	private String content;
	private Project project;

	public StatusReport(String content, Project project) {
		this(new Date(), content, project);
	}

	public StatusReport(Date date, String content, Project project) {
		this(IdGenerator.generator.getNextLong(StatusReport.class), date,
				content, project);
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

	public void setContinuousNumber(long continuousNumber) {
		this.continuousNumber = continuousNumber;
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

	public String toString() {
		return "Statusreport " + continuousNumber;
	}

	public String toFullString() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

		return "Nummer:  " + continuousNumber + "\nDatum:   " + df.format(date)
				+ "\nInhalt:  " + content + "\nProjekt: " + project;
	}
}
