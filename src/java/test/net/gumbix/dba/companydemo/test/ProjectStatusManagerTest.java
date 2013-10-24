package net.gumbix.dba.companydemo.test;

import static org.junit.Assert.*;

import java.util.List;

import net.gumbix.dba.companydemo.application.process.ProjectStatusEnum;
import net.gumbix.dba.companydemo.application.process.ProjectStatusManager;

import org.junit.Test;

public class ProjectStatusManagerTest {
	
	@Test
	public void getNextStatusTest(){
		
		ProjectStatusManager psm = ProjectStatusManager.getInstance();
		if(psm == null){
			assertTrue(false);
		}
		
		List<ProjectStatusEnum> nextStatusList;
		
		nextStatusList = psm.getNextStatus(ProjectStatusEnum.New);
		if(nextStatusList.get(0) != ProjectStatusEnum.InProcess){
			assertTrue(false);
		}
		
		nextStatusList = psm.getNextStatus(ProjectStatusEnum.InProcess);
		if(		nextStatusList.get(0) != ProjectStatusEnum.Finished ||
				nextStatusList.get(1) != ProjectStatusEnum.Blocked ||
				nextStatusList.get(2) != ProjectStatusEnum.Cancelled	){
			assertTrue(false);
		}
		
		nextStatusList = psm.getNextStatus(ProjectStatusEnum.Finished);
		if(nextStatusList.size() != 0){
			assertTrue(false);
		}
		
		nextStatusList = psm.getNextStatus(ProjectStatusEnum.Blocked);
		if(nextStatusList.get(0) != ProjectStatusEnum.InProcess){
			assertTrue(false);
		}
		
		nextStatusList = psm.getNextStatus(ProjectStatusEnum.Cancelled);
		if(nextStatusList.get(0) != ProjectStatusEnum.InProcess){
			assertTrue(false);
		}
		
	}

}
