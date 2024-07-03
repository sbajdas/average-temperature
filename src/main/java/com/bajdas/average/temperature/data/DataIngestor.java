package com.bajdas.average.temperature.data;

import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataIngestor {

    public void ingestData(Path dataFilePath) {
        log.info("Ingesting data file");
    }
}
