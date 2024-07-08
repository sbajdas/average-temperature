package com.bajdas.average.temperature.data;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bajdas.average.temperature.exceptions.CityNotFoundException;
import com.bajdas.average.temperature.model.AverageTemp;
import com.bajdas.average.temperature.model.CityYear;
import com.bajdas.average.temperature.model.WorldTemperatures;
import com.bajdas.average.temperature.model.YearlyDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AverageTempInMemoryDao implements AverageTempRepository {

    WorldTemperatures data;
    final CacheManager cacheManager;

    @Override
    public void update(WorldTemperatures data) {
        this.data = data;
        Optional.ofNullable(cacheManager.getCache("getCity")).ifPresent(Cache::invalidate);
        log.info("Data updated");
    }

    @Override
    public List<YearlyDataDto> getCityData(String cityName) {
        var cityTemperatures = data.getByCity(cityName);
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
