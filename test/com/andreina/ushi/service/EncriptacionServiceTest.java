package com.andreina.ushi.service;

import com.andreina.ushi.service.impl.EncriptacionServiceBCryptImpl;

public class EncriptacionServiceTest {

    private EncriptacionService service;

    public EncriptacionServiceTest() {
        this.service = new EncriptacionServiceBCryptImpl();
    }

    public void testEncryptAndVerify(String plain) {
        System.out.println("--- EncriptacionService.encrypt/verify ---");
        String hash = service.encrypt(plain);
        System.out.println("plain=" + plain);
        System.out.println("hash=" + hash);
        System.out.println("verify ok=" + service.verify(plain, hash));
        System.out.println("verify bad=" + service.verify(plain + "x", hash));
        System.out.println();
    }

    public static void main(String[] args) {
        EncriptacionServiceTest test = new EncriptacionServiceTest();
        test.testEncryptAndVerify("test123");
    }
}
