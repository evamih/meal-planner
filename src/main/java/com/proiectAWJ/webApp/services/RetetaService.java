/** Interfata service pentru definirea functiilor specifice retetelor
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.services;

import com.proiectAWJ.webApp.dto.RetetaDto;
import com.proiectAWJ.webApp.models.Reteta;

import java.util.List;

public interface RetetaService {
    List<RetetaDto> findAllRetete();
    Reteta saveReteta(Reteta retetaDto);
    RetetaDto findRetetaById(long retetaId);
    void updateReteta(RetetaDto reteta);
    void delete(Long retetaId);
    List<RetetaDto> searchRetete(String query);
    void addRetetaToMeniu(Long retetaId, Long meniuId);
    Reteta updateRetetaById(Reteta reteta, Long id);
}
