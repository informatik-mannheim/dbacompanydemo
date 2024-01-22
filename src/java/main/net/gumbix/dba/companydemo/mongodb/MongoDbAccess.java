package net.gumbix.dba.companydemo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.gumbix.dba.companydemo.db.AbstractDBAccess;
import net.gumbix.dba.companydemo.domain.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MongoDbAccess extends AbstractDBAccess {

	private MongoDatabase db;
	private MongoClient mClient;
	private MongoCollection<Document> collection;

	private PersonnelMongoDao persDao;
	private DepartmentMongoDao depDao;
	private CarMongoDao carDao;
	private CompanyCarMongoDao companyCarDao;
	private ProjectMongoDao projectDao;
	private StatRepMongoDao statRepDao;
	private WorksOnMongoDao worksOnDao;


	public MongoDbAccess() {
		startClient();
		initDaos();
	}

	private void startClient() {
		try {
			mClient = new MongoClient("localhost", 27017);
			db = mClient.getDatabase("firmenwelt");
			MongoDbIdGenerator.generator = new MongoDbIdGenerator(db);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/* DAOs needs the connection to the database */
	private void initDaos(){
		persDao = new PersonnelMongoDao(this);
		depDao = new DepartmentMongoDao(this);
		carDao = new CarMongoDao(this);
		companyCarDao = new CompanyCarMongoDao(this);
		projectDao = new ProjectMongoDao(this);
		statRepDao = new StatRepMongoDao(this);
		worksOnDao = new WorksOnMongoDao(this);
	}

	// TODO: find better way to pass database (maybe inheritance?)
	public MongoDatabase getMongoDatabase(){
		return db;
	}


	// Personnel
	@Override
	public Personnel loadPersonnel(long persNr) throws Exception {
		return persDao.load(persNr);
	}

	@Override
	public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception {
		return persDao.queryByName(firstName, lastName);
	}

	@Override
	public void storePersonnel(Personnel pers) throws Exception {
		persDao.store(pers);
	}

	@Override
	public void deletePersonnel(Personnel pers) throws Exception {
		persDao.delete(pers);
	}

	// Department
	@Override
	public Department loadDepartment(long depNumber) throws Exception {
		return depDao.load(depNumber);
	}

	@Override
	public List<Department> queryDepartmentByName(String queryString) throws Exception {
		return depDao.queryByName(queryString);
	}

	@Override
	public void storeDepartment(Department department) throws Exception {
		depDao.store(department);
	}

	@Override
	public void deleteDepartment(Department department) throws Exception {
		depDao.delete(department);
	}


	// Car
	@Override
	public Car loadCar(String model) throws Exception {
		return carDao.load(model);
	}

	@Override
	public void storeCar(Car car) throws Exception {
		carDao.store(car);
	}

	@Override
	public void deleteCar(Car car) throws Exception {
		carDao.delete(car);
	}

	// CompanyCars
	@Override
	public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
		return companyCarDao.load(licensePlate);
	}

	@Override
	public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
		return companyCarDao.queryByModel(model);
	}

	@Override
	public void storeCompanyCar(CompanyCar car) throws Exception {
		companyCarDao.store(car);
	}

	@Override
	public void deleteCompanyCar(CompanyCar car) throws Exception {
		companyCarDao.delete(car);
	}


	// Project
	@Override
	public Project loadProject(String projId) throws Exception {
		return projectDao.load(projId);
	}

	@Override
	public List<Project> queryProjectByDescription(String queryString) throws Exception {
		return projectDao.queryByDescription(queryString);
	}

	@Override
	public void storeProject(Project proj) throws Exception {
		projectDao.store(proj);
	}

	@Override
	public void deleteProject(Project proj) throws Exception {
		projectDao.delete(proj);
	}

	// Status Report
	@Override
	public StatusReport loadStatusReport(Project project, long continuousNumber) throws Exception {
		return statRepDao.load(project, continuousNumber);
	}

	@Override
	public List<StatusReport> loadStatusReport(Project project) throws Exception {
		return statRepDao.load(project);
	}

	@Override
	public void storeStatusReport(StatusReport rep) throws Exception {
		statRepDao.store(rep);
	}

	@Override
	public void deleteStatusReport(StatusReport rep) throws Exception {
		statRepDao.delete(rep);

	}


	// WorksOn
	@Override
	public Set<WorksOn> loadWorksOn(Employee employee) throws Exception {
		return worksOnDao.load(employee);
	}

	@Override
	public Set<WorksOn> loadWorksOn(Project proj) throws Exception {
		return worksOnDao.load(proj);
	}

	@Override
	public void storeWorksOn(WorksOn wo) throws Exception {
		worksOnDao.store(wo);
	}

	@Override
	public void deleteWorksOn(WorksOn wo) throws Exception {
		worksOnDao.delete(wo);
	}


	// Queries
	@Override
	public int getNumberOfPersonnel() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfProjects() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Employee> getIdleEmployees() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	// not sure what this method is used for or if it's even necessary
	public long getIDfromLast() {
		collection = db.getCollection("Personnel");
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
		long temp = 0;
		for (int i = 0; i < documents.size(); i++) {
			if (documents.get(i).getLong("personnelID") > temp) {
				temp = documents.get(i).getLong("personnelID");
			}
		}
		return temp + 1;
	} 


}
