package net.gumbix.dba.companydemo.jdbc;

import net.gumbix.dba.companydemo.db.IdGenerator;
import net.gumbix.dba.companydemo.domain.Personnel;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class JdbcIdGenerator extends IdGenerator {

    private JdbcAccess access;

    public JdbcIdGenerator(JdbcAccess access) {
        this.access = access;
    }

    public long getNextLong(Class clazz) {
        try {
            if (clazz.equals(Personnel.class)) {
                return access.nextPersonnelId();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Unknown class: " + clazz);
    }
}
