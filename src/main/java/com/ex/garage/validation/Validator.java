package com.ex.garage.validation;

import com.ex.garage.exception.InvalidInputException;
import com.ex.garage.model.Vehicle;

public class Validator {
    public static boolean isValidInput(Object input) throws InvalidInputException {
        if (input == null)
            throw new InvalidInputException("Invalid input");
        if (input instanceof String) {
            if (((String) input).isEmpty())
                throw new InvalidInputException("Invalid input");
        } else if (input instanceof Vehicle) {
            if (input == null)
                throw new InvalidInputException("Invalid input");
        }
        return true;
    }
}
