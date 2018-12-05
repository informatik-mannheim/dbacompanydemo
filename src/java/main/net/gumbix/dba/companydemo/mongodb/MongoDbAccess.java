package net.gumbix.dba.companydemo.mongodb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
//import com.google.common.collect.Lists;
import net.gumbix.dba.companydemo.db.AbstractDBAccess;
import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Car;
import net.gumbix.dba.companydemo.domain.CompanyCar;
import net.gumbix.dba.companydemo.domain.Department;
import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.StatusReport;
import net.gumbix.dba.companydemo.domain.Worker;
import net.gumbix.dba.companydemo.domain.WorksOn;

public class MongoDbAccess extends AbstractDBAccess {

	private static final int CASE_INSENSITIVE = 0;
	private MongoDatabase db;
	private MongoClient mClient;
	private BasicDBObject doc;
	private BasicDBObject addresseVonPersonel;
	private MongoCollection<Document> collection;
	private MongoDbIdGenerator mdbIdGenerator;
	public MongoDbAccess(String string) {
		startClient();
		mdbIdGenerator = new MongoDbIdGenerator();
		
	}

	@SuppressWarnings("deprecation")
	private void startClient() {
		try {
			
			mClient = new MongoClient("localhost", 27017);
			db = mClient.getDatabase("firmenwelt");
			db.createCollection("Personal");
			db.createCollection("Department");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Personnel loadPersonnel(long persNr) throws Exception {
		collection = db.getCollection("Personal");
		Personnel temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("PersonalID", persNr))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp = new Personnel(persNr, documents.get(i).getString("lastName"),
					documents.get(i).getString("firstName"), documents.get(i).getDate("Birthdate"),
					new Address(documents.get(i).getString("Straﬂe"), documents.get(i).getString("Hausnummer"),
							documents.get(i).getString("PLZ"), documents.get(i).getString("Stadt")));
		}
		return temp;
	}

	@Override
	public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception {
		collection = db.getCollection("Personal");
		List<Personnel> temp = new ArrayList();
		List<Document> documents = (List<Document>) collection.find(Filters.and(Filters.eq("lastName", lastName),
				Filters.eq("firstName", firstName)))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp.add(new Personnel(documents.get(i).getLong("PersonalID"), documents.get(i).getString("lastName"), documents.get(i).getString("firstName"),
					documents.get(i).getDate("Birthdate"), new Address(documents.get(i).getString("Straﬂe"),documents.get(i).getString("Hausnummer"),
							documents.get(i).getString("PLZ"), documents.get(i).getString("Stadt"))));
		}
		return temp;
	}

	@Override
	public void storePersonnel(Personnel pers) throws Exception {
		if(pers instanceof Worker) {
			storeWorker(pers);
		}
		else if(pers instanceof Employee) {
			storeEmploye(pers);
		}
		else {
			storePers(pers);
		}
	}
	
	private void storeEmploye(Personnel pers) {
		Employee e = (Employee) pers;
		MongoCollection<Document> collection = db.getCollection("Personal");
		long temp = mdbIdGenerator.getID();
		pers.setPersonnelNumber(temp);
		Document document = new Document("PersonalID", temp).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("Birthdate", pers.getBirthDate())
				.append("salary", pers.getSalary()).append("Straﬂe", pers.getAddress().getStreet())
				.append("Hausnummer", pers.getAddress().getHouseNumber()).append("PLZ", pers.getAddress().getZip())
				.append("Stadt", pers.getAddress().getZipCity().getCity()).append("Telefonnummer", e.getPhoneNumber());
		collection.insertOne(document);
	}
	
	private void storeWorker(Personnel pers) {
		Worker w = (Worker) pers;
		MongoCollection<Document> collection = db.getCollection("Personal");
		long temp = mdbIdGenerator.getID();
		pers.setPersonnelNumber(temp);
		Document document = new Document("PersonalID", temp).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("Birthdate", pers.getBirthDate())
				.append("salary", pers.getSalary()).append("Straﬂe", pers.getAddress().getStreet())
				.append("Hausnummer", pers.getAddress().getHouseNumber()).append("PLZ", pers.getAddress().getZip())
				.append("Stadt", pers.getAddress().getZipCity().getCity()).append("Arebitsplatz", w.getWorkspace());
		collection.insertOne(document);
	}
	
	private void storePers(Personnel pers) {
		MongoCollection<Document> collection = db.getCollection("Personal");
		long temp = mdbIdGenerator.getID();
		pers.setPersonnelNumber(temp);
		Document document = new Document("PersonalID", temp).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("Birthdate", pers.getBirthDate())
				.append("salary", pers.getSalary()).append("Straﬂe", pers.getAddress().getStreet())
				.append("Hausnummer", pers.getAddress().getHouseNumber()).append("PLZ", pers.getAddress().getZip())
				.append("Stadt", pers.getAddress().getZipCity().getCity());
		collection.insertOne(document);
	}

	@Override
	public void deletePersonnel(Personnel pers) throws Exception {
		collection = db.getCollection("Personal");
		collection.deleteOne(Filters.eq("PersonalID", pers.getPersonnelNumber()));
	}

	@Override
	public Department loadDepartment(long depNumber) throws Exception {
		collection = db.getCollection("Department");
		Department temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("DepNr", depNumber))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp = new Department(depNumber, documents.get(i).getString("Name"));
		}
		return temp;
	}

	@Override
	public List<Department> queryDepartmentByName(String queryString) throws Exception {
		collection = db.getCollection("Department");
		List<Department> temp = new ArrayList<Department>();
		List<Document> documents = (List<Document>) collection.find(Filters.eq("Name", queryString))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp.add(new Department(documents.get(i).getLong("DepNr"), documents.get(i).getString("Name")));
		}
		return temp;
	}

	@Override
	public void storeDepartment(Department department) throws Exception {
		MongoCollection<Document> collection = db.getCollection("Department");
		Document document = new Document("DepNr", department.getDepNumber()).append("Name", department.getName());
		collection.insertOne(document);
	}

	@Override
	public void deleteDepartment(Department department) throws Exception {
		collection = db.getCollection("Department");
		collection.deleteOne(Filters.eq("DepNr", department.getDepNumber()));
	}

	@Override
	public Car loadCar(String modell) throws Exception {
		collection = db.getCollection("Car");
		Car temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("Modell", modell))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp = new Car(modell, (String) documents.get(i).get("name"));
		}
		return temp;
	}

	@Override
	public void storeCar(Car car) throws Exception {
		MongoCollection<Document> doc = db.getCollection("Car");
		Document document = new Document("Modell", car.getModel()).append("Type", car.getType());
		doc.insertOne(document);
	}

	@Override
	public void deleteCar(Car car) throws Exception {
		collection = db.getCollection("Car");
		collection.deleteOne(Filters.eq("Modell", car.getModel()));
	}

	@Override
	public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
		collection = db.getCollection("Firmenwagen");
		CompanyCar temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("Kennzeichen", licensePlate))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp = new CompanyCar(licensePlate,
					new Car(documents.get(i).getString("Modell"), documents.get(i).getString("Type")));
		}
		return temp;
	}

	@Override
	public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
		collection = db.getCollection("Firmenwagen");
		List<CompanyCar> temp = new ArrayList();
		List<Document> documents = (List<Document>) collection.find(Filters.eq("Modell", model))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp.add(new CompanyCar(documents.get(i).getString("Kennzeichen"),
					new Car(documents.get(i).getString("Modell"), documents.get(i).getString("Type"))));
		}
		return temp;
	}

	@Override
	public void storeCompanyCar(CompanyCar car) throws Exception {
		MongoCollection<Document> collection = db.getCollection("Firmenwagen");
		// Logikfehler in der UI! Beim anlegen eines Firmenwagnes wird defaultm‰ﬂig ein Auto-Modell angelegt
		Car temp = new Car("Polo", "VW");
		Document document = new Document("Kennzeichen", car.getLicensePlate()).append("Modell", temp.getModel())
				.append("Marke", temp.getType()).append("Fahrer", car.getDriver());
		collection.insertOne(document);
	}

	@Override
	public void deleteCompanyCar(CompanyCar car) throws Exception {
		collection = db.getCollection("Firmenwagen");
		collection.deleteOne(Filters.eq("Kennzeichen", car.getLicensePlate()));
	}

	@Override
	public Project loadProject(String projId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> queryProjectByDescription(String queryString) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeProject(Project proj) throws Exception {
		collection = db.getCollection("Project");
		doc = new BasicDBObject();
		doc.put("beschreibung", proj.getDescription());
		// doc.put("", arg1)
	}

	@Override
	public void deleteProject(Project proj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public StatusReport loadStatusReport(Project project, long continuousNumber) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatusReport> loadStatusReport(Project project) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeStatusReport(StatusReport rep) throws Exception {
		collection = db.getCollection("statusReport");
		doc = new BasicDBObject();
		doc.put("content", rep.getContent());

		doc.put("continuousNumber", rep.getContinuousNumber());
		doc.put("date", rep.getDate());
		doc.put("prjact", rep.getProject());

		// dbColletction.insert(doc);

	}

	@Override
	public void deleteStatusReport(StatusReport rep) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<WorksOn> loadWorksOn(Employee employee) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<WorksOn> loadWorksOn(Project proj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeWorksOn(WorksOn wo) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteWorksOn(WorksOn wo) throws Exception {
		// TODO Auto-generated method stub

	}

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
	
	public long getIDfromLast() {
		collection = db.getCollection("Personal");
		List<Document> documents =(List<Document>) collection.find().into(new ArrayList<Document>());
		long temp = 0;
		for(int i = 0; i < documents.size(); i++) {
			if(documents.get(i).getLong("PersonalID") > temp) {
				temp = documents.get(i).getLong("PersonalID");
			}
		}
		return temp+1;
	}

}
