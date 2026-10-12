/** Clasa controller pentru gestionarea metodelor de post si get a meniurilor
 * @author Mihai Eva-Elena
 * @version 7 Ianuarie 2025
 */
package com.proiectAWJ.webApp.controllers;

import com.proiectAWJ.webApp.dto.MeniuDto;
import com.proiectAWJ.webApp.dto.RetetaDto;
import com.proiectAWJ.webApp.models.Meniu;
import com.proiectAWJ.webApp.models.RetetaIngrediente;
import com.proiectAWJ.webApp.repository.MeniuRepository;
import com.proiectAWJ.webApp.repository.RetetaIngredienteRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import com.proiectAWJ.webApp.services.MeniuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MeniuController {
    private MeniuRepository meniuRepository;
    private MeniuService meniuService;
    private RetetaRepository retetaRepository;
    private RetetaIngredienteRepository retetaIngredienteRepository;

    @Autowired
    public MeniuController(MeniuRepository meniuRepository, MeniuService meniuService,
                           RetetaRepository retetaRepository, RetetaIngredienteRepository retetaIngredienteRepository) {
        this.meniuRepository = meniuRepository;
        this.meniuService = meniuService;
        this.retetaRepository = retetaRepository;
        this.retetaIngredienteRepository = retetaIngredienteRepository;
    }

    @GetMapping("/meniuri")
    public String listMeniuri(Model model)
    {
        List<MeniuDto> meniuri = meniuService.findAllMeniuri();
        model.addAttribute("meniuri", meniuri);
        return "meniuri-list";
    }

    @GetMapping("/meniuri/new")
    public String createMeniuForm(Model model){
        Meniu meniu = new Meniu();
        model.addAttribute("meniu", meniu);
        return "meniuri-create";
    }

    @PostMapping("/meniuri/new")
    public String saveMeniu(@Valid @ModelAttribute("meniu") Meniu meniu,
                            BindingResult result, Model model) {

        List<String> statusOptions = List.of("draft", "nou", "realizat");
        model.addAttribute("statusOptions", statusOptions);
        if(result.hasErrors())
        {
            return "meniuri-create";
        }
        meniuService.saveMeniu(meniu);
        return "redirect:/meniuri";
    }

    @GetMapping("/meniuri/{meniuId}/edit")
    public String editMeniuForm(@PathVariable("meniuId") long meniuId, Model model) {
        MeniuDto meniu = meniuService.findMeniuById(meniuId);
        //preluarea efectiva a instantei din baza de date
        model.addAttribute("meniu", meniu);
        model.addAttribute("statusM", meniu.getStatus());
        return "meniuri-edit";
    }

    @PostMapping("/meniuri/{meniuId}/edit")
    public String updateMeniu(@PathVariable("meniuId") Long meniuId,
                              @Valid @ModelAttribute("meniu") MeniuDto meniu,
                              BindingResult result)
    {
        Meniu meniu_existent = meniuRepository.findById(meniuId).get();
        meniu.setStatus(meniu_existent.getStatus());
        //meniu.setProgramatLa(meniu_existent.getProgramatLa());
        if(result.hasErrors())
        {
            return "meniuri-edit";
        }
        meniu.setId(meniuId);
        meniuService.updateMeniu(meniu);
        return "redirect:/meniuri";
    }

    @GetMapping("/meniuri/{meniuId}/delete/confirm")
    public  String confirmDeleteMeniu(@PathVariable("meniuId") Long meniuId, Model model) {
        Meniu meniu = meniuRepository.findById(meniuId).get();
        model.addAttribute("meniu", meniu);
        return "meniuri-delete-confirmation";
    }

    @PostMapping("/meniuri/{meniuId}/delete")
    public String deleteMeniu(@PathVariable("meniuId") Long meniuId)
    {
        meniuService.delete(meniuId);
        return "redirect:/meniuri";
    }

    @GetMapping("/meniuri/{meniuId}/retete")
    public String showReteteMeniu(@PathVariable("meniuId") Long meniuId,
                                  Model model)
    {
        List<RetetaDto> retete = meniuService.findRetete(meniuId);
        model.addAttribute("retete", retete);
        return "retete-list";
    }

    @GetMapping("/meniuri/{meniuId}/lista")
    public String showListaCumparaturi(@PathVariable("meniuId") Long meniuId,
                                       Model model)
    {
        Meniu meniu = meniuRepository.findById(meniuId).get();
        List<RetetaDto> retete = meniuService.findRetete(meniuId);
        List<RetetaIngrediente> ingrediente = new ArrayList<RetetaIngrediente>();
        for(RetetaDto retetaDto : retete){
            ingrediente.addAll(retetaIngredienteRepository.findByRetetaId(retetaDto.getId()));
        }
        model.addAttribute("ingrediente", ingrediente);
        model.addAttribute("meniu", meniu);
        return "meniuri-lista-cumparaturi";
    }

    @PostMapping("/meniuri/{meniuId}/markDone")
    public String updateStatusDone(@PathVariable("meniuId") Long meniuId) {
        // Retrieve the Meniu object
        Meniu meniu = meniuRepository.findById(meniuId).get();
        if (meniu != null) {
            // Update the status
            meniu.setStatus("realizat");
            meniuRepository.save(meniu);
        }
        // Redirect to the desired page
        return "redirect:/meniuri";
    }

    @PostMapping("/meniuri/{meniuId}/markDraft")
    public String updateStatusDraft(@PathVariable("meniuId") Long meniuId) {
        // Retrieve the Meniu object
        Meniu meniu = meniuRepository.findById(meniuId).get();
        if (meniu != null) {
            // Update the status
            meniu.setStatus("draft");
            meniuRepository.save(meniu);
        }
        // Redirect to the desired page
        return "redirect:/meniuri";
    }

}
