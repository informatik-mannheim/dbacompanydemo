package net.gumbix.dba.companydemo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.gumbix.dba.companydemo.domain.Address;
import net.gumbix.dba.companydemo.domain.Personnel;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class AddressDAO extends AbstractDAO {

    public AddressDAO(JdbcAccess access) {
        super(access);
    }

	// Loads an Address Object from Table "Ort"
	public Address load(String zip) throws Exception {

		ResultSet rs = executeSQLQuery("select * from Ort where plz = " + zip);
		Address adr;

		if(rs.next()){

			adr = new Address(rs.getString("plz"), rs.getString("ortsname"));

		} else {

			adr = new Address();
		}

		return adr;

	}

	// Store or Update an Address Object in Table "Ort"
	public void store(Address adr) throws Exception {

		Address adrNew = load(adr.getZip());
		PreparedStatement pstmt;

		if (adrNew.getZip() == null) {

			// new record
			pstmt = access.connection.prepareStatement("insert into Ort values ( ?, ? )");
			pstmt.setString(1, adr.getZip());
			pstmt.setString(2, adr.getCity());
			pstmt.execute();

		} else {

			// update
			pstmt = access.connection.prepareStatement("update Ort set ortsname = ? where plz = ?");
			pstmt.setString(1, adr.getCity());
			pstmt.setString(2, adr.getZip());
			pstmt.execute();
		}
	}

	// Delete an Address Object from Table "Ort"
	public void delete(Address adr) throws Exception {
        // TODO
	}
}
