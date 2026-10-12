/** Clasa service pentru implementarea functiilor specifice retetelor
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.services.impl;

import com.proiectAWJ.webApp.dto.RetetaDto;
import com.proiectAWJ.webApp.models.Meniu;
import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.repository.MeniuRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import com.proiectAWJ.webApp.services.RetetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RetetaServiceImpl implements RetetaService {

    private RetetaRepository retetaRepository;
    private MeniuRepository meniuRepository;

    @Autowired
    public RetetaServiceImpl(RetetaRepository retetaRepository, MeniuRepository meniuRepository) {
        this.retetaRepository = retetaRepository;
        this.meniuRepository = meniuRepository;
    }
    @Override
    public List<RetetaDto> findAllRetete() {
        List<Reteta> retete = retetaRepository.findAll();
        return retete.stream().map((reteta) -> mapToRetetaDto(reteta)).collect(Collectors.toList());
    }

    @Override
    public Reteta saveReteta(Reteta reteta){
        //System.out.println("Saving reteta: " + reteta);
        //Reteta reteta = mapToRetetaNoId(retetaDto);
        return retetaRepository.save(reteta);
    }

    @Override
    public RetetaDto findRetetaById(long retetaId) {
        Reteta reteta = retetaRepository.findById(retetaId).get();
        return mapToRetetaDto(reteta);
    }

    @Override
    public void updateReteta(RetetaDto retetaDto) {
        Reteta reteta = mapToReteta(retetaDto);
        retetaRepository.save(reteta);
    }

    public Reteta updateRetetaById(Reteta reteta, Long id)
    {
        Reteta reteta1 = retetaRepository.findByRetetaId(id);
        reteta = retetaRepository.save(reteta1);
        return reteta;
    }

    @Override
    public void delete(Long retetaId) {
        retetaRepository.deleteById(retetaId);
    }

    @Override
    public List<RetetaDto> searchRetete(String query) {
        List<Reteta> retete = retetaRepository.searchRetete(query);
        return retete.stream().map(reteta -> mapToRetetaDto(reteta)).collect(Collectors.toList());
    }

    @Override
    public void addRetetaToMeniu(Long retetaId, Long meniuId) {
        Meniu meniu = meniuRepository.findById(meniuId).get();
        retetaRepository.addRetetaToMeniu(retetaId, meniu);
    }


    //    private Reteta mapToReteta(RetetaDto retetaDto) {
//        Reteta reteta = Reteta.builder()
//                .id(retetaDto.getId())
//                .nume(retetaDto.getNume())
//                .calorii(retetaDto.getCalorii())
//                .descriere(retetaDto.getDescriere())
//                .photoUrl(retetaDto.getPhotoUrl())
//                .timpPreparare(retetaDto.getTimpPreparare())
//                .creataLa(retetaDto.getCreataLa())
//                .build();
//        return reteta;
//    }
    public Reteta mapToReteta(RetetaDto retetaDto) {
        return new Reteta(
                retetaDto.getId(),
                retetaDto.getNume(),
                retetaDto.getDescriere(),
                retetaDto.getPhotoUrl(),
                retetaDto.getTimpPreparare(),
                retetaDto.getCalorii(),
                retetaDto.getCreataLa()
        );
    }
//    private Reteta mapToRetetaNoId(RetetaDto retetaDto) {
//        return new Reteta(
//                0L,
//                retetaDto.getNume(),
//                retetaDto.getDescriere(),
//                retetaDto.getPhotoUrl(),
//                retetaDto.getTimpPreparare(),
//                retetaDto.getCalorii(),
//                LocalDateTime.now()
//        );
//    }

    /*private RetetaDto mapToRetetaDto(Reteta reteta)
    {
        return RetetaDto.builder()
                .id(reteta.getId())
                .nume(reteta.getNume())
                .descriere(reteta.getDescriere())
                .photoUrl(reteta.getPhotoUrl())
                .timpPreparare(reteta.getTimpPreparare())
                .calorii(reteta.getCalorii())
                .creataLa(reteta.getCreataLa())
                .build();
    }*/
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
