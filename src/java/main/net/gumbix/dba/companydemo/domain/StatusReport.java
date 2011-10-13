package net.gumbix.dba.companydemo.domain;

import java.util.GregorianCalendar;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class StatusReport {

	private long continuouslyNr;
	private GregorianCalendar date;
	private String content;


	public StatusReport(){
		this.date = new  GregorianCalendar();
	}

	public StatusReport(long continuouslyNr, String content){
		this.continuouslyNr = continuouslyNr;
		this.content = content;

	}


	public long getContinuouslyNr() {
		return continuouslyNr;
	}


	public void setContinuouslyNr(long continuouslyNr) {
		this.continuouslyNr = continuouslyNr;
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


}
