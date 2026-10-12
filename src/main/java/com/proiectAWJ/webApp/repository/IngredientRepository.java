/** Clasa repository pentru functiile cu acces la baza de date referitoare la ingrediente
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.repository;

import com.proiectAWJ.webApp.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("select i from Ingredient i where i.id = :ingredientId")
    Ingredient findByIngredientId(Long ingredientId);
}
