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
public class Address {

    private String street;
    private String houseNumber;

    private ZipCity zipCity = new ZipCity();

    public Address() {
    }

    public Address(String street, String houseNumber, String zip, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCity = new ZipCity(zip, city);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZip() {
        return zipCity.getZipCode();
    }

    public void setZip(String zip) {
        zipCity.setZipCode(zip);
    }

    public String getCity() {
        return zipCity.getCity();
    }

    public void setCity(String city) {
        zipCity.setCity(city);
    }

    public ZipCity getZipCity() {
        return zipCity;
    }

    public void setZipCity(ZipCity zipCity) {
        this.zipCity = zipCity;
    }

    public String toString() {
        return street + " " + houseNumber + ", " + zipCity.zipCode +
                " " + zipCity.city;
    }

    public static class ZipCity {

        private String zipCode;
        private String city;

        // TODO must be public for Hibernate Javassist
        public ZipCity() {
        }

        public ZipCity(String zipCode, String city) {
            this.zipCode = zipCode;
            this.city = city;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}