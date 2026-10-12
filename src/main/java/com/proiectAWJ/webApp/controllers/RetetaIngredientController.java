/** Clasa controller pentru gestionarea metodelor de post si get a ingredientelor unei retete
 * @author Mihai Eva-Elena
 * @version 3 Ianuarie 2025
 */
package com.proiectAWJ.webApp.controllers;

import com.proiectAWJ.webApp.dto.IngredientDto;
import com.proiectAWJ.webApp.dto.RetetaIngredientDto;
import com.proiectAWJ.webApp.models.Ingredient;
import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.models.RetetaIngrediente;
import com.proiectAWJ.webApp.repository.IngredientRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import com.proiectAWJ.webApp.services.RetetaIngredienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class RetetaIngredientController {
    private RetetaIngredienteService retetaIngredienteService;
    private RetetaRepository retetaRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public RetetaIngredientController(RetetaRepository retetaRepository, IngredientRepository ingredientRepository, RetetaIngredienteService retetaIngredienteService) {
        this.retetaRepository = retetaRepository;
        this.ingredientRepository = ingredientRepository;
        this.retetaIngredienteService = retetaIngredienteService;
    }

    @GetMapping("/ingrediente/{retetaId}/new")
    public String createIngredientForm(@PathVariable("retetaId") Long retetaId, Model model) {
        RetetaIngrediente retetaIngrediente = new RetetaIngrediente();
        retetaIngrediente.setIngredient(new Ingredient());
        model.addAttribute("retetaId", retetaId);
        model.addAttribute("retetaIngredient", retetaIngrediente);
        return "ingrediente-create";
    }

    @PostMapping("/ingrediente/{retetaId}/new")
    public String saveIngredient(@PathVariable("retetaId") Long retetaId,
                                 @Valid @ModelAttribute("retetaIngredient") RetetaIngredientDto retetaIngredientDto,
                                 BindingResult result, Model model) {
        if(retetaIngredientDto.getCantitate() <= 0) {
            result.rejectValue("cantitate", "error.cantitate", "Cantitatea ingredientului trebuie să fie un număr pozitiv mai mare decât 0");
        }
        if(retetaIngredientDto.getIngredient().getNume().isEmpty()) {
            result.rejectValue("ingredient.nume", "error.ingredient.nume", "Numele ingredientului trebuie să fie completat");
        }
        String[] validUnits = {
                "gram", "g",
                "kilogram", "kg",
                "mililitru", "ml",
                "litru", "l",
                "cana", "cani",
                "lingura", "linguri",
                "lingurita", "lingurite",
                "bucata", "bucati",
                "felie", "felii"
        };
        boolean isValidUnit = Arrays.asList(validUnits).contains(retetaIngredientDto.getUnitate().toLowerCase());
        if(!isValidUnit){
            result.rejectValue("unitate", "error.unitate", "Unitatea de măsură a ingredientului trebuie să fie una acceptată general");
        }
        if(result.hasErrors()){
            return "ingrediente-create";
        }

        retetaIngredienteService.createIngredient(retetaId, retetaIngredientDto);
        return "redirect:/retete/" + retetaId;
    }

    @GetMapping("/ingrediente/{retetaId}/edit/{ingredientId}")
    public String editIngredientForm(@PathVariable("retetaId") Long retetaId,
                                     @PathVariable("ingredientId") Long ingredientId,
                                     Model model) {
        RetetaIngredientDto retetaIngredientDto = retetaIngredienteService.findRIngredientbyId(retetaId, ingredientId);
        //Ingredient ingredient = ingredientRepository.findByIngredientId(ingredientId);
        //model.addAttribute("ingredient1", ingredient);
        model.addAttribute("retetaIngredient", retetaIngredientDto);
        return "ingrediente-edit";
    }

    @PostMapping("/ingrediente/{retetaId}/edit/{ingredientId}")
    public String updateIngredient(@PathVariable("retetaId") Long retetaId,
                                   @PathVariable("ingredientId") Long ingredientId,
                                   @Valid @ModelAttribute("retetaIngredient") RetetaIngredientDto retetaIngredientDto,
                                   BindingResult result)
    {
        String nume = retetaIngredientDto.getIngredient().getNume();
        Reteta reteta_temp = retetaRepository.findByRetetaId(retetaId);
        retetaIngredientDto.setReteta(reteta_temp);
        Ingredient ingredient_temp = ingredientRepository.findByIngredientId(ingredientId);
        retetaIngredientDto.setIngredient(ingredient_temp);

        if(retetaIngredientDto.getCantitate() <= 0) {
            result.rejectValue("cantitate", "error.cantitate", "Cantitatea ingredientului trebuie să fie un număr pozitiv mai mare decât 0");
        }
        if(retetaIngredientDto.getIngredient().getNume().isEmpty()) {
            result.rejectValue("ingredient.nume", "error.ingredient.nume", "Numele ingredientului trebuie să fie completat");
        }
        String[] validUnits = {
                "gram", "g",
                "kilogram", "kg",
                "mililitru", "ml",
                "litru", "l",
                "cana", "cani",
                "lingura", "linguri",
                "lingurita", "lingurite",
                "bucata", "bucati",
                "felie", "felii"
        };
        boolean isValidUnit = Arrays.asList(validUnits).contains(retetaIngredientDto.getUnitate().toLowerCase());
        if(!isValidUnit){
            result.rejectValue("unitate", "error.unitate", "Unitatea de măsură a ingredientului trebuie să fie una acceptată general");
        }
        if(result.hasErrors()){
            return "ingrediente-edit";
        }
        retetaIngredientDto.getIngredient().setNume(nume);
        retetaIngredienteService.updateRetetaIngredient(retetaIngredientDto);
        return "redirect:/retete/" + retetaId;
    }

    @GetMapping("/ingrediente/{retetaId}/delete/{ingredientId}/confirm")
    public String confirmDeleteIngredient(@PathVariable Long retetaId, @PathVariable Long ingredientId, Model model) {
        RetetaIngredientDto retetaIngredient = retetaIngredienteService.findRIngredientbyId(retetaId, ingredientId);
        model.addAttribute("retetaIngredient", retetaIngredient);
        return "ingrediente-delete-confirmation";
    }

    @PostMapping("/ingrediente/{retetaId}/delete/{ingredientId}")
    public String deleteIngredient(@PathVariable("retetaId") Long retetaId, @PathVariable Long ingredientId)
    {
        retetaIngredienteService.delete(retetaId, ingredientId);
        return "redirect:/retete/" + retetaId;
    }
}
