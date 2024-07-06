package com.bajdas.average.temperature.rest;

import java.util.List;

import com.bajdas.average.temperature.model.YearlyDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AverageTemperatureController {

    final CityService cityService;

    @GetMapping("/city/{cityName}")
    public ResponseEntity<List<YearlyDataDto>> getCityData(@PathVariable String cityName) {
        var cityData = cityService.getCityData(cityName);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cityData);
    }

}
