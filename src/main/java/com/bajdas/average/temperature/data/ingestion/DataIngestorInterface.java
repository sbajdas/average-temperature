package com.bajdas.average.temperature.data.ingestion;

import java.nio.file.Path;

public interface DataIngestorInterface {

    void ingestData(Path dataFilePath);

}
