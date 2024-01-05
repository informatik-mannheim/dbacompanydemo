package net.gumbix.dba.companydemo.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import net.gumbix.dba.companydemo.db.IdGenerator;

public class MongoDbIdGenerator extends IdGenerator{

    private MongoDatabase db;

    public MongoDbIdGenerator(MongoDatabase db){
        this.db = db;
    }

    @Override
    public long getNextLong(Class clazz) { // the class is for the database in MongoDB irrelevant
        MongoCollection<Document> collection;
        List<Document> results = new ArrayList<>();
        long nextId = 1;
        
        collection = db.getCollection("Personnel"); // because this function is only called by Personnel-classes
        if (collection.find().first() == null){ // check for empty collection
            return nextId;
        } else {
            collection.find().sort(descending("personnelID")).limit(1).into(results); // get the document with the highest id and put it into an ArrayList
            nextId = results.get(0).getLong("personnelID") + 1; // increase the id by one
            return nextId;
        }
    }
    
}
