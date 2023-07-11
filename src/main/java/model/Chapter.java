package model;

import java.util.Objects;

public class Chapter {
    private int partitionNumber;
    private String name;
    private String address;

    public Chapter(int partitionNumber, String name, String address) {
        this.partitionNumber = partitionNumber;
        this.name = name;
        this.address = address;
    }

    public int getPartitionNumber() {
        return partitionNumber;
    }

    public void setPartitionNumber(int partitionNumber) {
        this.partitionNumber = partitionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return partitionNumber == chapter.partitionNumber && Objects.equals(name, chapter.name) && Objects.equals(address, chapter.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partitionNumber, name, address);
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "partitionNumber=" + partitionNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
