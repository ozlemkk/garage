package com.ex.garage.service;

import com.ex.garage.exception.InvalidInputException;
import com.ex.garage.exception.InvalidTicketNumberException;
import com.ex.garage.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class GarageServiceTest {
    @Autowired
    GarageService garageService;

    @Test
    void whenValidCarInput_park_thenReturnNoException() throws InvalidInputException {
        Car car = new Car();
        car.setColor("Black");
        car.setPlate("34abc34");
        String park = garageService.park(car);
        assertEquals(park, "Allocated 1 slot.");
    }

    @Test
    void whenInvalidCarInput_park_thenReturnException() {
        Car car = new Car();
        car.setColor("Black");
        assertThrows(InvalidInputException.class, () -> {
            garageService.park(car);
        });
    }

    @Test
    void whenInvalidInput_leave_thenReturnException() throws InvalidTicketNumberException {
        assertThrows(InvalidTicketNumberException.class, () -> {
            garageService.leave(1);
        });
    }

    @Test
    void whenInvalidInput_status_thenReturnMessage() {
        String status = garageService.status();
        assertEquals(status, "No vehicle in the garage");
    }
}