package com.bajdas.average.temperature.data.ingestion;

import java.time.LocalDateTime;

import com.bajdas.average.temperature.model.DataPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatapointParserTest {

    private final DatapointParser parser = new DatapointParser();

    @Test
    void shouldParseCSVToDatapoint() {
        //given
        var testLine = "Warszawa;2018-10-20 19:12:51.235;13.12";
        var expectedTime = LocalDateTime.of(2018, 10, 20, 19, 12, 51, 235000000);
        var expected = new DataPoint("Warszawa", expectedTime, 13.12);
        //when
        DataPoint actual = parser.parse(testLine);
        //then
        assertEquals(expected, actual);

    }

    @Test
    void shouldThrowException() {
        //given
        var testLine = "Warszawa;20218-10-20 19:12:51.235;13.12";

        //then
        assertThrows(ParsingException.class, () -> parser.parse(testLine));
    }

}
