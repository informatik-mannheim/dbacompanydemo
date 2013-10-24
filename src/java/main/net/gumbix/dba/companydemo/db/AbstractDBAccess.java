package net.gumbix.dba.companydemo.db;

import net.gumbix.dba.companydemo.domain.Employee;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Worker;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
abstract public class AbstractDBAccess implements DBAccess {

    public Employee loadEmployee(long persNr) throws Exception {
        // Note: An ObjectNotFound exception can be thrown anyway:
        Personnel personnel = loadPersonnel(persNr);
        if (personnel instanceof Employee) {
            return (Employee) personnel;
        } else {
            throw new ObjectNotFoundException(personnel.getClass(),
                    "Personnelnumber " +
                            persNr + " refers to an object of type " +
                            personnel.getClass().getName());
        }
    }

    public Worker loadWorker(long persNr) throws Exception {
        // Note: An ObjectNotFound exception can be thrown anyway:
        Personnel personnel = loadPersonnel(persNr);
        if (personnel instanceof Worker) {
            return (Worker) personnel;
        } else {
            throw new ObjectNotFoundException(personnel.getClass(),
                    "Personnelnumber " +
                            persNr + " refers to an object of type " +
                            personnel.getClass().getName());
        }
    }
}
