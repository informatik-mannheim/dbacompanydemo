package net.gumbix.dba.companydemo.domain;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class CompanyCar {

	private String licensePlate;
	private Car model;
    private Employee driver;

	public CompanyCar() {
	}

	public CompanyCar(String licensePlate, Car model) {
		this.licensePlate = licensePlate;
		this.model = model;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Car getModel() {
		return model;
	}

	public void setModel(Car model) {
		this.model = model;
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
}
