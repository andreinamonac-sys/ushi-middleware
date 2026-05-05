package com.andreina.ushi.dao;

import java.util.List;

import com.andreina.ushi.model.Tag;

public class TagDAOTest {

	private static void findByIdTest() {
		TagDAO dao = new TagDAO();
		Tag tag = dao.findById(1L);
		if (tag != null) {
			System.out.println(tag.getId() + " - " + tag.getNumero());
		}
	}

	private static void findDisponibleTest() {
		TagDAO dao = new TagDAO();
		List<Tag> tags = dao.findDisponible();
		if (tags != null) {
			for (Tag tag : tags) {
				System.out.println(tag.getId() + " - " + tag.getNumero());
			}
		}
	}

	public static void main(String[] args) {
		findByIdTest();
//		findDisponibleTest();
	}
}
