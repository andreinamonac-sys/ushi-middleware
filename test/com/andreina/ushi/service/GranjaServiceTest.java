package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.service.impl.GranjaServiceImpl;

public class GranjaServiceTest {

    private GranjaService service;

    public GranjaServiceTest() {
        this.service = new GranjaServiceImpl();
    }

    public void testFindById(Long id) {
        System.out.println("--- GranjaService.findById(" + id + ") ---");
        GranjaDTO granja = service.findById(id);
        if (granja != null) {
            System.out.println(granja.getId() + " - " + granja.getNif());
        } else {
            System.out.println("No granja found.");
        }
        System.out.println();
    }

    public void testFindByNif(String nif) {
        System.out.println("--- GranjaService.findByNif(" + nif + ") ---");
        GranjaDTO granja = service.findByNif(nif);
        if (granja != null) {
            System.out.println(granja.getId() + " - " + granja.getNif());
        } else {
            System.out.println("No granja found.");
        }
        System.out.println();
    }

    public void testFindByEncargadoId(Long usuarioId) {
        System.out.println("--- GranjaService.findByEncargadoId(" + usuarioId + ") ---");
        List<GranjaDTO> granjas = service.findByEncargadoId(usuarioId);
        if (granjas != null && !granjas.isEmpty()) {
            for (GranjaDTO granja : granjas) {
                System.out.println(granja.getId() + " - " + granja.getNif());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GranjaServiceTest test = new GranjaServiceTest();
        test.testFindById(1L);
//        test.testFindByNif("B12345678");
//        test.testFindByEncargadoId(4L);
    }
}
