package com.bajdas.average.temperature.rest;

import java.util.List;

import com.bajdas.average.temperature.data.AverageTempRepository;
import com.bajdas.average.temperature.model.YearlyDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CityService {

    final AverageTempRepository repository;

    @Cacheable("getCity")
    public List<YearlyDataDto> getCityData(String cityName) {
        return repository.getCityData(cityName);
    }
}
