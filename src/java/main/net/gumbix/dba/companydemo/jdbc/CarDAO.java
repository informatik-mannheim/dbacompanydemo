package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Car;
import net.gumbix.dba.companydemo.domain.CompanyCar;
import net.gumbix.dba.companydemo.domain.Employee;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class CarDAO extends AbstractDAO {

    public CarDAO(JdbcAccess access) {
        super(access);
    }

    // Loads a Car Object from Table "Auto"
    public Car load(String modell) throws Exception {
        ResultSet rs = executeSQLQuery("select * from Auto where modell = " + "'" + modell + "'");
        if (rs.next()) {
            return new Car(rs.getString("modell"), rs.getString("marke"));
        } else {
            throw new ObjectNotFoundException(Car.class, modell);
        }
    }

    // Store or Update an Car Object in Table "Auto"
    public void store(Car car) throws Exception {

        PreparedStatement pstmt;
        try {
            Car carNew = load(car.getModel());
            // update
            pstmt = prepareStatement("update Auto set marke = ? where modell = ?");
            pstmt.setString(1, car.getType());
            pstmt.setString(2, car.getModel());
            pstmt.execute();
        } catch (Exception e) {
            // new record
            pstmt = prepareStatement("insert into Auto values ( ?, ? )");
            pstmt.setString(1, car.getModel());
            pstmt.setString(2, car.getType());
            pstmt.execute();
        }
    }

    /**
     * Delete a Car object from table "Auto". Note that a foreign key constraint
     * may result in an exception.
     */
    public void delete(Car car) throws Exception {
        executeSQLQuery("delete from Auto where modell = " + "'" + car.getModel() + "'");
    }
}























