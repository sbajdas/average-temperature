package com.bajdas.average.temperature.data.ingestion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.bajdas.average.temperature.model.DataPoint;
import com.bajdas.average.temperature.model.WorldTemperatures;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
    value = "spark.ingestion",
    havingValue = "false")
@Slf4j
@RequiredArgsConstructor
public class StreamDataIngestor implements DataIngestorInterface {

    final AverageTempRepository dataContainer;
    final DatapointParser parser;
    private WorldTemperatures worldTemperatures;

    public void ingestData(Path dataFilePath) {
        log.info("Processing data file: {}", dataFilePath);
        long start = System.currentTimeMillis();
        worldTemperatures = new WorldTemperatures();
        processFile(dataFilePath);
        long end = System.currentTimeMillis();
        log.info("File processing done in {} ms!", end - start);

        dataContainer.update(worldTemperatures);
    }

    private void processFile(Path dataFilePath) {
        try (Stream<String> fileStream = Files.lines(dataFilePath)) {
            fileStream
                .parallel()
                .forEach(this::processLine);
        } catch (IOException e) {
            log.error("File access error", e);
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void processLine(String line) {
        DataPoint dataPoint = parser.parse(line);
        worldTemperatures.addData(dataPoint);
    }
}
