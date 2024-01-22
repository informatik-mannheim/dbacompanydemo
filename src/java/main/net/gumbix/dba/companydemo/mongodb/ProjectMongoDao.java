package net.gumbix.dba.companydemo.mongodb;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import net.gumbix.dba.companydemo.domain.Project;

/* TODO: this class is not fully implemented yet*/
public class ProjectMongoDao extends AbstractMongoDao{

    private MongoCollection<Document> collection;

    public ProjectMongoDao(MongoDbAccess mongoDbAccess) {
        super(mongoDbAccess);
        this.collection = mongoDbAccess.getMongoDatabase().getCollection("Project");
    }
    
    public Project load(String projectId){
        System.err.println("Noch nicht implementiert.");
        return null;
    }

    public List<Project> queryByDescription(String queryString){
        System.err.println("Noch nicht implementiert.");
        return null;
    }

    public void store(Project proj){
        System.err.println("Noch nicht implementiert.");
    }

    public void delete(Project proj){
        System.err.println("Noch nicht implementiert.");
    }
}
