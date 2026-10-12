package com.proiectAWJ.webApp;

import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.repository.RetetaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class RetetaRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RetetaRepository retetaRepository;

//    private Reteta reteta(String nume) {
//        Reteta r = new Reteta();
//        r.setNume(nume);
//        r.setDescriere("Reteta de test pentru integrare");
//        r.setTimpPreparare("30");
//        r.setCalorii("250");
//        r.setPhotoUrl("https://example.com/poza.jpg");
//        r.setCreataLa(LocalDateTime.now());
//        return r;
//    }

    @Test
    void savedRecipe_canBeReadBackFromPostgres() {

        // given + when
        Reteta saved = retetaRepository.save(TestData.validReteta("Lasagna"));
        retetaRepository.flush();   // force Hibernate to send the INSERT now

        // then
        assertThat(saved.getId()).isNotNull();   // PostgreSQL generated an id
        assertThat(retetaRepository.findById(saved.getId()))
                .isPresent()
                .get()
                .extracting(Reteta::getNume)
                .isEqualTo("Lasagna");
    }

    // Add a test for ONE of your custom JPQL queries:
    //
     @Test
     void customQuery_returnsMatchingRecipes() {
         // given: save 2 recipes, one that should match and one that shouldn't
         retetaRepository.save(TestData.validReteta("Supa de pui"));
         retetaRepository.save(TestData.validReteta("Clatite cu gem"));
         retetaRepository.flush();

         // when:  call your @Query method, e.g. retetaRepository.findByCaloriiLessThan(300)
         var result = retetaRepository.searchRetete("supa");

         // then:  assert only the right one comes back
         assertThat(result).extracting("nume").containsExactly("Supa de pui");

     }
}