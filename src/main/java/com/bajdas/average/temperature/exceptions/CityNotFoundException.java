package com.bajdas.average.temperature.exceptions;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String cityName) {
        super(String.format("City not found: %s", cityName));
    }
}
