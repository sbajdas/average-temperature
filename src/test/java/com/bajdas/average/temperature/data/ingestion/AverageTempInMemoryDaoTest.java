package com.bajdas.average.temperature.data.ingestion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.bajdas.average.temperature.data.AverageTempInMemoryDao;
import com.bajdas.average.temperature.exceptions.CityNotFoundException;
import com.bajdas.average.temperature.model.DataPoint;
import com.bajdas.average.temperature.model.WorldTemperatures;
import com.bajdas.average.temperature.model.YearlyDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AverageTempInMemoryDaoTest {

    @Mock
    CacheManager cacheManagerMock;
    @Mock
    Cache cacheMock;
    @InjectMocks
    private AverageTempInMemoryDao averageTempInMemoryDao;

    @BeforeEach
    public void setUp() {
        WorldTemperatures data = new WorldTemperatures();
        data.addData(new DataPoint("Warszawa", LocalDateTime.of(2000, 1, 1, 12, 12), 12.0));
        averageTempInMemoryDao.update(data);
    }

    @Test
    void shouldConvertDataToDto() {
        //given
        YearlyDataDto expected = new YearlyDataDto(2000, BigDecimal.valueOf(12.0));

        //when
        List<YearlyDataDto> cityData = averageTempInMemoryDao.getCityData("Warszawa");

        //then
        assertEquals(1, cityData.size());
        assertEquals(expected, cityData.get(0));
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //when
        assertThrows(CityNotFoundException.class, () -> averageTempInMemoryDao.getCityData("fakeCity"));
    }

    @Test
    void verifyCacheInvalidation() {
        //given
        when(cacheManagerMock.getCache(anyString())).thenReturn(cacheMock);

        //when
        averageTempInMemoryDao.update(new WorldTemperatures());

        //then
        verify(cacheMock).invalidate();
    }

    //TODO: testy na sortowanie

}
