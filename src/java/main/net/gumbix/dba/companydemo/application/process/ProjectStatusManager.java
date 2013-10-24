package net.gumbix.dba.companydemo.application.process;

import java.util.ArrayList;
import java.util.List;
import net.gumbix.dba.companydemo.db.DBAccess;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.jdbc.ProjectDAO;

/**
 * @author Maximilian Nährlich (maximilian.naehrlich@stud.hs-mannheim.de )
 */

public class ProjectStatusManager {
	
	private static ProjectStatusManager instance;
	
	public static ProjectStatusManager getInstance(){
		if(instance == null){
			instance = new ProjectStatusManager();
		}
		return instance;
	}
	
	public List<ProjectStatusEnum> getNextStatus(ProjectStatusEnum currentStatus){
		List<ProjectStatusEnum> nextStatus = new ArrayList<>();
		
		switch(currentStatus){
			case New:
			case Cancelled:
			case Blocked:
				nextStatus.add(ProjectStatusEnum.InProcess);
				break;
			case Finished:
				break;
			case InProcess:
				nextStatus.add(ProjectStatusEnum.Finished);
				nextStatus.add(ProjectStatusEnum.Blocked);
				nextStatus.add(ProjectStatusEnum.Cancelled);
				break;
		}
		
		return nextStatus;
	}
	
	public void setNextStatus(DBAccess db, Project project, ProjectStatusEnum nextStatus){
		/*
		//test if nextStatis is valid
		boolean isValid = false;
		List<ProjectStatus> posNextStatus = this.getNextStatus(project.getStatus());
		if(posNextStatus == null){
			throw new Exception();
		}
		for(ProjectStatus s : posNextStatus){
			isValid = (s == nextStatus)? true : false;
		}
		if(!isValid){
			throw new Exception();
		}
		
		if(nextStatus == ProjectStatus.InProcess || nextStatus == ProjectStatus.Blocked){
			//Simply set the new status, there no further requirements
			project.setStatus(nextStatus);
			db.storeProject(project);			
		}
		
		if(nextStatus == ProjectStatus.Finished){
			//remove all Employees working on this project
		}
		
		if(nextStatus == ProjectStatus.Cancelled){
			//if anyone is working on this project abort status change (its no allowed then)
		}
		
		*/
	}

}
