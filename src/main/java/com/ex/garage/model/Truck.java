package com.ex.garage.model;

public class Truck extends Vehicle{
    public static final int SLOT_SIZE = 4;

    @Override
    public int getSize() {
        return SLOT_SIZE;
    }
}
