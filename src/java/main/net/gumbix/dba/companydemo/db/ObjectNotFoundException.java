package net.gumbix.dba.companydemo.db;

/**
 * This exception is used within this project to indicate that
 * an object could not be found in the database.
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class ObjectNotFoundException extends RuntimeException {

    private String key;
    private Class clazz;

    public ObjectNotFoundException(Class clazz, String key) {
        this.clazz = clazz;
        this.key = key;
    }

    public String toString() {
        return "Object of type " + clazz.toString() + " with key = " + key + " not found. ";
    }
}
