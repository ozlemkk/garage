package com.ex.garage.model;

public class Car extends Vehicle{
    public static final int SLOT_SIZE = 1;

    @Override
    public int getSize() {
        return SLOT_SIZE;
    }
}
