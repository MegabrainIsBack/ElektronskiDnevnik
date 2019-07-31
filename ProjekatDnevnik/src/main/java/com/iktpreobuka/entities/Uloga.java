package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.JoinTables.KU;
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
	
	@OneToMany(mappedBy = "uloga",
		    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<KU> ku= new ArrayList<KU>();
	
	public List<KU> getKu() {
		return ku;
	}

	public void setKu(List<KU> ku) {
		this.ku = ku;
	}

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
