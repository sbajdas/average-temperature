package com.bajdas.average.temperature.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.ReentrantLock;

public class AverageTemp {

    transient double temperatureSum;
    transient int datapoints;
    BigDecimal averageTemp;
    final ReentrantLock lock = new ReentrantLock();

    public AverageTemp(double temperature) {
        this.temperatureSum = temperature;
        datapoints = 1;
    }

    public AverageTemp addData(double temp) {
        lock.lock();
        temperatureSum += temp;
        datapoints++;
        lock.unlock();
        return this;
    }

    public BigDecimal getAverageTemp() {
        if (averageTemp == null) {
            double rawAverage = temperatureSum / datapoints;
            averageTemp = BigDecimal.valueOf(rawAverage).setScale(1, RoundingMode.HALF_UP);
        }
        return averageTemp;
    }

}
