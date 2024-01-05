package net.gumbix.dba.companydemo.mongodb;

import java.util.Set;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.WorksOn;


/* TODO: This class is not fully implemented yet */
public class WorksOnMongoDao extends AbstractMongoDao{

    private MongoCollection<Document> collection;

    public WorksOnMongoDao(MongoDbAccess mongoDbAccess) {
        super(mongoDbAccess);
        this.collection = mongoDbAccess.getMongoDatabase().getCollection("WorksOn");
    }

    public Set<WorksOn> load(Employee employee){
        System.err.println("Noch nicht implementiert");
        return null;
    }

    public Set<WorksOn> load(Project proj){
        System.err.println("Noch nicht implementiert");
        return null;
    }

    public void store(WorksOn worksOn){
        System.err.println("Noch nicht implementiert");
    }

    public void delete(WorksOn worksOn){
        System.err.println("Noch nicht implementiert");
    }
    
}
