package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Nastavnik extends Korisnik{
	
	//@JsonIgnore
	//@NotNull(message = "Morate unijeti predmet.")
	//@ElementCollection
	
	@JsonBackReference
	@OneToMany(cascade=CascadeType.ALL)
	/*@JoinTable(name = "PredmetiKojeNastavnikPredaje" ,joinColumns = {
	        @JoinColumn(name = "IdNastavnika", unique=false) },
	        inverseJoinColumns = { @JoinColumn(name = "IdPredmeta", referencedColumnName ="IdPredmeta", unique=false) })*/
	private List<Predmet> predmeti= new ArrayList<Predmet>();
	
	private String imePredmeta;
	
	public Nastavnik() {
		
	}

	public List<Predmet> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(List<Predmet> predmeti) {
		this.predmeti = predmeti;
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}


}
