package com.proiectAWJ.webApp;

import com.proiectAWJ.webApp.models.Reteta;
import java.time.LocalDateTime;

public final class TestData {
    private TestData() {}

    public static Reteta validReteta(String nume) {
        Reteta r = new Reteta();
        r.setNume(nume);
        r.setDescriere("Descriere de test");
        r.setTimpPreparare("10");
        r.setCalorii("50");
        r.setPhotoUrl("https://example.com/poza.jpg");
        r.setCreataLa(LocalDateTime.now());
        return r;
    }
}