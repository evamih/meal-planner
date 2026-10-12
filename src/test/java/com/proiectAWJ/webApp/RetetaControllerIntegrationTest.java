package com.proiectAWJ.webApp;

import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.repository.RetetaIngredienteRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class RetetaControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired RetetaRepository retetaRepository;
    @Autowired RetetaIngredienteRepository retetaIngredienteRepository;

    @BeforeEach
    void cleanDatabase() {
        retetaIngredienteRepository.deleteAll();
        retetaRepository.deleteAll();
    }

    @Test
    void validForm_savesRecipeAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/retete/new")
                        .param("nume", "Reteta din formular")
                        .param("descriere", "Test de integrare")
                        .param("timpPreparare", "25")
                        .param("calorii", "300")
                        .param("photoUrl", "https://example.com/poza.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/retete"));

        assertThat(retetaRepository.findAll())
                .singleElement()
                .satisfies(r -> {
                    assertThat(r.getNume()).isEqualTo("Reteta din formular");
                    assertThat(r.getCreataLa()).isNotNull();   // your controller sets it
                });
    }

    @Test
    void negativeCalories_showsFormAgain_andSavesNothing() throws Exception {
        mockMvc.perform(post("/retete/new")
                        .param("nume", "Reteta invalida")
                        .param("descriere", "Calorii negative")
                        .param("timpPreparare", "25")
                        .param("calorii", "-5")
                        .param("photoUrl", "https://example.com/poza.jpg"))
                .andExpect(status().isOk())                          // no redirect
                .andExpect(view().name("retete-create"))             // back to the form
                .andExpect(model().attributeHasFieldErrors("reteta", "calorii"));

        assertThat(retetaRepository.count()).isZero();             // nothing stored
    }

    @Test
    void listPage_showsRecipesFromDatabase() throws Exception {
        retetaRepository.save(TestData.validReteta("Din baza de date"));

        mockMvc.perform(get("/retete"))
                .andExpect(status().isOk())
                .andExpect(view().name("retete-list"))
                .andExpect(model().attributeExists("retete"))
                .andExpect(content().string(containsString("Din baza de date")));
    }

    @Test
    void missingPhotoUrl_showsFormError_insteadOfCrashing() throws Exception {
        mockMvc.perform(post("/retete/new")
                        .param("nume", "Fara poza")
                        .param("descriere", "photoUrl lipsa")
                        .param("timpPreparare", "25")
                        .param("calorii", "300"))       // no photoUrl
                .andExpect(status().isOk())
                .andExpect(view().name("retete-create"))
                .andExpect(model().attributeHasFieldErrors("reteta", "photoUrl"));

        assertThat(retetaRepository.count()).isZero();
    }
}