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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "maminaDjeca"})
public class RoditeljMajka extends Korisnik{
	
	@JsonIgnore
	//@JsonManagedReference
	@OneToMany(mappedBy="mama", fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private List<Ucenik> maminaDjeca= new ArrayList<Ucenik>();
	
	public void dodajDijete(Ucenik ucenik) {
	    this.maminaDjeca.add(ucenik);
	    ucenik.setMama(this);
	}
	
	
	public List<Ucenik> getMaminaDjeca() {
		return maminaDjeca;
	}

	public void setMaminaDjeca(List<Ucenik> maminaDjeca) {
		this.maminaDjeca = maminaDjeca;
	}
	
	public RoditeljMajka() {
		
	}

}
