package net.gumbix.dba.companydemo.domain;

/**
 * @author Maximilian Nährlich (maximilian.naehrlich@stud.hs-mannheim.de )
 */

public class ProjectStatus {
	
	private String statusId;
	private String description;
	
	public ProjectStatus() {}
	
	public ProjectStatus(String statusId, String description){
		this.statusId = statusId;
		this.description = description;		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusId() {
		return statusId;
	}
	
	public boolean equals(Object other) {
        if (other == null || !(other instanceof ProjectStatus)) {
            return false;
        } else {
        	ProjectStatus otherObject = (ProjectStatus) other;
            return getStatusId().equals(otherObject.getStatusId());
        }
    }

    public String toString() {
        return description + " (" + statusId + ")";
    }

    public String toFullString() {
        return toString();
    }

}
