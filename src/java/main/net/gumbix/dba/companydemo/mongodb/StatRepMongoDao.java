package net.gumbix.dba.companydemo.mongodb;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import net.gumbix.dba.companydemo.domain.Project;
import net.gumbix.dba.companydemo.domain.StatusReport;

/* TODO: This class is not fully implemented yet */
public class StatRepMongoDao extends AbstractMongoDao{

    private MongoCollection<Document> collection;

    public StatRepMongoDao(MongoDbAccess mongoDbAccess) {
        super(mongoDbAccess);
        this.collection = mongoDbAccess.getMongoDatabase().getCollection("StatusReport");
    }

    public StatusReport load(Project project, long continuousNumber){
        System.err.println("Noch nicht implementiert.");
        return null;
    }

    public List<StatusReport> load(Project project){
        System.err.println("Noch nicht implementiert.");
        return null;
    }
    
    public void store(StatusReport statRep){
        System.err.println("Noch nicht implementiert.");   
    }

    public void delete(StatusReport statRep){
        System.err.println("Noch nicht implementiert.");
    }

    
}
