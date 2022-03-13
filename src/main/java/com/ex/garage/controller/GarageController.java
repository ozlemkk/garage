package com.ex.garage.controller;

import com.ex.garage.exception.InvalidInputException;
import com.ex.garage.exception.InvalidTicketNumberException;
import com.ex.garage.model.Vehicle;
import com.ex.garage.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/garage")
public class GarageController {

    @Autowired
    private GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping(path = "/park", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String park(@RequestBody @Validated Vehicle vehicle) throws InvalidInputException {
        try {
            return garageService.park(vehicle);
        } catch (InvalidInputException e) {
            throw e;
        } catch (Exception e) {
            return "Try again.";
        }
    }

    @DeleteMapping(path = "/leave", produces = MediaType.APPLICATION_JSON_VALUE)
    public void leave(@RequestParam("ticketId") int ticketId) throws InvalidTicketNumberException {
        garageService.leave(ticketId);
    }

    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String status() {
        try {
            return garageService.status();
        } catch (Exception e) {
            return "Try again.";
        }
    }
}