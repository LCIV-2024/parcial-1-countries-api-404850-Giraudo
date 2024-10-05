package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {
    @SpyBean
    private CountryService countryService;
    @MockBean
    private CountryRepository countryRepository;

    @MockBean
    private RestTemplate restTemplate;

    private CountryDto countryDto;
    private CountryDto countryDto2;
    private Country country;
    private Country country2;
    private List<Country> countryList;
    private List<CountryDto> countryDtoList;

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

        List<Country> countryList = new ArrayList<>();
        countryList.add(country);
        countryList.add(country2);

        List<CountryDto> countryDtoList = new ArrayList<>();
        countryDtoList.add(countryDto);
        countryDtoList.add(countryDto2);
    }

    @Test
    void getAllCountries() {

    }

    @Test
    void getAllCountriesDto() {
        String url = "https://restcountries.com/v3.1/all";
        when(restTemplate.getForObject(url, List.class));
        when(countryService.getAllCountries()).thenReturn(countryList);

        List<CountryDto> result = countryService.getAllCountriesDto();

        assertEquals(2, result.size());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    void getCountriesByName() {
    }

    @Test
    void getCountriesByCode() {
    }

    @Test
    void getAllCountriesByContinent() {
    }

    @Test
    void getAllCountriesByLanguage() {
    }

    @Test
    void mostBorders() {
    }

    @Test
    void saveCountries() {
    }
}