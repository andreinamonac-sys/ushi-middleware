package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.model.Tag;
import com.andreina.ushi.service.impl.TagServiceImpl;

public class TagServiceTest {

    private TagService service;

    public TagServiceTest() {
        this.service = new TagServiceImpl();
    }

    public void testFindById(Long id) throws Exception {
        System.out.println("--- TagService.findById(" + id + ") ---");
        Tag tag = service.findById(id);
        if (tag != null) {
            System.out.println(tag.getId() + " - " + tag.getNumero());
        } else {
            System.out.println("No tag found.");
        }
        System.out.println();
    }

    public void testFindDisponible() throws Exception {
        System.out.println("--- TagService.findDisponible ---");
        List<Tag> tags = service.findDisponible();
        if (tags != null && !tags.isEmpty()) {
            for (Tag tag : tags) {
                System.out.println(tag.getId() + " - " + tag.getNumero());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public void testFindConIncidencias()throws Exception {
        System.out.println("--- TagService.findConIncidencias ---");
        List<Tag> tags = service.findConIncidencias();
        if (tags != null && !tags.isEmpty()) {
            for (Tag tag : tags) {
                System.out.println(tag.getId() + " - " + tag.getNumero());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public void testFindByAnimalId(Long animalId)throws Exception {
        System.out.println("--- TagService.findByAnimalId(" + animalId + ") ---");
        List<Tag> tags = service.findByAnimalId(animalId);
        if (tags != null && !tags.isEmpty()) {
            for (Tag tag : tags) {
                System.out.println(tag.getId() + " - " + tag.getNumero());
            }
        } else {
            System.out.println("No results.");
        }
        System.out.println();
    }

    public static void main(String[] args)throws Exception {
        TagServiceTest test = new TagServiceTest();
        //test.testFindById(1L);
       test.testFindDisponible();
//        test.testFindConIncidencias();
//        test.testFindByAnimalId(1L);
    }
}
