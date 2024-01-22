package net.gumbix.dba.companydemo.mongodb;

import com.mongodb.client.MongoDatabase;

public class AbstractMongoDao {
 
    protected MongoDbAccess mongoDbAccess;

    public AbstractMongoDao(MongoDbAccess mongoDbAccess){
        this.mongoDbAccess = mongoDbAccess;
    }

    public MongoDatabase database(){
        return mongoDbAccess.getMongoDatabase();
    }
}
