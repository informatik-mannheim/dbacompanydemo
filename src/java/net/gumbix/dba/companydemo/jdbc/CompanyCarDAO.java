package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.gumbix.dba.companydemo.db.ObjectNotFoundException;
import net.gumbix.dba.companydemo.domain.Car;
import net.gumbix.dba.companydemo.domain.CompanyCar;
import net.gumbix.dba.companydemo.domain.Employee;

/**
 * @author Marius Czardybon (m.czardybon@gmx.net)
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 */
public class CompanyCarDAO extends AbstractDAO {

    public CompanyCarDAO(JdbcAccess access) {
        super(access);
    }

    // Loads an CompanyCar Object from Table "Firmenwagen" using the primary key
    public CompanyCar load(String licensePlate) throws Exception {
        ResultSet rs = executeSQLQuery("select * from Firmenwagen where nummernschild = " + "'" + licensePlate + "'");
        if (rs.next()) {
            Car car = access.loadCar(rs.getString("modell"));
            long personalNr = rs.getLong("personalNr");
            CompanyCar companyCar = new CompanyCar(licensePlate, car);
            if (personalNr != 0) {
                Employee driver = access.loadEmployee(personalNr);
                driver.setCar(companyCar);
            }
            return companyCar;
        } else {
            throw new ObjectNotFoundException(CompanyCar.class, licensePlate);
        }
    }

    // Loads an CompanyCar Object from Table "Firmenwagen" using the primary key
    public List<CompanyCar> query(String licensePlate) throws Exception {
        return null;
    }

    // Store or Update an CompanyCar Object in Table "Firmenwagen"
    public void store(CompanyCar car) throws Exception {
        PreparedStatement pstmt;
        try {
            CompanyCar carNew = load(car.getLicensePlate());
            // update
            pstmt = prepareStatement("update Firmenwagen set modell = ? " +
                    "where nummernschild = ?");
            pstmt.setString(1, car.getModel().getModel());
            pstmt.setString(2, car.getLicensePlate());
            pstmt.execute();
        } catch (Exception e) {
            // new record
            pstmt = prepareStatement("insert into Auto values ( ?, ? )");
            pstmt.setString(1, car.getModel().getModel());
            pstmt.setString(2, car.getLicensePlate());
            pstmt.execute();
        }
    }

    /**
     * Delete an object from table "Firmenwagen". Note that a foreign key constraint
     * may result in an exception.
     */
    public void delete(CompanyCar car) throws Exception {
        executeSQLQuery("delete from Firmenwagen where nummernschild = '" +
                car.getLicensePlate() + "'");
    }
}
