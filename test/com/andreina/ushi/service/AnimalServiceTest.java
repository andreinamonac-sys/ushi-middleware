package com.andreina.ushi.service;

import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.service.impl.AnimalServiceImpl;

public class AnimalServiceTest {

    private AnimalService service;

    public AnimalServiceTest() {
        this.service = new AnimalServiceImpl();
    }

    public void testFindById(Long id) {
        System.out.println("--- AnimalService.findById(" + id + ") ---");
        AnimalDTO animal = service.findById(id);
        if (animal != null) {
            System.out.println(animal.getId() + " - " + animal.getNumRegistro());
        } else {
            System.out.println("No animal found.");
        }
        System.out.println();
    }

    public void testFindByNumRegistro(String numRegistro) {
        System.out.println("--- AnimalService.findByNumRegistro(" + numRegistro + ") ---");
        AnimalDTO animal = service.findByNumRegistro(numRegistro);
        if (animal != null) {
            System.out.println(animal.getId() + " - " + animal.getNumRegistro());
        } else {
            System.out.println("No animal found.");
        }
        System.out.println();
    }

    public void testFindByCriteria(Long granjaId) {
        System.out.println("--- AnimalService.findByCriteria(granjaId=" + granjaId + ") ---");
        AnimalCriteria criteria = new AnimalCriteria();
        criteria.setGranjaId(granjaId);
        
        int pageSize=10;
        List<AnimalDTO> animales = new ArrayList<AnimalDTO>();
        int from=1;
        
        do {
        	animales=
        			
        			
        			service.findByCriteria(criteria, from, pageSize);
        }while (animales.size()==pageSize);
        
        if (animales != null && !animales.isEmpty()) {
            for (AnimalDTO animal : animales) {
                System.out.println(animal.getId() + " - " + animal.getNumRegistro());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        AnimalServiceTest test = new AnimalServiceTest();
        test.testFindById(1L);
//        test.testFindByNumRegistro("AN-001-2020");
        	test.testFindByCriteria(1L);
    }
}
