package br.com.uol.testbackendjava.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tb_player_group")
public class PlayerGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5574454007818108708L;

	@Id
	private long id;

	private String name;

	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}