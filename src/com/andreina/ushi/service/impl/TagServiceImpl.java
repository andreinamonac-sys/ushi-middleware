package com.andreina.ushi.service.impl;

import java.util.List;

import com.andreina.ushi.dao.TagDAO;
import com.andreina.ushi.model.Tag;
import com.andreina.ushi.service.TagService;

public class TagServiceImpl implements TagService {

	private TagDAO tagDAO = null;

	public TagServiceImpl() {
		tagDAO = new TagDAO();
	}

	@Override
	public Tag create(Tag tag) {
		return tagDAO.create(tag);
	}

	@Override
	public Tag update(Tag tag) {
		return tagDAO.update(tag);
	}

	@Override
	public boolean delete(Long id) {
		return tagDAO.delete(id);
	}

	@Override
	public Tag findById(Long id) {
		return tagDAO.findById(id);
	}

	@Override
	public Tag findByNumero(String numero) {
		return tagDAO.findByNumero(numero);
	}

	@Override
	public List<Tag> findByAnimalId(Long animalId) {
		return tagDAO.findByAnimalId(animalId);
	}

	@Override
	public List<Tag> findConIncidencias() {
		return tagDAO.findConIncidencias();
	}

	@Override
	public List<Tag> findDisponible() {
		return tagDAO.findDisponible();
	}
}
