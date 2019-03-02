package br.com.uol.testbackendjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.testbackendjava.model.PlayerGroup;
import br.com.uol.testbackendjava.repository.PlayerGroupRepository;

@Service
public class PlayerGroupService {

	@Autowired
	private PlayerGroupRepository repository;
	
	public Iterable<PlayerGroup> findAll() {
		return repository.findAll();
	}
		
}