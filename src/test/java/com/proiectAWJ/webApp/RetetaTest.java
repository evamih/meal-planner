package com.proiectAWJ.webApp;

import com.proiectAWJ.webApp.controllers.RetetaController;
import com.proiectAWJ.webApp.models.Reteta;
import com.proiectAWJ.webApp.services.RetetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RetetaTest {

    @Mock
    private RetetaService retetaService;
    @Mock
    private BindingResult bindingResult;
    @Mock
    Model model;
    @InjectMocks
    private RetetaController retetaController;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReteta(){
        Reteta reteta = new Reteta();
        reteta.setNume("Reteta de test");
        reteta.setCalorii("100");
        reteta.setDescriere("o reteta pentru testul j-unit");
        reteta.setPhotoUrl("https://global-sensors.com/wp-content/uploads/2021/07/test.jpg");
        reteta.setTimpPreparare("20");
        //reteta.set
        when(bindingResult.hasErrors()).thenReturn(false);

        String arataNume = retetaController.saveReteta(reteta, bindingResult, model);

        assertEquals("redirect:/retete", arataNume);
        verify(retetaService, times(1)).saveReteta(any());
    }

    @Test
    void testEditReteta(){

    }

}
