package com.andreina.ushi.dao;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.utils.JDBCUtils;

public class EventoDAOTest {

    private static void findByIdTest(Connection c) throws Exception {
        EventoDAO dao = new EventoDAO();
        EventoDTO evento = dao.findById(c, 1L);
        if (evento != null) {
            System.out.println(evento.getId() + " - " + evento.getDescripcion());
        } else {
            System.out.println("No evento found.");
        }
    }

    private static void findByTipoTest(Connection c) throws Exception {
        EventoDAO dao = new EventoDAO();
        List<EventoDTO> eventos = dao.findByTipoEventoId(c, 1L);
        if (eventos != null && !eventos.isEmpty()) {
            for (EventoDTO evento : eventos) {
                System.out.println(evento.getId() + " - " + evento.getDescripcion());
            }
        } else {
            System.out.println("No results.");
        }
    }

    private static void findByAnimalIdTest(Connection c, Long animalId) throws Exception {
        EventoDAO dao = new EventoDAO();
        List<EventoDTO> eventos = dao.findByAnimalId(c, animalId);
        if (eventos != null && !eventos.isEmpty()) {
            for (EventoDTO evento : eventos) {
                System.out.println(evento.getId() + " - " + evento.getDescripcion());
            }
        } else {
            System.out.println("No results.");
        }
    }

    public static void main(String[] args) throws Exception {
        Connection c = JDBCUtils.getConnection();
        try {
            //findByIdTest(c);
            //findByTipoTest(c);
            findByAnimalIdTest(c, 1L);
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}