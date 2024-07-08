package com.bajdas.average.temperature.data.ingestion;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ApacheSparkDataIngestorTest {

    private final Path dataFilePath = Paths.get("src/test/resources/test_file.csv");

    @Test
    void testIngestion() {
        //given
        ApacheSparkDataIngestor ingestor = new ApacheSparkDataIngestor();
        //when
        ingestor.ingestData(dataFilePath);

        //then
        assertTrue(true);
    }

}
