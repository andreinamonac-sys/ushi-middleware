package com.andreina.ushi.service;

import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {

    private UsuarioService service;

    public UsuarioServiceTest() {
        this.service = new UsuarioServiceImpl();
    }

    public void testLogin(String email, String password) throws Exception {
        System.out.println("--- UsuarioService.login(" + email + ") ---");
        UsuarioDTO usuario = service.login(email, password);
        if (usuario != null) {
            System.out.println("Login ok: " + usuario.getId() + " - " + usuario.getEmail());
        } else {
            System.out.println("Login failed for: " + email);
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        UsuarioServiceTest test = new UsuarioServiceTest();
        test.testLogin("juan.garcia@email.com", "test123");
    }
}
