package com.bajdas.average.temperature.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.bajdas.average.temperature.exceptions.CityNotFoundException;
import com.bajdas.average.temperature.model.YearlyDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class AverageTemperatureControllerTest {

    private MockMvc mvc;

    @Mock
    private CityService cityServiceMock;

    @InjectMocks
    private AverageTemperatureController averageTemperatureController;


    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
//        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(averageTemperatureController)
                             .setControllerAdvice(new ExceptionsHandler())
                             .build();
    }

    @Test
    void shouldReturnProperResponse() throws Exception {
        //given
        when(cityServiceMock.getCityData(eq("Kraków"))).thenReturn(List.of(new YearlyDataDto(2024, BigDecimal.ONE.setScale(1, RoundingMode.HALF_UP))));

        //when
        MockHttpServletResponse response = mvc.perform(
                                                  get("/city/Kraków")
                                                      .accept(MediaType.APPLICATION_JSON))
                                              .andReturn().getResponse();

        //then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"year\":2024,\"averageTemperature\":1.0}]", response.getContentAsString());
    }

    @Test
    void shouldReturnBadRequestForCityNotFound() throws Exception {
        //given
        when(cityServiceMock.getCityData(eq("non-existing"))).thenThrow(new CityNotFoundException("non-existing"));
        //when
        MockHttpServletResponse response = mvc.perform(
                                                  get("/city/non-existing")
                                                      .accept(MediaType.APPLICATION_JSON))
                                              .andReturn().getResponse();
        //then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("City not found: non-existing", response.getContentAsString());
    }
}
