package com.bajdas.average.temperature.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public class WorldTemperatures {

    final Map<String, CityTemperatures> worldTemperatures = new ConcurrentHashMap<>();

    public void addData(DataPoint dataPoint) {
        worldTemperatures.merge(dataPoint.city(),
            new CityTemperatures(dataPoint),
            (existing, newValue) -> existing.addData(dataPoint));
    }

    public CityTemperatures getCity(String city) {
        return worldTemperatures.get(city);
    }
}