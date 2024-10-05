package ar.edu.utn.frc.tup.lciii.dtos.countries;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {;
    @NotNull
    @Positive
    @Max(value = 10, message = "debe ser menor que o igual a 10")
    private Integer amountOfCountryToSave;
}
