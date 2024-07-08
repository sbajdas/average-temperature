package com.bajdas.average.temperature.rest;

import java.math.BigDecimal;
import java.util.List;

import com.bajdas.average.temperature.data.AverageTempRepository;
import com.bajdas.average.temperature.model.YearlyDataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    AverageTempRepository repositoryMock;

    @InjectMocks
    CityService cityService;

    @Test
    void shouldGetProperData() {
        //given
        String cityName = "Warszawa";
        var expected = List.of(new YearlyDataDto(2000, BigDecimal.ONE));
        when(repositoryMock.getCityData(eq(cityName))).thenReturn(expected);

        //when
        var actual = cityService.getCityData(cityName);

        //then
        assertEquals(expected, actual);
    }

}
