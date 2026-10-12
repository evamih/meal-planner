/** Clasa service pentru implementarea functiilor specifice meniurilor
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.services.impl;

import com.proiectAWJ.webApp.dto.MeniuDto;
import com.proiectAWJ.webApp.dto.RetetaDto;
import com.proiectAWJ.webApp.models.Meniu;
import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.repository.MeniuRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import com.proiectAWJ.webApp.services.MeniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeniuServiceImpl implements MeniuService {

    private MeniuRepository meniuRepository;
    private RetetaRepository retetaRepository;

    @Autowired
    public MeniuServiceImpl(MeniuRepository meniuRepository, RetetaRepository retetaRepository) {
        this.meniuRepository = meniuRepository;
        this.retetaRepository = retetaRepository;
    }

    @Override
    public List<MeniuDto> findAllMeniuri() {
        List<Meniu> meniuri = meniuRepository.findAll();
        return meniuri.stream().map((meniu) -> mapToMeniuDto(meniu)).collect(Collectors.toList());
    }

    @Override
    public Meniu saveMeniu(Meniu meniu) {
        return meniuRepository.save(meniu);
    }

    @Override
    public MeniuDto findMeniuById(long meniuId) {
        Meniu meniu = meniuRepository.findById(meniuId).get();
        return mapToMeniuDto(meniu);
    }

    @Override
    public void updateMeniu(MeniuDto meniuDto) {
        Meniu meniu = mapToMeniu(meniuDto);
        meniuRepository.save(meniu);
    }

    @Override
    public List<RetetaDto> findRetete(Long meniuId) {
        List<Reteta> retete = meniuRepository.findRetete(meniuId);
        return retete.stream().map(reteta -> mapToRetetaDto(reteta)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long meniuId) {
        meniuRepository.deleteById(meniuId);
    }


    private Meniu mapToMeniu(MeniuDto meniuDto) {
        return new Meniu(
                meniuDto.getId(),
                meniuDto.getNume(),
                meniuDto.getDescriere(),
                meniuDto.getProgramatLa(),
                meniuDto.getStatus(),
                meniuDto.getCreatLa()
        );
    }

    private MeniuDto mapToMeniuDto(Meniu meniu) {
        return new MeniuDto(
                meniu.getId(),
                meniu.getNume(),
                meniu.getDescriere(),
                meniu.getProgramatLa(),
                meniu.getStatus(),
                meniu.getCreatLa()
        );
    }
    private RetetaDto mapToRetetaDto(Reteta reteta) {
        return new RetetaDto(
                reteta.getId(),
                reteta.getNume(),
                reteta.getDescriere(),
                reteta.getPhotoUrl(),
                String.valueOf(reteta.getTimpPreparare()),
                String.valueOf(reteta.getCalorii()),
                reteta.getCreataLa()
        );
    }
}
