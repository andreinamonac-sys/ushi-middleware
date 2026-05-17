package com.andreina.ushi.service;

import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.service.impl.AnimalServiceImpl;

public class AnimalServiceTest {

    private AnimalService service;

    public AnimalServiceTest() {
        this.service = new AnimalServiceImpl();
    }

    public void testFindById(Long id) throws Exception {
        System.out.println("--- AnimalService.findById(" + id + ") ---");
        AnimalDTO animal = service.findById(id);
        if (animal != null) {
            System.out.println(animal.getId() + " - " + animal.getNumRegistro());
        } else {
            System.out.println("No animal found.");
        }
        System.out.println();
    }

    public void testFindByNumRegistro(String numRegistro) throws Exception {
        System.out.println("--- AnimalService.findByNumRegistro(" + numRegistro + ") ---");
        AnimalDTO animal = service.findByNumRegistro(numRegistro);
        if (animal != null) {
            System.out.println(animal.getId() + " - " + animal.getNumRegistro());
        } else {
            System.out.println("No animal found.");
        }
        System.out.println();
    }

    public void testFindByCriteria(String granjaNif) throws Exception {
        System.out.println("--- AnimalService.findByCriteria(granjaId=" + granjaNif + ") ---");
        AnimalCriteria criteria = new AnimalCriteria();
        criteria.getGranjaNif();

        int pageSize = 10;
        
        List<AnimalDTO> animales = new ArrayList<>();
        List<AnimalDTO> pagina;
        int from = 1;
        do {
            Results<AnimalDTO> results = service.findByCriteria(criteria, from, pageSize);
            pagina = results.getPageResults();
            animales.addAll(pagina);
            from += pageSize;
        } while (pagina.size() == pageSize);

        if (!animales.isEmpty()) {
            for (AnimalDTO animal : animales) {
                System.out.println(animal.getId() + " - " + animal.getNumRegistro());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        AnimalServiceTest test = new AnimalServiceTest();
        test.testFindById(1L);
//        test.testFindByNumRegistro("AN-001-2020");
        test.testFindByCriteria("GRANJA-001");
    }
}