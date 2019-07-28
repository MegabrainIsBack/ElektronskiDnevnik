package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.compositeKeys.JoinTables.NPVezna;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Nastavnik extends Korisnik{

	@OneToMany(mappedBy = "nastavnik",
            cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<NPVezna> npVeza= new ArrayList<NPVezna>();
	
	private String imePredmeta;
	
	

	public Nastavnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<NPVezna> getNpVeza() {
		return npVeza;
	}

	public void setNpVeza(List<NPVezna> npVeza) {
		this.npVeza = npVeza;
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}

	


}
