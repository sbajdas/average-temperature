package com.bajdas.average.temperature.data.ingestion;

import com.bajdas.average.temperature.model.WorldTemperatures;

public interface AverageTempRepository {

    void update(WorldTemperatures data);

}
