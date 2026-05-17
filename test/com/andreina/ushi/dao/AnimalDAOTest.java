package com.andreina.ushi.dao;

import java.sql.Connection;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.utils.JDBCUtils;

public class AnimalDAOTest {

    private static void findByIdTest(Connection c) throws Exception {
        AnimalDAO dao = new AnimalDAO();
        AnimalDTO animal = dao.findById(c, 1L);
        if (animal != null) {
            System.out.println(animal.getId() + " - " + animal.getNumRegistro());
        } else {
            System.out.println("No animal found.");
        }
    }

    private static void findByNumRegistroTest(Connection c) throws Exception {
        AnimalDAO dao = new AnimalDAO();
        AnimalDTO animal = dao.findByNumRegistro(c, "AN-001-2020");
        if (animal != null) {
            System.out.println(animal.getId() + " - " + animal.getNumRegistro());
        } else {
            System.out.println("No animal found.");
        }
    }

    private static void findByCriteriaTest(Connection c) throws Exception {
        AnimalDAO dao = new AnimalDAO();
        AnimalCriteria criteria = new AnimalCriteria();
        Results<AnimalDTO> results = dao.findByCriteria(c, criteria, 1, Integer.MAX_VALUE);
        if (results != null && !results.getPageResults().isEmpty()) {
            for (AnimalDTO animal : results.getPageResults()) {
                System.out.println(animal.getId() + " - " + animal.getNumRegistro());
            }
        } else {
            System.out.println("No results.");
        }
    }

    public static void main(String[] args) throws Exception {
        Connection c = JDBCUtils.getConnection();
        try {
            findByIdTest(c);
            findByNumRegistroTest(c);
            findByCriteriaTest(c);
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}