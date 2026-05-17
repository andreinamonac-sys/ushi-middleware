package com.andreina.ushi.dao;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.Tag;
import com.andreina.ushi.utils.JDBCUtils;

public class TagDAOTest {

	private static void findByIdTest(Connection c)throws Exception {
		TagDAO dao = new TagDAO();
		Tag tag = dao.findById(c,1L);
		if (tag != null) {
			System.out.println(tag.getId() + " - " + tag.getNumero());
		}
	}

	private static void findDisponibleTest(Connection c)throws Exception {
		TagDAO dao = new TagDAO();
		List<Tag> tags = dao.findDisponible(c);
		if (tags != null) {
			for (Tag tag : tags) {
				System.out.println(tag.getId() + " - " + tag.getNumero());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Connection c = JDBCUtils.getConnection();
		try {
		findByIdTest(c);
		//findDisponibleTest();
		} finally {
			JDBCUtils.close(c, true);
		}
	}
}
