package com.bajdas.average.temperature.data.ingestion;

import java.nio.file.Path;

import com.bajdas.average.temperature.data.AverageTempRepository;
import com.bajdas.average.temperature.model.WorldTemperatures;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.col;

@Component
@ConditionalOnProperty(
    value = "spark.ingestion",
    havingValue = "true")
@Slf4j
@RequiredArgsConstructor
public class ApacheSparkDataIngestor implements DataIngestorInterface {

    final AverageTempRepository dataContainer;
    final DatapointParser parser;
    final SparkSession sparkSession;
    WorldTemperatures worldTemperatures;


    @Override
    public void ingestData(Path dataFilePath) {

        worldTemperatures = new WorldTemperatures();
        long start = System.currentTimeMillis();
        Dataset<Row> dataset = processFile(dataFilePath);
        dataset.toLocalIterator()
               .forEachRemaining(this::processLine);
        long end = System.currentTimeMillis();
        log.info("File processing via Spark done in {} ms!", end - start);
        dataContainer.update(worldTemperatures);
    }

    private Dataset<Row> processFile(Path dataFilePath) {
        return sparkSession.read()
                    .option("timestampFormat", "yyyy")
                    .option("delimiter", ";")
                    .schema("city STRING, time Timestamp, temp DOUBLE")
                    .csv(dataFilePath.toString())
                    .groupBy(col("city"), col("time").alias("year"))
                    .agg(avg("temp"));
    }

    @SneakyThrows
    private void processLine(Row line) {
        var dataPoint = parser.parse(line);
        worldTemperatures.addData(dataPoint);
    }
}
