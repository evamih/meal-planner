/** Interfata service pentru definirea functiilor specifice meniurilor
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.services;

import com.proiectAWJ.webApp.dto.MeniuDto;
import com.proiectAWJ.webApp.dto.RetetaDto;
import com.proiectAWJ.webApp.models.Meniu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeniuService {
    List<MeniuDto> findAllMeniuri();

    Meniu saveMeniu(Meniu meniu);

    MeniuDto findMeniuById(long meniuId);

    void updateMeniu(MeniuDto meniu);

    List<RetetaDto> findRetete(Long meniuId);

    void delete(Long meniuId);
}
