/** Clasa dto (data transfer object) pentru lucrul cu obiectele de tip ingredient
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
    private long id;
    @NotEmpty(message = "Numele ingredientului trebuie sa fie completat")
    private String nume;
}
