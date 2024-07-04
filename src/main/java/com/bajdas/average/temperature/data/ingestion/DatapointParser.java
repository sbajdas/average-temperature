package com.bajdas.average.temperature.data.ingestion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bajdas.average.temperature.model.DataPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatapointParser {

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public DataPoint parse(String line) throws ParsingException {
        try {
            String[] elements = line.split(";");
            String city = elements[0];
            LocalDateTime time = LocalDateTime.parse(elements[1], formatter);
            double temperature = Double.parseDouble(elements[2]);
            return new DataPoint(city, time, temperature);
        } catch (Exception e) {
            log.error("Exception in parsing line: [{}]", line, e);
            throw new ParsingException();
        }
    }
}
