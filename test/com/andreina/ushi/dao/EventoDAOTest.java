package com.andreina.ushi.dao;

import java.util.List;

import com.andreina.ushi.model.EventoDTO;

public class EventoDAOTest {

	private static void findByIdTest() {
		EventoDAO dao = new EventoDAO();
		EventoDTO evento = dao.findById(1L);
		if (evento != null) {
			System.out.println(evento.getId() + " - " + evento.getDescripcion());
		}
	}

	private static void findByTipoTest() {
		EventoDAO dao = new EventoDAO();
		List<EventoDTO> eventos = dao.findByTipo(1L);
		if (eventos != null) {
			for (EventoDTO evento : eventos) {
				System.out.println(evento.getId() + " - " + evento.getDescripcion());
			}
		}
	}

	private static void findByAnimalIdTest() {
		EventoDAO dao = new EventoDAO();
		List<EventoDTO> eventos = dao.findByAnimalId(1L);
		if (eventos != null) {
			for (EventoDTO evento : eventos) {
				System.out.println(evento.getId() + " - " + evento.getDescripcion());
			}
		}
	}

	public static void main(String[] args) {
		findByIdTest();
//		findByTipoTest();
//		findByAnimalIdTest();
	}
}
