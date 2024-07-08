package com.bajdas.average.temperature.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class WorldTemperatures {

    final Map<CityYear, AverageTemp> worldTemperatures = new ConcurrentHashMap<>();

    public void addData(DataPoint dataPoint) {
        worldTemperatures.merge(new CityYear(dataPoint.city(), dataPoint.time().getYear()),
            new AverageTemp(dataPoint.temperature()),
            (existing, newValue) -> existing.addData(dataPoint.temperature()));
    }

    public Map<CityYear, AverageTemp> getByCity(String city) {
        return worldTemperatures.entrySet()
                                .stream()
                                .filter(entry -> entry.getKey().city().equals(city))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
