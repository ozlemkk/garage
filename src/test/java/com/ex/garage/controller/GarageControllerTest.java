package com.ex.garage.controller;

import com.ex.garage.model.Car;
import com.ex.garage.model.Jeep;
import com.ex.garage.model.Truck;
import com.ex.garage.service.GarageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
class GarageControllerTest {

    @MockBean
    GarageService garageService;

    @Autowired
    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void whenValidCarInput_park_thenReturnNoException() throws Exception {
        Car car = new Car();
        car.setColor("Black");
        car.setPlate("34abc34");
        Mockito.when(garageService.park(ArgumentMatchers.any())).thenReturn("Allocated 1 slot.");
        String json = mapper.writeValueAsString(car);
        mockMvc.perform(post("/garage/park").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo("Allocated 1 slot.")));
    }

    @Test
    void whenValidJeepInput_park_thenReturnNoException() throws Exception {
        Jeep jeep = new Jeep();
        jeep.setColor("Black");
        jeep.setPlate("06abc34");

        Mockito.when(garageService.park(ArgumentMatchers.any())).thenReturn("Allocated 2 slots.");
        String json = mapper.writeValueAsString(jeep);
        mockMvc.perform(post("/garage/park").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo("Allocated 2 slots.")));
    }

    @Test
    void whenValidTruckInput_park_thenReturnNoException() throws Exception {
        Truck truck = new Truck();
        truck.setColor("White");
        truck.setPlate("41abc41");

        Mockito.when(garageService.park(ArgumentMatchers.any())).thenReturn("Allocated 4 slots.");
        String json = mapper.writeValueAsString(truck);
        mockMvc.perform(post("/garage/park").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo("Allocated 4 slots.")));
    }

    @Test
    void whenValidInputGiven_leave_thenReturnNoException() throws Exception {
        GarageService garageService = mock(GarageService.class);
        doNothing().when(garageService).leave(ArgumentMatchers.anyInt());
        mockMvc.perform(delete("/garage/leave").param("ticketId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void status() throws Exception {
        String status = "34-AB-34 Red [1,2]";
        Mockito.when(garageService.status()).thenReturn(status);
        mockMvc.perform(get("/garage/status")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", Matchers.equalTo(status)));

    }
}