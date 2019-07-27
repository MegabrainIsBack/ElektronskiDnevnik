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
	
	private Boolean aktivan=true;
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

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}

	


}
