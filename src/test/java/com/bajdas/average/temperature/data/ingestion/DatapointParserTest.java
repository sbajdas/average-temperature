package com.bajdas.average.temperature.data.ingestion;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.bajdas.average.temperature.exceptions.ParsingException;
import com.bajdas.average.temperature.model.DataPoint;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class DatapointParserTest {

    private final DatapointParser parser = new DatapointParser();

    @Test
    void shouldParseCSVToDatapoint() throws ParsingException {
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
        var testLine = "Warszawa;notaDate-10-20 19:12:51.235;1a3.12";

        //then
        assertThrows(ParsingException.class, () -> parser.parse(testLine));
    }

    @Test
    void shouldParseRow() throws ParsingException {
        //given
        Row row = Mockito.mock(Row.class);
        when(row.getString(eq(0))).thenReturn("Warszawa");
        when(row.getTimestamp(eq(1))).thenReturn(Timestamp.valueOf("2018-10-20 19:12:51.235"));
        when(row.getDouble(eq(2))).thenReturn(13.12d);
        var expectedTime = LocalDateTime.of(2018, 10, 20, 19, 12, 51, 235000000);
        var expected = new DataPoint("Warszawa", expectedTime, 13.12);

        //when
        DataPoint actual = parser.parse(row);

        //then
        assertEquals(expected, actual);

    }

}
