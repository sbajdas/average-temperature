package com.bajdas.average.temperature.data.ingestion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bajdas.average.temperature.model.AverageTemp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageTempTest {

    @Test
    void shouldCalculateAverageTempCorrectly() {
        //given
        BigDecimal expected = BigDecimal.valueOf(10).setScale(1, RoundingMode.HALF_UP);
        //when
        AverageTemp averageTemp = new AverageTemp(0.0);
        averageTemp.addData(10.0);
        averageTemp.addData(20.0);

        //then
        BigDecimal actual = averageTemp.getAverageTemp();
        assertEquals(expected, actual);
    }


    @Test
    void shouldCalculateAverageTempPrecisely() {
        //given
        AverageTemp averageTemp = new AverageTemp(7.15);
        BigDecimal expected = BigDecimal.valueOf(7.2).setScale(1, RoundingMode.HALF_UP);
        //when
        averageTemp.addData(7.15);
        averageTemp.addData(7.15);

        //then
        BigDecimal actual = averageTemp.getAverageTemp();
        assertEquals(expected, actual);
    }

}
