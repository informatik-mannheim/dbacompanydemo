package net.gumbix.dba.companydemo.mongodb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MongoDbIdGenerator {
	private long id;

	public MongoDbIdGenerator() {
		dateiEinlesen();
	}

	private void dateiUpdate() {
		BufferedWriter br = null;
		id++;
		try {
			br = new BufferedWriter(new FileWriter("MongoIdGenerator.csv"));
			br.write(""+id);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}

	private void dateiEinlesen() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("MongoIdGenerator.csv"));
			String datenAlsString = br.readLine();
			String [] tempArray = datenAlsString.split(";");
			long temp = Long.parseLong(tempArray[0]);
			this.id = temp;
		} catch (IOException e) {
		}
		finally {
			try {
				br.close();
			}catch(IOException e) {
			}
		}
	}

	public long getID() {
		dateiUpdate();
		return id;
	}
}
