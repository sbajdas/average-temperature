package com.bajdas.average.temperature.data.ingestion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bajdas.average.temperature.model.DataPoint;
import com.bajdas.average.temperature.model.WorldTemperatures;
import com.bajdas.average.temperature.model.YearlyDataDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryDaoTest {

    @Test
    void shouldConvertDataToDto() {
        //given
        InMemoryDao dao = new InMemoryDao();
        WorldTemperatures data = new WorldTemperatures();
        data.addData(new DataPoint("Warszawa", LocalDateTime.of(2000, 1, 1, 12, 12), 12.0));
        dao.update(data);
        YearlyDataDto expected = new YearlyDataDto(2000, BigDecimal.valueOf(12.0));
        //when
        List<YearlyDataDto> cityData = dao.getCityData("Warszawa");
        //then
        assertEquals(1, cityData.size());
        assertEquals(expected, cityData.get(0));
    }

    //TODO: testy na sortowanie

}
