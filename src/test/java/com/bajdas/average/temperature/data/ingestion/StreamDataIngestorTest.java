package com.bajdas.average.temperature.data.ingestion;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.bajdas.average.temperature.model.WorldTemperatures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StreamDataIngestorTest {

    private final Path dataFilePath = Paths.get("src/test/resources/test_file.csv");
    @Mock
    InMemoryDao dataContainerMock;

    StreamDataIngestor streamDataIngestor;

    @BeforeEach
    void setUp() {
        streamDataIngestor = new StreamDataIngestor(dataContainerMock, new DatapointParser());
    }

    @Test
    void shouldIngestDataToContainer() {
        //given

        //when
        streamDataIngestor.ingestData(dataFilePath);

        //then
        Mockito.verify(dataContainerMock).update(any());
    }

    @Test
    void shouldProcessFile() {
        //given
        var captor = ArgumentCaptor.forClass(WorldTemperatures.class);
        //when
        streamDataIngestor.ingestData(dataFilePath);

        //then
        Mockito.verify(dataContainerMock).update(captor.capture());
        WorldTemperatures actualData = captor.getValue();
        assertEquals(1, actualData.getWorldTemperatures().size());
        assertEquals(2, actualData.getCity("Warszawa").map(city -> city.getCityTemperatures().size()).orElse(0));

    }

}
