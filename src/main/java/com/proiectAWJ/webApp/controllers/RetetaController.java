/** Clasa controller pentru gestionarea metodelor de post si get a retetelor
 * @author Mihai Eva-Elena
 * @version 10 Ianuarie 2025
 */
package com.proiectAWJ.webApp.controllers;

import com.proiectAWJ.webApp.dto.MeniuDto;
import com.proiectAWJ.webApp.dto.RetetaDto;
import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.models.RetetaIngrediente;
import com.proiectAWJ.webApp.repository.RetetaIngredienteRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import com.proiectAWJ.webApp.services.MeniuService;
import com.proiectAWJ.webApp.services.RetetaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RetetaController {
    private RetetaService retetaService;
    private RetetaRepository retetaRepository;
    private RetetaIngredienteRepository retetaIngredienteRepository;
    private MeniuService meniuService;

    @Autowired
    public RetetaController(RetetaService retetaService, RetetaRepository retetaRepository,
                            RetetaIngredienteRepository retetaIngredienteRepository, MeniuService meniuService) {
        this.retetaService = retetaService;
        this.retetaRepository = retetaRepository;
        this.retetaIngredienteRepository = retetaIngredienteRepository;
        this.meniuService = meniuService;
    }

    @GetMapping("/retete")
    public String listRetete(Model model) {
        List<RetetaDto> retete = retetaService.findAllRetete();
        model.addAttribute("retete", retete);
        return "retete-list";
    }

    @GetMapping("/retete/{retetaId}")
    public String showRetetaDetails(@PathVariable Long retetaId, Model model) {
        Reteta reteta = retetaRepository.findById(retetaId).orElseThrow(() -> new RuntimeException("Reteta not found"));
        List<RetetaIngrediente> ingrediente = retetaIngredienteRepository.findByRetetaId(retetaId);
        model.addAttribute("reteta", reteta);
        model.addAttribute("ingrediente", ingrediente);
        List<MeniuDto> meniuri = meniuService.findAllMeniuri();
        model.addAttribute("meniuri", meniuri);
        return "retete-details";
    }

    @GetMapping("/retete/{retetaId}/addToMenu")
    public String addToMeniuForm(@PathVariable Long retetaId, Model model)
    {
        List<MeniuDto> meniuri = meniuService.findAllMeniuri();
        model.addAttribute("meniuri", meniuri);
        Reteta reteta = retetaRepository.findById(retetaId).orElseThrow(() -> new RuntimeException("Reteta not found"));
        model.addAttribute("reteta", reteta);
        return "retete-add-meniu";
    }

    @PostMapping("/retete/{retetaId}/addToMenu")
    public String addToMeniu(@PathVariable Long retetaId, @RequestParam Long meniuId) {
        retetaService.addRetetaToMeniu(retetaId, meniuId);
        return "redirect:/retete/" + retetaId;
    }

    @GetMapping("/retete/new")
    public String createRetetaForm(Model model) {
        Reteta reteta = new Reteta();
        model.addAttribute("reteta", reteta);
        return "retete-create";
    }

    @PostMapping("/retete/new")
    public String saveReteta(@Valid @ModelAttribute("reteta") Reteta reteta, BindingResult result, Model model) {
//        if (reteta.getTimpPreparare() <= 0) {
//            result.rejectValue("timpPreparare", "error.timpPreparare", "Timpul de preparare trebuie să fie un număr pozitiv mai mare decât 0");
//        }
//        if (reteta.getCalorii() <= 0) {
//            result.rejectValue("calorii", "error.calorii", "Caloriile trebuie să fie un număr pozitiv mai mare decât 0");
//        }
        boolean hasErrors = false;

        // Validate and convert timpPreparare
        try {
            int timpPreparare = Integer.parseInt(reteta.getTimpPreparare());
            if (timpPreparare <= 0) {
                result.rejectValue("timpPreparare", "error.timpPreparare",
                        "Timpul de preparare trebuie să fie un număr pozitiv");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            result.rejectValue("timpPreparare", "error.timpPreparare",
                    "Timpul de preparare trebuie să fie un număr valid");
            hasErrors = true;
        }

        // Validate and convert calorii
        try {
            int calorii = Integer.parseInt(reteta.getCalorii());
            if (calorii <= 0) {
                result.rejectValue("calorii", "error.calorii",
                        "Caloriile trebuie să fie un număr pozitiv");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            result.rejectValue("calorii", "error.calorii",
                    "Caloriile trebuie să fie un număr valid");
            hasErrors = true;
        }

        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";

        boolean isValidUrl = false;
        Pattern p = Pattern.compile(regex);
        // If the string is empty return false
        isValidUrl = reteta.photoUrl != null
                && reteta.photoUrl.length() <= 455
                && p.matcher(reteta.photoUrl).matches();

        if(!isValidUrl)
        {
            result.rejectValue("photoUrl", "error.photoUrl",
                    "Link-ul fotografiei trebuie să fie unul valid de maxim 455 caractere");
        }
        if (result.hasErrors() || hasErrors) {
            return "retete-create"; // Return the form with errors
        }

        if (reteta.getCreataLa() == null) reteta.setCreataLa(LocalDateTime.now());

        retetaService.saveReteta(reteta);
        return "redirect:/retete";
    }

    @GetMapping("/retete/{retetaId}/edit")
    public String editRetetaForm(@PathVariable("retetaId") long retetaId, Model model) {
        RetetaDto reteta = retetaService.findRetetaById(retetaId);
        //preluarea efectiva a instantei din baza de date
        model.addAttribute("reteta", reteta);
        return "retete-edit";
    }

    @PostMapping("/retete/{retetaId}/edit")
    public String updateReteta(@PathVariable("retetaId") Long retetaId,
                             @Valid @ModelAttribute("reteta") RetetaDto reteta,
                             BindingResult bindingResult)
    {
//        if (reteta.getTimpPreparare() <= 0) {
//            bindingResult.rejectValue("timpPreparare", "error.timpPreparare", "Timpul de preparare trebuie să fie un număr pozitiv mai mare decât 0");
//        }
//        if (reteta.getCalorii() <= 0) {
//            bindingResult.rejectValue("calorii", "error.calorii", "Caloriile trebuie să fie un număr pozitiv mai mare decât 0");
//        }
        // Validation flags
        boolean hasErrors = false;

        // Validate and convert timpPreparare
        try {
            int timpPreparare = Integer.parseInt(reteta.getTimpPreparare());
            if (timpPreparare <= 0) {
                bindingResult.rejectValue("timpPreparare", "error.timpPreparare",
                        "Timpul de preparare trebuie să fie un număr pozitiv");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            bindingResult.rejectValue("timpPreparare", "error.timpPreparare",
                    "Timpul de preparare trebuie să fie un număr valid");
            hasErrors = true;
        }

        // Validate and convert calorii
        try {
            int calorii = Integer.parseInt(reteta.getCalorii());
            if (calorii <= 0) {
                bindingResult.rejectValue("calorii", "error.calorii",
                        "Caloriile trebuie să fie un număr pozitiv");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            bindingResult.rejectValue("calorii", "error.calorii",
                    "Caloriile trebuie să fie un număr valid");
            hasErrors = true;
        }

        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";

        boolean isValidUrl = false;
        Pattern p = Pattern.compile(regex);
        // If the string is empty return false
        isValidUrl = reteta.photoUrl != null
                && reteta.photoUrl.length() <= 455
                && p.matcher(reteta.photoUrl).matches();

        if(!isValidUrl)
        {
            bindingResult.rejectValue("photoUrl", "error.photoUrl",
                    "Link-ul fotografiei trebuie să fie unul valid de maxim 455 caractere");
        }
        if(bindingResult.hasErrors() || hasErrors){
            return "retete-edit";
        }
        reteta.setId(retetaId);
        retetaService.updateReteta(reteta);
        return "redirect:/retete";
    }

    @GetMapping("/retete/{retetaId}/delete/confirm")
    public String confirmDeleteReteta(@PathVariable Long retetaId, Model model) {
        Reteta reteta = retetaRepository.findById(retetaId).get();
        model.addAttribute("reteta", reteta);
        return "retete-delete-confirmation";
    }

    @PostMapping("/retete/{retetaId}/delete")
    public String deleteReteta(@PathVariable("retetaId") Long retetaId)
    {
        retetaService.delete(retetaId);
        return "redirect:/retete";
    }

    @GetMapping("/retete/search")
    public String searchRetete(@RequestParam(value = "query") String query, Model model)
    {
        List<RetetaDto> retete = retetaService.searchRetete(query);
        model.addAttribute("retete", retete);
        return "retete-list";
    }
}
