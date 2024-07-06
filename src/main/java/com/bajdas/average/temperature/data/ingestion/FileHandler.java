package com.bajdas.average.temperature.data.ingestion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileHandler {

    @Value("${data.file.uri}")
    String dataFileUri;
    final DataIngestorInterface dataIngestor;

    Path dataFilePath;
    FileTime lastModifiedTime;

    @PostConstruct
    void init(){
        dataFilePath = Paths.get(dataFileUri);
    }

    @Scheduled(fixedDelayString = "${file.refresh.cadence.milliseconds}")
    void checkFileForUpdates() {
        FileTime actualLastModifiedTime = getLastModifiedTime();
        if (!actualLastModifiedTime.equals(lastModifiedTime)) {
            lastModifiedTime = actualLastModifiedTime;
            log.info("There is new file available, modified at: {}", lastModifiedTime);
            dataIngestor.ingestData(dataFilePath);
        }
    }

    private FileTime getLastModifiedTime() {
        try {
            return Files.getLastModifiedTime(dataFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
