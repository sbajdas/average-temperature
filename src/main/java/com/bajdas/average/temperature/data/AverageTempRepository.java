package com.bajdas.average.temperature.data;

import java.util.List;

import com.bajdas.average.temperature.model.WorldTemperatures;
import com.bajdas.average.temperature.model.YearlyDataDto;

public interface AverageTempRepository {

    void update(WorldTemperatures data);

    List<YearlyDataDto> getCityData(String cityName);
}
