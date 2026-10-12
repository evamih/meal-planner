/** Clasa dto (data transfer object) pentru lucrul cu obiectele de tip ingredient a unei retete
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.dto;

import com.proiectAWJ.webApp.models.Ingredient;
import com.proiectAWJ.webApp.models.Reteta;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
public class RetetaIngredientDto {
    private Long id;
    @Max(value = 10000, message = "Cantitatea ingredientului nu poate depăși 10000")
    private long cantitate;
    @NotEmpty(message = "Unitatea de măsură a ingredientului trebuie sa fie completată")
    private String unitate;
    private Reteta reteta;
    private Ingredient ingredient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCantitate() {
        return cantitate;
    }

    public void setCantitate(long cantitate) {
        this.cantitate = cantitate;
    }

    public String getUnitate() {
        return unitate;
    }

    public void setUnitate(String unitate) {
        this.unitate = unitate;
    }

    public Reteta getReteta() {
        return reteta;
    }

    public void setReteta(Reteta reteta) {
        this.reteta = reteta;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public RetetaIngredientDto(Long id, long cantitate, String unitate, Reteta reteta, Ingredient ingredient) {
        this.id = id;
        this.cantitate = cantitate;
        this.unitate = unitate;
        this.reteta = reteta;
        this.ingredient = ingredient;
    }

    public RetetaIngredientDto() {
    }
}
