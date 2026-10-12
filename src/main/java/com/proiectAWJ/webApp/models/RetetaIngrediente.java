/** Clasa entitate pentru lucrul cu tabelul de legatura retete_ingrediente din baza de date
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "reteta_ingrediente")
public class RetetaIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reteta_id", nullable = false)
    private Reteta reteta;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Max(value = 10000, message = "Cantitatea ingredientului nu poate depăși 10000")
    private long cantitate;
    @NotEmpty(message = "Unitatea de măsură a ingredientului trebuie sa fie completată")
    private String unitate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RetetaIngrediente(Long id, Reteta reteta, Ingredient ingredient, long cantitate, String unitate) {
        this.id = id;
        this.reteta = reteta;
        this.ingredient = ingredient;
        this.cantitate = cantitate;
        this.unitate = unitate;
    }

    public RetetaIngrediente() {
    }
}
