package com.proiectAWJ.webApp;

import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.repository.RetetaIngredienteRepository;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class RetetaApiIntegrationTest extends AbstractIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired RetetaRepository retetaRepository;
    @Autowired RetetaIngredienteRepository retetaIngredienteRepository;

    @BeforeEach
    void cleanDatabase() {
        retetaIngredienteRepository.deleteAll();   // children first (foreign key)
        retetaRepository.deleteAll();
    }

    @Test
    void postRecipe_storesItInDatabase() throws Exception {
        String json = """
            {
              "nume": "Reteta API",
              "descriere": "Creata prin test de integrare",
              "timpPreparare": "20",
              "calorii": "100",
              "photoUrl": "https://example.com/poza.jpg"
            }
            """;

        mockMvc.perform(post("/api/retete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())          // PostgreSQL generated an id
                .andExpect(jsonPath("$.nume").value("Reteta API"));

        assertThat(retetaRepository.findAll())
                .extracting(Reteta::getNume)
                .containsExactly("Reteta API");
    }

    @Test
    void getRecipes_returnsWhatIsInDatabase() throws Exception {
        // given
        retetaRepository.save(TestData.validReteta("Din baza de date"));

        // when + then
        mockMvc.perform(get("/api/retete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nume").value("Din baza de date test fail"));
    }

    @Test
    void deleteRecipe_removesItFromDatabase() throws Exception {
        Long id = retetaRepository.save(TestData.validReteta("De sters")).getId();

        mockMvc.perform(delete("/api/retete/" + id))
                .andExpect(status().isOk());

        assertThat(retetaRepository.findById(id)).isEmpty();
    }
}