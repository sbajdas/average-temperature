package com.bajdas.average.temperature.rest;

import java.util.List;

import com.bajdas.average.temperature.data.ingestion.AverageTempRepository;
import com.bajdas.average.temperature.model.YearlyDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CityService {

    final AverageTempRepository repository;

    public List<YearlyDataDto> getCityData(String cityName) {
        return repository.getCityData(cityName);
    }
}
