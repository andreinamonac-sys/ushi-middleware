package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.model.Semilla;
import com.andreina.ushi.service.impl.SemillaServiceImpl;

public class SemillaServiceTest {

    private SemillaService service;

    public SemillaServiceTest() {
        this.service = new SemillaServiceImpl();
    }

    public void testFindTopByItp(Integer limite) throws Exception {
        System.out.println("--- SemillaService.findTopByItp(" + limite + ") ---");
        List<Semilla> semillas = service.findTopByItp(limite);
        if (semillas != null && !semillas.isEmpty()) {
            for (Semilla semilla : semillas) {
                System.out.println(semilla.getId() + " - itp=" + semilla.getItp());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public void testFindByMeritoNetoGreaterThan(Integer minimo)throws Exception {
        System.out.println("--- SemillaService.findByMeritoNetoGreaterThan(" + minimo + ") ---");
        List<Semilla> semillas = service.findByMeritoNetoGreaterThan(minimo);
        if (semillas != null && !semillas.isEmpty()) {
            for (Semilla semilla : semillas) {
                System.out.println(semilla.getId() + " - merito=" + semilla.getMeritoNeto());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        SemillaServiceTest test = new SemillaServiceTest();
        test.testFindTopByItp(5);
//        test.testFindByMeritoNetoGreaterThan(50);
    }
}
