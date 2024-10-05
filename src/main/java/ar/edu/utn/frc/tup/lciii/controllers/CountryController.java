package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.countries.CountryDto;
import ar.edu.utn.frc.tup.lciii.dtos.countries.PostDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    @Autowired
    private final CountryService countryService;



    // 1. Crear un endpoint que exponga todos los países. (5 puntos)
    @GetMapping("api/countries")
    public ResponseEntity<List<CountryDto>> getCountries(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "code", required = false) String code
    ) {
        List<CountryDto> countries = new ArrayList<>();
        if(name != null && !name.isEmpty()){
            countries = countryService.getCountriesByName(name);
        } else if (code != null && !code.isEmpty()){
            countries = countryService.getCountriesByCode(code);
        } else {
            countries = countryService.getAllCountriesDto();
        }

        return ResponseEntity.ok(countries);
    }

    // 2. El endpoint anterior debe permitir filtrar la lista tanto por nombre como por codigo. (5 puntos)



    // 3. Crear un endpoint que exponga todos paises que se encuentren dentro de un continente pasandoselo por parametro. (5 puntos)
    @GetMapping("api/countries/{continent}/continent")
    public ResponseEntity<List<CountryDto>> getCountriesByContinent(@PathVariable String continent) {

        List<CountryDto> countries = countryService.getAllCountriesByContinent(continent);

        return ResponseEntity.ok(countries);
    }


    // 4. Crear un endpoint que exponga todos paises que hablen un idioma pasandoselo por parametro. (10 puntos)
    @GetMapping("api/countries/{language}/language")
    public ResponseEntity<List<CountryDto>> getCountriesByLang(@PathVariable String continent) {

        List<CountryDto> countries = countryService.getAllCountriesByContinent(continent);

        return ResponseEntity.ok(countries);
    }

    // 6. Crear un endpoint que traiga el nombre del pais con mas fronteras (5 puntos)
    @GetMapping("api/countries/most-borders")
    public ResponseEntity<CountryDto> getCountryBorder() {

        CountryDto country = countryService.mostBorders();

        return ResponseEntity.ok(country);
    }


    // 7. Duplicar el endpoint creado en el primer punto, pero que sea de tipo post y reciba un body de tipo (20 puntos):
    @PostMapping("api/countries")
    public ResponseEntity<List<CountryDto>> post(@Valid @RequestBody PostDto postDto) {

        List<CountryDto> countries = countryService.saveCountries(postDto.getAmountOfCountryToSave());

        return ResponseEntity.ok(countries);
    }
}