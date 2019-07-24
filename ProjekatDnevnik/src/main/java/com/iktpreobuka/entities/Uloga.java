package com.iktpreobuka.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.enums.Role;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Uloga")
public class Uloga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IdUloge")
	private Integer id;
	
	@Column(name="Uloga")
	private Role ime;
	
	public Uloga(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getIme() {
		return ime;
	}

	public void setIme(Role ime) {
		this.ime = ime;
	}

}
