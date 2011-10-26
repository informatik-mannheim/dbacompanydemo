package net.gumbix.dba.companydemo.jdbc;

import java.sql.PreparedStatement;
import java.util.List;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.domain.Worker;

/**
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class WorkerDAO extends PersonnelDAO {

    public WorkerDAO(JdbcAccess access) {
        super(access);
    }

    public Worker load(long persNr) throws Exception {
        // Note: An ObjectNotFound exception can be thrown anyway:
        Personnel personnel = access.loadPersonnel(persNr);
        if (personnel instanceof Worker) {
            return (Worker) personnel;
        } else {
            throw new ObjectNotFoundException(personnel.getClass(),
                    "Personnelnumber " +
                            persNr + " refers to an object of type " +
                            personnel.getClass().getName());
        }
    }

    public void store(Worker worker) throws Exception {
        // First create an entry in table Mitarbeiter:
        super.store(worker);

        // Then also fill in the data for the worker:
        try {
            Personnel personnel = load(worker.getPersonnelNumber());

            // update
            PreparedStatement pstmt =
                    prepareStatement("update Arbeiter set arbeitsplatz = ?, " +
                            " where personalNr = ?");
            pstmt.setString(1, worker.getWorkspace());
            pstmt.setLong(2, worker.getPersonnelNumber());
            pstmt.execute();
            pstmt.close();
        } catch (ObjectNotFoundException e) {
            // new
            PreparedStatement pstmt =
                    prepareStatement("insert into Arbeiter values (?, ?)");
            pstmt.setLong(1, worker.getPersonnelNumber());
            pstmt.setString(2, worker.getWorkspace());
            pstmt.execute();
            pstmt.close();
        }
    }

    public void delete(Worker worker) throws Exception {
        PreparedStatement pstmt =
                prepareStatement("delete from Arbeiter where personalNr = ?");
        pstmt.setLong(1, worker.getPersonnelNumber());
        pstmt.execute();
        pstmt.close();

        super.delete(worker);
    }
}
