/** Clasa entitate pentru lucrul cu tabelul ingrediente din baza de date
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingrediente")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Numele ingredientului trebuie sa fie completat")
    private String nume;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Set<RetetaIngrediente> getRetetaIngrediente() {
        return retetaIngrediente;
    }

    public void setRetetaIngrediente(Set<RetetaIngrediente> retetaIngrediente) {
        this.retetaIngrediente = retetaIngrediente;
    }

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.REMOVE)
    private Set<RetetaIngrediente> retetaIngrediente = new HashSet<>();
}
