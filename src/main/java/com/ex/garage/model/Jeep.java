package com.ex.garage.model;

public class Jeep extends Vehicle{
    public static final int SLOT_SIZE = 2;

    @Override
    public int getSize() {
        return SLOT_SIZE;
    }
}
