package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoditeljOtac extends Korisnik{
	
	
	@JsonIgnore
	//@JsonManagedReference
	@OneToMany(mappedBy="tata", fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private List<Ucenik> tatinaDjeca= new ArrayList<Ucenik>();
	
	public void dodajDijete(Ucenik ucenik) {
	    this.tatinaDjeca.add(ucenik);
	    ucenik.setTata(this);
	}
	
	public List<Ucenik> getTatinaDjeca() {
		return tatinaDjeca;
	}

	public void setTatinaDjeca(List<Ucenik> tatinaDjeca) {
		this.tatinaDjeca = tatinaDjeca;
	}
	
	public RoditeljOtac() {
		
	}

}
