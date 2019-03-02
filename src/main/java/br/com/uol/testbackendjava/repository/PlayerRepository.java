package br.com.uol.testbackendjava.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.uol.testbackendjava.model.Player;
import br.com.uol.testbackendjava.model.PlayerGroup;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
	
	List<Player> findByPlayerGroupAndCodenameIn(PlayerGroup playerGroup, List<String> codenameList);

}
