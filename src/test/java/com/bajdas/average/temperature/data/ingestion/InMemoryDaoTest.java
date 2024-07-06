package com.bajdas.average.temperature.data.ingestion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bajdas.average.temperature.exceptions.CityNotFoundException;
import com.bajdas.average.temperature.model.DataPoint;
import com.bajdas.average.temperature.model.WorldTemperatures;
import com.bajdas.average.temperature.model.YearlyDataDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryDaoTest {

    private final InMemoryDao inMemoryDao = new InMemoryDao();

    @Test
    void shouldConvertDataToDto() {
        //given
        WorldTemperatures data = new WorldTemperatures();
        data.addData(new DataPoint("Warszawa", LocalDateTime.of(2000, 1, 1, 12, 12), 12.0));
        inMemoryDao.update(data);
        YearlyDataDto expected = new YearlyDataDto(2000, BigDecimal.valueOf(12.0));
        //when
        List<YearlyDataDto> cityData = inMemoryDao.getCityData("Warszawa");
        //then
        assertEquals(1, cityData.size());
        assertEquals(expected, cityData.get(0));
    }


    @Test
    void shouldThrowExceptionWhenNotFound() {
        //when
        assertThrows(CityNotFoundException.class, () -> inMemoryDao.getCityData("fakeCity"));
    }

    //TODO: testy na sortowanie

}
