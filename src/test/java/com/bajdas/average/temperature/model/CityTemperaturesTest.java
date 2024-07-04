package com.bajdas.average.temperature.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityTemperaturesTest {

    @Test
    void shouldAddYearlyTemperatures() {
        //given
        var dataPoint1 = new DataPoint("City1", LocalDateTime.of(2000, 1, 1, 11, 11), 0.0);
        var dataPoint2 = new DataPoint("City1", LocalDateTime.of(2000, 1, 1, 11, 11), 0.0);
        var dataPoint3 = new DataPoint("City1", LocalDateTime.of(2001, 1, 1, 11, 11), 0.0);
        var cityTemperatures = new CityTemperatures(dataPoint1);

        //when
        cityTemperatures.addData(dataPoint2);
        cityTemperatures.addData(dataPoint3);

        //then
        assertEquals(2, cityTemperatures.getCityTemperatures().size());
    }

}
