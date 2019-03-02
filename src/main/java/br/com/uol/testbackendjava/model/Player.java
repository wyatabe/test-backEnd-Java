package br.com.uol.testbackendjava.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "tb_player")
public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2034684911064206940L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(message = "O Nome é obrigatório.")
	@Size(min = 2, message = "O nome deve possuir no mínimo {min} caracteres.")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "O E-mail é obrigatório.")
	@Email(message = "Informe um e-mail válido.")
	@Column(nullable = false)
	private String email;
	
	@Size(min = 14, message = "O telefone deve possuir no mínimo {min} dígitos.")
	private String telephone;
	
	private String codename;
	
	@NotNull(message = "O Grupo é Obrigatório.")
	@ManyToOne
    @JoinColumn(name = "player_group_id", nullable = false)
	private PlayerGroup playerGroup;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public PlayerGroup getPlayerGroup() {
		return playerGroup;
	}

	public void setPlayerGroup(PlayerGroup playerGroup) {
		this.playerGroup = playerGroup;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", email=" + email + ", telephone=" + telephone + ", codename="
				+ codename + ", playerGroup=" + playerGroup + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codename == null) ? 0 : codename.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (codename == null) {
			if (other.codename != null)
				return false;
		} else if (!codename.equals(other.codename))
			return false;
		return true;
	}

}