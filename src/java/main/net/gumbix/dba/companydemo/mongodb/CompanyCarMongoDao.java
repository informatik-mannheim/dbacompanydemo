package net.gumbix.dba.companydemo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.gumbix.dba.companydemo.domain.Car;
import net.gumbix.dba.companydemo.domain.CompanyCar;

public class CompanyCarMongoDao extends AbstractMongoDao {

    private MongoCollection<Document> collection;

    public CompanyCarMongoDao(MongoDbAccess mongoDbAccess) {
        super(mongoDbAccess);
        this.collection = mongoDbAccess.getMongoDatabase().getCollection("CompanyCar");
    }

    public CompanyCar load(String licensePlate){
        CompanyCar car = null;
		List<Document> documents = (List<Document>) collection.find(Filters.eq("licensePlate", licensePlate))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			car = new CompanyCar(licensePlate,
					new Car(documents.get(i).getString("model"), documents.get(i).getString("type")));
		}
		return car;
    }
    
    public List<CompanyCar> queryByModel(String model){
        List<CompanyCar> compCarList = new ArrayList<CompanyCar>();
		List<Document> documents = (List<Document>) collection.find(Filters.eq("model", model))
				.into(new ArrayList<Document>());
		for (int i = 0; i < documents.size(); i++) {
			compCarList.add(new CompanyCar(documents.get(i).getString("licensePlate"),
					new Car(documents.get(i).getString("model"), documents.get(i).getString("type"))));
		}
		return compCarList;
    }

    public void store(CompanyCar compCar){
        Car car = compCar.getCar();
		Document document = new Document("licensePlate", compCar.getLicensePlate()).append("model", car.getModel())
				.append("type", car.getType()).append("driver", compCar.getDriver());
		collection.insertOne(document);
    }

    public void delete(CompanyCar compCar){
        collection.deleteOne(Filters.eq("licensePlate", compCar.getLicensePlate()));
    }
}
