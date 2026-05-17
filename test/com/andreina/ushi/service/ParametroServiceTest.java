package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.service.impl.ParametroServiceImpl;

public class ParametroServiceTest {

    private ParametroService service;

    public ParametroServiceTest() {
        this.service = new ParametroServiceImpl();
    }

    public void testFindByCriteria(Long animalId) throws Exception {
        System.out.println("--- ParametroService.findByCriteria(animalId=" + animalId + ") ---");
        ParametroCriteria criteria = new ParametroCriteria();
        criteria.setAnimalId(animalId);
        List<ParametroDTO> parametros = service.findByCriteria(criteria, 1, 10);
        if (parametros != null && !parametros.isEmpty()) {
            for (ParametroDTO parametro : parametros) {
                System.out.println(parametro.getId() + " - " + parametro.getValorParametro());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public static void main(String[] args)throws Exception {
        ParametroServiceTest test = new ParametroServiceTest();
        test.testFindByCriteria(1L);
    }
}
