/** Interfata service pentru definirea functiilor specifice ingredientelor
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.services;

import com.proiectAWJ.webApp.dto.RetetaIngredientDto;
import org.springframework.stereotype.Service;

@Service
public interface RetetaIngredienteService {
    void createIngredient(Long retetaId, RetetaIngredientDto retetaIngredientDto);

    RetetaIngredientDto findRIngredientbyId(Long retetaId, Long ingredientId);

    void updateRetetaIngredient(RetetaIngredientDto retetaIngredientDto);

    void delete(Long retetaId, Long ingredientId);
}
