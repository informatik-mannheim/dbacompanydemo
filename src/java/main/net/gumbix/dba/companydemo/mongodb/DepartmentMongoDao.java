package net.gumbix.dba.companydemo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.gumbix.dba.companydemo.domain.Department;



public class DepartmentMongoDao extends AbstractMongoDao {

    private MongoCollection<Document> collection;

    public DepartmentMongoDao(MongoDbAccess mongoDbAccess) {
        super(mongoDbAccess);
        this.collection = mongoDbAccess.getMongoDatabase().getCollection("Department");
    }

    public Department load(long depNumber){
        Department dep = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("depNumber", depNumber))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			dep = new Department(depNumber, documents.get(i).getString("name"));
		}
		return dep;
    }

    public List<Department> queryByName(String queryString){
        List<Department> depList = new ArrayList<Department>();
		List<Document> documents = (List<Document>) collection.find(Filters.eq("name", queryString))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			depList.add(new Department(documents.get(i).getLong("depNumber"), documents.get(i).getString("name")));
		}
		return depList;
    }

    public void store(Department department){
        Document document = new Document("depNumber", department.getDepNumber()).append("name", department.getName());
		collection.insertOne(document);
    }

    public void delete (Department department){
        collection.deleteOne(Filters.eq("depNumber", department.getDepNumber()));
    }
}
