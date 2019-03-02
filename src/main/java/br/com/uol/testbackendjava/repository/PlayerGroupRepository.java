package br.com.uol.testbackendjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.uol.testbackendjava.model.PlayerGroup;

@Repository
public interface PlayerGroupRepository extends CrudRepository<PlayerGroup, Long> {

}
