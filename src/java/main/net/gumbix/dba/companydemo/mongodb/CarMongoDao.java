package net.gumbix.dba.companydemo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.gumbix.dba.companydemo.domain.Car;

public class CarMongoDao extends AbstractMongoDao {

    private MongoCollection<Document> collection;

    public CarMongoDao(MongoDbAccess mongoDbAccess) {
        super(mongoDbAccess);
        this.collection = mongoDbAccess.getMongoDatabase().getCollection("Car");
    }

    public Car load(String model){
        Car car = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("model", model))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			car = new Car(model, (String) documents.get(i).get("name"));
		}
		return car;
    }

    public void store(Car car){
        Document document = new Document("model", car.getModel()).append("type", car.getType());
		collection.insertOne(document);
    }

    public void delete(Car car){
        collection.deleteOne(Filters.eq("model", car.getModel()));
    }
    
}
