package net.gumbix.dba.companydemo.domain;

/**
 * @author Markus Gumbel (m.gumbel@hs-mannheim.de)
 * @author Marius Czardybon (m.czardybon@gmx.net)
 */
public class Department {

    private long depNumber;
    private String name;

    public Department(long depNumber, String name) {
        this.depNumber = depNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDepNumber() {
        return depNumber;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof Department)) {
            return false;
        } else {
            Department otherObject = (Department) other;
            return getDepNumber() == otherObject.getDepNumber();
        }
    }

    public String toString() {
        return depNumber + ": " + name;
    }
}
