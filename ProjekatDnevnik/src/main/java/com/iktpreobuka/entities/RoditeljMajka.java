package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "maminaDjeca"})
//@DiscriminatorValue("Majka")
public class RoditeljMajka extends Korisnik{
	
	@JsonIgnore
	@JsonManagedReference
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
