package com.bajdas.average.temperature.data.ingestion;

import com.bajdas.average.temperature.model.WorldTemperatures;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataContainer implements AverageTempRepository {

    WorldTemperatures data;

    @Override
    public void update(WorldTemperatures data) {
        this.data = data;
        log.info("Data updated");
    }
}
