package net.gumbix.dba.companydemo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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
	private MongoDbIdGenerator mdbIdGenerator;

	public MongoDbAccess() {
		startClient();
	}

	private void startClient() {
		try {
			mdbIdGenerator = new MongoDbIdGenerator();
			mClient = new MongoClient("localhost", 27017);
			db = mClient.getDatabase("firmenwelt");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Personnel loadPersonnel(long persNr) throws Exception {
		collection = db.getCollection("Personnel");
		Personnel temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("PersonnelID", persNr))
				.into(new ArrayList<Document>());

		for (int i = 0; i < documents.size(); i++) {
			if (documents.get(i).getString("typ").equals("personnel")) {
				temp = new Personnel(persNr, documents.get(i).getString("lastName"),
						documents.get(i).getString("firstName"), documents.get(i).getDate("birthDate"),
						new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
						documents.get(i).getString("zipCode"), documents.get(i).getString("city")));
			} else if (documents.get(i).getString("typ").equals("employee")) {
				temp = new Employee(persNr, documents.get(i).getString("lastName"),
						documents.get(i).getString("firstName"), documents.get(i).getDate("birthDate"),
						new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
						documents.get(i).getString("zipCode"), documents.get(i).getString("city")),
						documents.get(i).getString("tel"));
			} else {
				temp = new Worker(persNr, documents.get(i).getString("lastName"),
						documents.get(i).getString("firstName"), documents.get(i).getDate("birthDate"),
						new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
						documents.get(i).getString("zipCode"), documents.get(i).getString("city")),
						documents.get(i).getString("workspace"));
			}
		}
		return temp;
	}

	@Override
	public List<Personnel> queryPersonnelByName(String firstName, String lastName) throws Exception {
		collection = db.getCollection("Personnel");
		List<Personnel> temp = new ArrayList<Personnel>();
		List<Document> documents = (List<Document>) collection
				.find(Filters.and(Filters.eq("lastName", lastName), Filters.eq("firstName", firstName)))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp.add(new Personnel(documents.get(i).getLong("PersonnelID"), documents.get(i).getString("lastName"),
					documents.get(i).getString("firstName"), documents.get(i).getDate("Birthdate"),
					new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
							documents.get(i).getString("zipCode"), documents.get(i).getString("city"))));
		}
		return temp;
	}

	@Override
	public void storePersonnel(Personnel pers) throws Exception {
		if (pers instanceof Worker) {
			storeWorker(pers);
		} else if (pers instanceof Employee) {
			storeEmploye(pers);
		} else {
			storePers(pers);
		}
	}

	private void storeEmploye(Personnel pers) {
		Employee e = (Employee) pers;
		MongoCollection<Document> collection = db.getCollection("Personnel");
		long temp = mdbIdGenerator.getID();
		pers.setPersonnelNumber(temp);
		Document document = new Document("PersonnelID", temp).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("Birthdate", pers.getBirthDate())
				.append("Salary", pers.getSalary()).append("street", pers.getAddress().getStreet())
				.append("houseNumber", pers.getAddress().getHouseNumber()).append("zipCode", pers.getAddress().getZip())
				.append("city", pers.getAddress().getZipCity().getCity()).append("type", "employee")
				.append("phoneNumber", e.getPhoneNumber());
		collection.insertOne(document);
	}

	private void storeWorker(Personnel pers) {
		Worker w = (Worker) pers;
		MongoCollection<Document> collection = db.getCollection("Personnel");
		long temp = mdbIdGenerator.getID();
		pers.setPersonnelNumber(temp);
		Document document = new Document("PersonnelID", temp).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("Birthdate", pers.getBirthDate())
				.append("salary", pers.getSalary()).append("street", pers.getAddress().getStreet())
				.append("houseNumber", pers.getAddress().getHouseNumber()).append("zipCode", pers.getAddress().getZip())
				.append("city", pers.getAddress().getZipCity().getCity()).append("type", "worker")
				.append("workspace", w.getWorkspace());
		collection.insertOne(document);
	}

	private void storePers(Personnel pers) {
		MongoCollection<Document> collection = db.getCollection("Personnel");
		long temp = mdbIdGenerator.getID();
		pers.setPersonnelNumber(temp);
		Document document = new Document("PersonnelID", temp).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("Birthdate", pers.getBirthDate())
				.append("salary", pers.getSalary()).append("street", pers.getAddress().getStreet())
				.append("houseNumber", pers.getAddress().getHouseNumber()).append("zipCode", pers.getAddress().getZip())
				.append("city", pers.getAddress().getZipCity().getCity()).append("type", "personnel");
		collection.insertOne(document);
	}

	@Override
	public void deletePersonnel(Personnel pers) throws Exception {
		collection = db.getCollection("Personnel");
		collection.deleteOne(Filters.eq("PersonnelID", pers.getPersonnelNumber()));
	}

	@Override
	public Department loadDepartment(long depNumber) throws Exception {
		collection = db.getCollection("Department");
		Department temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("depNumber", depNumber))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp = new Department(depNumber, documents.get(i).getString("name"));
		}
		return temp;
	}

	@Override
	public List<Department> queryDepartmentByName(String queryString) throws Exception {
		collection = db.getCollection("Department");
		List<Department> temp = new ArrayList<Department>();
		List<Document> documents = (List<Document>) collection.find(Filters.eq("name", queryString))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp.add(new Department(documents.get(i).getLong("depNumber"), documents.get(i).getString("name")));
		}
		return temp;
	}

	@Override
	public void storeDepartment(Department department) throws Exception {
		MongoCollection<Document> collection = db.getCollection("Department");
		Document document = new Document("depNumber", department.getDepNumber()).append("name", department.getName());
		collection.insertOne(document);
	}

	@Override
	public void deleteDepartment(Department department) throws Exception {
		collection = db.getCollection("Department");
		collection.deleteOne(Filters.eq("depNumber", department.getDepNumber()));
	}

	@Override
	public Car loadCar(String model) throws Exception {
		collection = db.getCollection("Car");
		Car temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("model", model))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp = new Car(model, (String) documents.get(i).get("name"));
		}
		return temp;
	}

	@Override
	public void storeCar(Car car) throws Exception {
		MongoCollection<Document> doc = db.getCollection("Car");
		Document document = new Document("model", car.getModel()).append("type", car.getType());
		doc.insertOne(document);
	}

	@Override
	public void deleteCar(Car car) throws Exception {
		collection = db.getCollection("Car");
		collection.deleteOne(Filters.eq("model", car.getModel()));
	}

	@Override
	public CompanyCar loadCompanyCar(String licensePlate) throws Exception {
		collection = db.getCollection("CompanyCar");
		CompanyCar temp = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("licensePlate", licensePlate))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp = new CompanyCar(licensePlate,
					new Car(documents.get(i).getString("model"), documents.get(i).getString("type")));
		}
		return temp;
	}

	@Override
	public List<CompanyCar> queryCompanyCarByModel(String model) throws Exception {
		collection = db.getCollection("CompanyCar");
		List<CompanyCar> temp = new ArrayList<CompanyCar>();
		List<Document> documents = (List<Document>) collection.find(Filters.eq("model", model))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			temp.add(new CompanyCar(documents.get(i).getString("licensePlate"),
				new Car(documents.get(i).getString("model"), documents.get(i).getString("type"))));
		}
		return temp;
	}

	@Override
	public void storeCompanyCar(CompanyCar car) throws Exception {
		MongoCollection<Document> collection = db.getCollection("CompanyCar");
		Car temp = car.getCar();
		Document document = new Document("licensePlate", car.getLicensePlate()).append("model", temp.getModel())
				.append("type", temp.getType()).append("driver", car.getDriver());
		collection.insertOne(document);
	}

	@Override
	public void deleteCompanyCar(CompanyCar car) throws Exception {
		collection = db.getCollection("CompanyCar");
		collection.deleteOne(Filters.eq("licensePlate", car.getLicensePlate()));
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
		// TODO Auto-generated method stub
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
		collection = db.getCollection("Personnel");
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
		long temp = 0;
		for (int i = 0; i < documents.size(); i++) {
			if (documents.get(i).getLong("PersonnelID") > temp) {
				temp = documents.get(i).getLong("PersonnelID");
			}
		}
		return temp + 1;
	}

}
