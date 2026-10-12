/** Clasa repository pentru functiile cu acces la baza de date referitoare la ingredientele unei retete
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.repository;

import com.proiectAWJ.webApp.models.RetetaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RetetaIngredienteRepository extends JpaRepository<RetetaIngrediente, Long> {
    @Query("SELECT ri FROM RetetaIngrediente ri WHERE ri.reteta.id = :retetaId")
    List<RetetaIngrediente> findByRetetaId(Long retetaId);

    @Query("select ri from RetetaIngrediente ri where ri.reteta.id = :retetaId and ri.ingredient.id = :ingredientId")
    RetetaIngrediente findByRetetaIdIngredientId(Long retetaId, Long ingredientId);
}
