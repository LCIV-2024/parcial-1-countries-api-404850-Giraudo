package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDto;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.utils.Continent;
import ar.edu.utn.frc.tup.lciii.utils.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private final CountryRepository countryRepository;

        @Autowired
        private final RestTemplate restTemplate;

//        @Autowired
//        private ModelMapper modelMapper;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        public List<CountryDto> getAllCountriesDto(){
                List<Country> countries = getAllCountries();
                List<CountryDto> countriesDto = new ArrayList<>();
                for(Country country : countries){
                        countriesDto.add(mapToDTO(country));
                        // countriesDto.add(modelMapper.map(country, CountryDto.class));
                }

                return countriesDto;
        }

        public List<CountryDto> getCountriesByName(String name){
                List<CountryDto> countries = getAllCountriesDto();
                List<CountryDto> result = new ArrayList<>();
                for(CountryDto country : countries){
                        if(country.getName().equals(name)) {
                                result.add(country);
                        }
                }
                return result;
        }

        public List<CountryDto> getCountriesByCode(String code){
                List<CountryDto> countries = getAllCountriesDto();
                List<CountryDto> result = new ArrayList<>();
                for(CountryDto country : countries){
                        if(country.getCode().equals(code)) {
                                result.add(country);
                        }
                }
                return result;
        }

        public List<CountryDto> getAllCountriesByContinent(Continent cont){
                List<CountryDto> countriesDto = new ArrayList<>();
                List<Country> countries = getAllCountries();

                for(Country country : countries){
                        if(country.getRegion().equals(cont.toString())){
                                countriesDto.add(mapToDTO(country));
                        }
                }
                return countriesDto;
        }

        public List<CountryDto> getAllCountriesByLanguage(Language language){
                List<CountryDto> countriesDto = new ArrayList<>();
                List<Country> countries = getAllCountries();

                for(Country c : countries){
                        if(c.getLanguages() != null && c.getLanguages().containsValue(language.toString()))
                        {
                                countriesDto.add(mapToDTO(c));
                        }
                }

                return countriesDto;
        }


        public CountryDto mostBorders() {
                List<Country> countries = getAllCountries();
                Integer f = 0;
                Country country = new Country();
                for(Country c : countries){
                        if(c.getBorders() != null && c.getBorders().size() > f){
                                f = c.getBorders().size();
                                country = c;
                        }
                }
                return mapToDTO(country);
        }


        public List<CountryDto> saveCountries(Integer cant) {
                List<Country> countries = getAllCountries();
                List<Country> countriesSelected = new ArrayList<>();
                List<CountryDto> countriesResult = new ArrayList<>();

                for (int i = 0; i < cant; i++) {
                        countriesSelected.add(countries.get(i));
                }

                for(Country country : countriesSelected) {
                        CountryEntity countryEntity = new CountryEntity();
                        countryEntity.setName(country.getName());
                        countryEntity.setCode(country.getCode());
                        countryEntity.setArea(country.getArea());
                        countryEntity.setPopulation(country.getPopulation());

                        countryRepository.save(countryEntity);
                        countriesResult.add(mapToDTO(country));
                }
                return countriesResult;
        }




        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .code((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .name(((String) countryData.get("cca3")))
                        .region((String) countryData.get("region"))
                        .borders((List<String>) countryData.get("borders"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }


        private CountryDto mapToDTO(Country country) {
                return new CountryDto(country.getCode(), country.getName());
        }
}