package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CountryService countryService;

    private CountryDto countryDto;
    private CountryDto countryDto2;
    private Country country;
    private Country country2;

    @BeforeEach
    void setUp(){
        Country country = new Country();
        country.setName("Argentina");
        country.setCode("ARG");
        country.setRegion("region");

        Country country2 = new Country();
        country2.setName("Brasil");
        country2.setCode("BRA");
        country2.setRegion("region");

        CountryDto countryDto = new CountryDto();
        countryDto.setName("Argentina");
        countryDto.setCode("ARG");

        CountryDto countryDto2 = new CountryDto();
        countryDto2.setName("Brasil");
        countryDto2.setCode("BRA");


    }

    @Test
    void getCountries() throws Exception {

        List<CountryDto> countryList = new ArrayList<>();
        countryList.add(countryDto);
        countryList.add(countryDto2);

        when(countryService.getAllCountriesDto()).thenReturn(countryList);

        mockMvc.perform(get("/api/countries").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getCountriesByContinent() {
    }

    @Test
    void getCountriesByLang() {
    }

    @Test
    void getCountryBorder() {
    }

    @Test
    void post() {
    }
}