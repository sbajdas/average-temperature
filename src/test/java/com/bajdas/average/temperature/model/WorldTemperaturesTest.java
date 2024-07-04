package com.bajdas.average.temperature.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldTemperaturesTest {

    @Test
    void shouldAddCitiesWithoutDuplication() {
        //given
        var worldTemperatures = new WorldTemperatures();
        DataPoint dataPoint1 = new DataPoint("City1", LocalDateTime.MIN, 0.0);
        DataPoint dataPoint2 = new DataPoint("City2", LocalDateTime.MIN, 0.0);
        DataPoint dataPoint3 = new DataPoint("City2", LocalDateTime.MIN, 0.0);

        //when
        worldTemperatures.addData(dataPoint1);
        worldTemperatures.addData(dataPoint2);
        worldTemperatures.addData(dataPoint3);

        //then
        assertEquals(2, worldTemperatures.getWorldTemperatures().size());
    }

}
