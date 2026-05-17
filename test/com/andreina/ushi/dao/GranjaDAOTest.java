package com.andreina.ushi.dao;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.utils.JDBCUtils;

public class GranjaDAOTest {

    private static void findByIdTest(Connection c) throws Exception {
        GranjaDAO dao = new GranjaDAO();
        GranjaDTO granja = dao.findById(c, 1L);
        if (granja != null) {
            System.out.println(granja.getId() + " - " + granja.getNif());
        } else {
            System.out.println("No granja found.");
        }
    }

    private static void findByNifTest(Connection c) throws Exception {
        GranjaDAO dao = new GranjaDAO();
        GranjaDTO granja = dao.findByNif(c, "B12345678");
        if (granja != null) {
            System.out.println(granja.getId() + " - " + granja.getNif());
        } else {
            System.out.println("No granja found.");
        }
    }

    private static void findByEncargadoIdTest(Connection c) throws Exception {
        GranjaDAO dao = new GranjaDAO();
        List<GranjaDTO> granjas = dao.findByEncargadoId(c, 4L);
        if (granjas != null && !granjas.isEmpty()) {
            for (GranjaDTO granja : granjas) {
                System.out.println(granja.getId() + " - " + granja.getNif());
            }
        } else {
            System.out.println("No results.");
        }
    }

    public static void main(String[] args) throws Exception {
        Connection c = JDBCUtils.getConnection();
        try {
            findByIdTest(c);
//            findByNifTest(c);
//            findByEncargadoIdTest(c);
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}