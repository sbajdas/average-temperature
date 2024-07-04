package com.bajdas.average.temperature.model;

import java.time.LocalDateTime;

public record DataPoint(
    String city,
    LocalDateTime time,
    double temperature) {

}
