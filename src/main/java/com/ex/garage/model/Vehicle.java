package com.ex.garage.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.IntStream;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Truck.class, name = "Truck"),
        @JsonSubTypes.Type(value = Jeep.class, name = "Jeep"),
        @JsonSubTypes.Type(value = Car.class, name = "Car")
})
@Getter
@Setter
public abstract class Vehicle implements Comparable<Vehicle> {
    private String plate;
    private String color;
    private int parkingIndexStart;

    public abstract int getSize();

    @Override
    public int compareTo(Vehicle o) {
        return Integer.compare(this.getParkingIndexStart(), o.getParkingIndexStart());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getPlate());
        stringBuilder.append(" ");
        stringBuilder.append(getColor());
        stringBuilder.append("[");
        IntStream.range(parkingIndexStart, parkingIndexStart + getSize()).forEach(i -> {
            stringBuilder.append(i);
            if (i != parkingIndexStart + getSize() - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append("]");
            }
        });
        return stringBuilder.toString();
    }
}
