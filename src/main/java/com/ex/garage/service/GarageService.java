package com.ex.garage.service;

import com.ex.garage.exception.InvalidInputException;
import com.ex.garage.exception.InvalidTicketNumberException;
import com.ex.garage.model.Vehicle;
import com.ex.garage.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class GarageService {

    public static final int GARAGE_SLOT_SIZE = 10;
    private int ticketId = 1;
    private final HashMap<Integer, Vehicle> vehicleMap = new HashMap<>();
    private final boolean[] slotStatus = new boolean[GARAGE_SLOT_SIZE];

    public String park(Vehicle vehicle) throws InvalidInputException {
        log.info("park started");
        Validator.isValidInput(vehicle);
        Validator.isValidInput(vehicle.getColor());
        Validator.isValidInput(vehicle.getPlate());
        for (int i = 1; i < GARAGE_SLOT_SIZE + 1; i++) {
            if (checkParkingAvailable(vehicle, i)) {
                return parkVehicle(vehicle, i);
            }
        }
        return "Garage is full.";
    }

    private boolean checkParkingAvailable(Vehicle vehicle, int i) {
        if (!slotStatus[i - 1] && (i != 10 && !slotStatus[i])) {
            int requiredSlotNumbers = i + vehicle.getSize();
            if (requiredSlotNumbers < GARAGE_SLOT_SIZE) {
                boolean checkSlotsFull = IntStream.rangeClosed(i, requiredSlotNumbers).anyMatch(j -> slotStatus[j]);
                if (checkSlotsFull) return false;
            } else
                return false;
            return true;
        } else {
            return false;
        }
    }


    private String parkVehicle(Vehicle vehicle, int index) {
        vehicle.setParkingIndexStart(index == 1 ? index : index + 1);
        vehicleMap.put(ticketId++, vehicle);
        for (int i = index; i < index + vehicle.getSize(); i++) {
            switch (index) {
                case 1:
                    slotStatus[i - 1] = true;
                    break;
                default:
                    slotStatus[i] = true;
                    break;
            }
        }
        log.info("park : parked vehicle :" + (ticketId - 1));
        return "Allocated " + vehicle.getSize() + (vehicle.getSize() == 1 ? " slot." : " slots.");
    }

    public void leave(int ticketId) throws InvalidTicketNumberException {
        log.info("leave : started");
        Vehicle parkedVehicle = vehicleMap.get(ticketId);
        if (parkedVehicle != null) {
            for (int i = parkedVehicle.getParkingIndexStart(); i < parkedVehicle.getParkingIndexStart() + parkedVehicle.getSize(); i++)
                slotStatus[i] = false;
            vehicleMap.remove(ticketId);
            log.info("leave : removed vehicle :" + ticketId);
        } else {
            throw new InvalidTicketNumberException("Invalid ticket number: " + ticketId);
        }
    }

    public String status() {
        log.info("status : started");
        return !vehicleMap.isEmpty() ? "Status:" + System.lineSeparator() +
                vehicleMap.values().stream().sorted()
                        .map(Object::toString).collect(Collectors.joining())
                : "No vehicle in the garage";
    }
}
