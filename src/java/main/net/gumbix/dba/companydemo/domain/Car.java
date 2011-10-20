package net.gumbix.dba.companydemo.domain;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 */
public class Car {

	private String model;
	private String type;

	public Car(String model, String type) {
		this.model = model;
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String toString() {
        return type + " " + model;
    }
}
