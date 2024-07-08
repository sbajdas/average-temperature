package com.bajdas.average.temperature.model;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldTemperaturesTest {

    @Test
    void shouldAddCitiesWithoutDuplication() {
        //given
        var worldTemperatures = new WorldTemperatures();
        DataPoint dataPoint1 = getDataPoint("City1");
        DataPoint dataPoint2 = getDataPoint("City2");
        DataPoint dataPoint3 = getDataPoint("City2");

        //when
        worldTemperatures.addData(dataPoint1);
        worldTemperatures.addData(dataPoint2);
        worldTemperatures.addData(dataPoint3);

        //then
        assertEquals(2, worldTemperatures.getWorldTemperatures().size());
    }

    @NotNull
    private static DataPoint getDataPoint(String city) {
        return new DataPoint(city, LocalDateTime.MIN, 0.0);
    }

}
