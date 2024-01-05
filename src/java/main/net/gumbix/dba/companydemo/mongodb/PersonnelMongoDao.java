package net.gumbix.dba.companydemo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Worker;

public class PersonnelMongoDao extends AbstractMongoDao {

	private MongoCollection<Document> collection;

    public PersonnelMongoDao(MongoDbAccess mongoDbAccess) {
        super(mongoDbAccess);
        this.collection = mongoDbAccess.getMongoDatabase().getCollection("Personnel");
    }
    
    public Personnel load(long persNr){
		Personnel pers = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("personnelID", persNr))
				.into(new ArrayList<Document>());

		for (int i = 0; i < documents.size(); i++) {
			if (documents.get(i).getString("type").equals("personnel")) {
				pers = loadPers(persNr, documents);
			} else if (documents.get(i).getString("type").equals("employee")) {
				pers = loadEmployee(persNr, documents);
			} else {
				pers = loadWorker(persNr, documents);
			}
		}
		return pers;
    }

    private Personnel loadPers(long persNr, List<Document> documents) {
		Personnel pers = null;
		for (int i = 0; i < documents.size(); i++) {
			pers = new Personnel(persNr, documents.get(i).getString("lastName"),
					documents.get(i).getString("firstName"), documents.get(i).getDate("birthDate"),
					new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
							documents.get(i).getString("zipCode"), documents.get(i).getString("city")));
		}
		return pers;
	}

	private Employee loadEmployee(long persNr, List<Document> documents) {
		Employee temp = null;
		for (int i = 0; i < documents.size(); i++) {
			temp = new Employee(persNr, documents.get(i).getString("lastName"),
					documents.get(i).getString("firstName"), documents.get(i).getDate("birthDate"),
					new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
							documents.get(i).getString("zipCode"), documents.get(i).getString("city")),
					documents.get(i).getString("tel"));
		}
		return temp;
	}

	private Worker loadWorker(long persNr, List<Document> documents) {
		Worker temp = null;
		for (int i = 0; i < documents.size(); i++) {
			temp = new Worker(persNr, documents.get(i).getString("lastName"),
					documents.get(i).getString("firstName"), documents.get(i).getDate("birthDate"),
					new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
							documents.get(i).getString("zipCode"), documents.get(i).getString("city")),
					documents.get(i).getString("workspace"));
		}
		return temp;
	}

    public List<Personnel> queryByName(String firstName, String lastName) throws Exception{
        List<Personnel> persList = new ArrayList<Personnel>();
		List<Document> documents = (List<Document>) collection
				.find(Filters.and(Filters.eq("lastName", lastName), Filters.eq("firstName", firstName)))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			persList.add(new Personnel(documents.get(i).getLong("personnelID"), documents.get(i).getString("lastName"),
					documents.get(i).getString("firstName"), documents.get(i).getDate("birthDate"),
					new Address(documents.get(i).getString("street"), documents.get(i).getString("houseNumber"),
							documents.get(i).getString("zipCode"), documents.get(i).getString("city"))));
		}
		return persList;
    } 

    public void store(Personnel pers) throws Exception{
        if (pers instanceof Worker) {
			storeWorker(pers);
		} else if (pers instanceof Employee) {
			storeEmploye(pers);
		} else {
			storePers(pers);
		}
    } 

    private void storePers(Personnel pers) {
		long nextId = MongoDbIdGenerator.generator.getNextLong(pers.getClass());
		pers.setPersonnelNumber(nextId);
		Document document = new Document("personnelID", nextId).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("birthDate", pers.getBirthDate())
				.append("salary", pers.getSalary()).append("street", pers.getAddress().getStreet())
				.append("houseNumber", pers.getAddress().getHouseNumber()).append("zipCode", pers.getAddress().getZip())
				.append("city", pers.getAddress().getZipCity().getCity()).append("type", "personnel");
		collection.insertOne(document);
	}

	private void storeEmploye(Personnel pers) {
		Employee e = (Employee) pers;
		long nextId = MongoDbIdGenerator.generator.getNextLong(pers.getClass());
		pers.setPersonnelNumber(nextId);
		Document document = new Document("personnelID", nextId).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("birthDate", pers.getBirthDate())
				.append("Salary", pers.getSalary()).append("street", pers.getAddress().getStreet())
				.append("houseNumber", pers.getAddress().getHouseNumber()).append("zipCode", pers.getAddress().getZip())
				.append("city", pers.getAddress().getZipCity().getCity()).append("type", "employee")
				.append("phoneNumber", e.getPhoneNumber());
		collection.insertOne(document);
	}

	private void storeWorker(Personnel pers) {
		Worker w = (Worker) pers;
		long nextId = MongoDbIdGenerator.generator.getNextLong(pers.getClass());
		pers.setPersonnelNumber(nextId);
		Document document = new Document("personnelID", nextId).append("lastName", pers.getLastName())
				.append("firstName", pers.getFirstName()).append("birthDate", pers.getBirthDate())
				.append("salary", pers.getSalary()).append("street", pers.getAddress().getStreet())
				.append("houseNumber", pers.getAddress().getHouseNumber()).append("zipCode", pers.getAddress().getZip())
				.append("city", pers.getAddress().getZipCity().getCity()).append("type", "worker")
				.append("workspace", w.getWorkspace());
		collection.insertOne(document);
	}

    public void delete(Personnel pers) throws Exception{
        collection.deleteOne(Filters.eq("personnelID", pers.getPersonnelNumber()));
    }

}
