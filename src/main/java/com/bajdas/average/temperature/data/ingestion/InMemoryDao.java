package com.bajdas.average.temperature.data.ingestion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bajdas.average.temperature.exceptions.CityNotFoundException;
import com.bajdas.average.temperature.model.AverageTemp;
import com.bajdas.average.temperature.model.CityTemperatures;
import com.bajdas.average.temperature.model.WorldTemperatures;
import com.bajdas.average.temperature.model.YearlyDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InMemoryDao implements AverageTempRepository {

    WorldTemperatures data;

    @Override
    public void update(WorldTemperatures data) {
        this.data = data;
        log.info("Data updated");
    }

    @Override
    public List<YearlyDataDto> getCityData(String cityName) {
        var cityTemperatures = data.getCity(cityName);
        return cityTemperatures.map(InMemoryDao::getYearlyDataDtos)
                               .orElseThrow(() -> new CityNotFoundException(cityName));
    }

    private static List<YearlyDataDto> getYearlyDataDtos(CityTemperatures cityTemperatures) {
        return cityTemperatures.getCityTemperatures().entrySet()
                               .stream()
                               .sorted(Map.Entry.comparingByKey())
                               .map(InMemoryDao::createYearlyDataDto)
                               .collect(Collectors.toList());
    }

    private static YearlyDataDto createYearlyDataDto(Map.Entry<Integer, AverageTemp> entry) {
        return new YearlyDataDto(entry.getKey(), entry.getValue().getAverageTemp());
    }
}
