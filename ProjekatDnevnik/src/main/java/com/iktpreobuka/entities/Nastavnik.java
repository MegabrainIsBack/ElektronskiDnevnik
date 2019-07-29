package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.JoinTables.ONP;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Nastavnik extends Korisnik{
	

	private String imePredmeta;
	
	@OneToMany(mappedBy = "nastavnik",
    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<ONP> onp= new ArrayList<ONP>();

	public List<ONP> getOnp() {
		return onp;
	}

	public void setOnp(List<ONP> onp) {
		this.onp = onp;
	}

	public Nastavnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}

	


}
