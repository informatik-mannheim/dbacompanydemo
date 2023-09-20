/*
A full-blown database demo developed at the
Mannheim University of Applied Sciences.

Copyright (C) 2011-2023 the authors listed below.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package net.gumbix.dba.companydemo.domain;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 */
public class CompanyCar {

	private String licensePlate;
	private Car car;
    private Employee driver;

	public CompanyCar() {}

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

    public boolean equals(Object other) {
        if (other == null || !(other instanceof CompanyCar)) {
            return false;
        } else {
            CompanyCar otherObject = (CompanyCar) other;
            return getLicensePlate() == otherObject.getLicensePlate();
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
