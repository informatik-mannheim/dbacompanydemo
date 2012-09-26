package net.gumbix.dba.companydemo.hibernate;

import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.domain.Personnel;
import net.gumbix.dba.companydemo.jdbc.JdbcAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.ResultSet;

/**
 * (c) 2012 by Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Markus Gumbel
 */
public class HibernateIdGenerator extends IdGenerator {
    private Session session;

    public HibernateIdGenerator(Session session) {
        this.session = session;
    }

    public long getNextLong(Class clazz) {
        try {
            if (clazz.equals(Personnel.class)) {
                Transaction transaction = session.beginTransaction();
                ResultSet rs = session
                        .connection().createStatement()
                        .executeQuery("select max(personalNr) from Mitarbeiter");
                rs.next();
                long next = rs.getLong(1) + 1;
                rs.close();
                transaction.commit();
                return next;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Unknown class: " + clazz);
    }
}
