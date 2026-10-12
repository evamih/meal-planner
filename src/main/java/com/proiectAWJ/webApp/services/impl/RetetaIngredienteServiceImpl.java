/** Clasa service pentru implementarea functiilor specifice ingredientelor
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.services.impl;

import com.proiectAWJ.webApp.dto.RetetaIngredientDto;
import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.models.RetetaIngrediente;
import com.proiectAWJ.webApp.repository.IngredientRepository;
import com.proiectAWJ.webApp.repository.RetetaIngredienteRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import com.proiectAWJ.webApp.services.RetetaIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetetaIngredienteServiceImpl implements RetetaIngredienteService {
    private RetetaIngredienteRepository retetaIngredienteRepository;
    private IngredientRepository ingredientRepository;
    private RetetaRepository retetaRepository;

    @Autowired
    public RetetaIngredienteServiceImpl(RetetaRepository retetaRepository, RetetaIngredienteRepository retetaIngredienteRepository, IngredientRepository ingredientRepository) {
        this.retetaRepository = retetaRepository;
        this.retetaIngredienteRepository = retetaIngredienteRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void createIngredient(Long retetaId, RetetaIngredientDto retetaIngredientDto) {
        Reteta reteta = retetaRepository.findById(retetaId).get();
        RetetaIngrediente retetaIngredient = mapToRetetaIngredient(retetaIngredientDto);
        retetaIngredient.setReteta(reteta);
        ingredientRepository.save(retetaIngredient.getIngredient());
        retetaIngredienteRepository.save(retetaIngredient);
    }

    @Override
    public RetetaIngredientDto findRIngredientbyId(Long retetaId, Long ingredientId) {
        RetetaIngrediente retetaIngredient = retetaIngredienteRepository.findByRetetaIdIngredientId(retetaId, ingredientId);
        return mapToRetetaIngredientDto(retetaIngredient);
    }

    @Override
    public void updateRetetaIngredient(RetetaIngredientDto retetaIngredientDto) {
        RetetaIngrediente retetaIngredient = mapToRetetaIngredient(retetaIngredientDto);
        retetaIngredienteRepository.save(retetaIngredient);
    }

    @Override
    public void delete(Long retetaId, Long ingredientId) {
        RetetaIngrediente retetaIngredient = retetaIngredienteRepository.findByRetetaIdIngredientId(retetaId, ingredientId);
        retetaIngredienteRepository.delete(retetaIngredient);
    }

    private RetetaIngrediente mapToRetetaIngredient(RetetaIngredientDto retetaIngredientDto)
    {
        return new RetetaIngrediente(
                retetaIngredientDto.getId(),
                retetaIngredientDto.getReteta(),
                retetaIngredientDto.getIngredient(),
                retetaIngredientDto.getCantitate(),
                retetaIngredientDto.getUnitate()
                );
    }

    private RetetaIngredientDto mapToRetetaIngredientDto(RetetaIngrediente retetaIngredient)
    {
        return new RetetaIngredientDto(
                retetaIngredient.getId(),
                retetaIngredient.getCantitate(),
                retetaIngredient.getUnitate(),
                retetaIngredient.getReteta(),
                retetaIngredient.getIngredient()
        );
    }
}
