package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.service.impl.EventoServiceImpl;

public class EventoServiceTest {

    private EventoService service;

    public EventoServiceTest() {
        this.service = new EventoServiceImpl();
    }

    public void testFindById(Long id)throws Exception {
        System.out.println("--- EventoService.findById(" + id + ") ---");
        EventoDTO evento = service.findById(id);
        if (evento != null) {
            System.out.println(evento.getId() + " - " + evento.getDescripcion());
        } else {
            System.out.println("No evento found.");
        }
        System.out.println();
    }

    public void testFindByTipo(Long tipoEventoId)throws Exception {
        System.out.println("--- EventoService.findByTipo(" + tipoEventoId + ") ---");
        List<EventoDTO> eventos = service.findByTipoEventoId(tipoEventoId);
        if (eventos != null && !eventos.isEmpty()) {
            for (EventoDTO evento : eventos) {
                System.out.println(evento.getId() + " - " + evento.getDescripcion());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public void testFindByAnimalId(Long animalId) throws Exception {
        System.out.println("--- EventoService.findByAnimalId(" + animalId + ") ---");
        List<EventoDTO> eventos = service.findByAnimalId(animalId);
        if (eventos != null && !eventos.isEmpty()) {
            for (EventoDTO evento : eventos) {
                System.out.println(evento.getId() + " - " + evento.getDescripcion());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        EventoServiceTest test = new EventoServiceTest();
        test.testFindById(1L);
//        test.testFindByTipo(1L);
//        test.testFindByAnimalId(1L);
    }
}
