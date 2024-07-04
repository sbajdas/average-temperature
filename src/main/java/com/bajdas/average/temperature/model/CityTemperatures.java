package com.bajdas.average.temperature.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public class CityTemperatures {

    final Map<Integer, AverageTemp> cityTemperatures;

    public CityTemperatures(DataPoint dataPoint) {
        cityTemperatures = new ConcurrentHashMap<>();
        addData(dataPoint);
    }

    public CityTemperatures addData(DataPoint dataPoint) {
        cityTemperatures.merge(dataPoint.time().getYear(),
            new AverageTemp(dataPoint.temperature()),
            (existing, newValue) -> existing.addData(dataPoint.temperature()));
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        cityTemperatures.forEach((k, v) -> builder.append(k).append(": ").append(v.getAverageTemp()).append("\n"));
        return builder.toString();
    }
}
