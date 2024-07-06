package com.bajdas.average.temperature.data.ingestion;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bajdas.average.temperature.exceptions.CityNotFoundException;
import com.bajdas.average.temperature.model.AverageTemp;
import com.bajdas.average.temperature.model.CityYear;
import com.bajdas.average.temperature.model.WorldTemperatures;
import com.bajdas.average.temperature.model.YearlyDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AverageTempInMemoryDao implements AverageTempRepository {

    WorldTemperatures data;

    @Override
    public void update(WorldTemperatures data) {
        this.data = data;
        log.info("Data updated");
    }

    @Override
    public List<YearlyDataDto> getCityData(String cityName) {
        var cityTemperatures = data.getCity(cityName);
        if (cityTemperatures.isEmpty()) {
            throw new CityNotFoundException(cityName);
        }
        return cityTemperatures.entrySet().stream()
                               .sorted(Comparator.comparingInt(entry -> entry.getKey().year()))
                               .map(AverageTempInMemoryDao::createYearlyDataDto)
                               .collect(Collectors.toList());
    }

    private static YearlyDataDto createYearlyDataDto(Map.Entry<CityYear, AverageTemp> entry) {
        return new YearlyDataDto(entry.getKey().year(), entry.getValue().getAverageTemp());
    }
}
