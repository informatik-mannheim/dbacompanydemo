package net.gumbix.dba.companydemo.domain;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 */
public class CompanyCar {

	private String licensePlate;
	private Car car;
    private Employee driver;

	public CompanyCar() {
	}

	public CompanyCar(String licensePlate, Car car) {
		this.licensePlate = licensePlate;
		this.car = car;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
        if (driver.getCar() != this) {
            driver.setCar(this);
        }
    }

    public String toString() {
        return licensePlate;
    }

    public String toFullString() {
        String s = "Nummernschild: " + licensePlate +
                 "\nModell:        " + car;
        if (driver != null) {
            s += "\nFahrer:        " + driver;
        }
        return s;
    }
}
