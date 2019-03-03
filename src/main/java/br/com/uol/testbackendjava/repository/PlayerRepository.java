package br.com.uol.testbackendjava.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.uol.testbackendjava.model.Player;
import br.com.uol.testbackendjava.model.PlayerGroup;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
	
	List<Player> findByPlayerGroup(PlayerGroup playerGroup);
	
	@Query(value="SELECT * FROM tb_player p WHERE LOWER(p.name) LIKE :word OR LOWER(p.email) LIKE :word OR LOWER(p.codename) LIKE :word", nativeQuery = true)
	Page<Player> findByWord(@Param("word") String word, Pageable pageable);
	
	public boolean existsByName(String name);
	
	public boolean existsByEmail(String email);
	
	public boolean existsByTelephone(String telephone);
}