package com.iktpreobuka.entities;

import java.util.HashSet;
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

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "maminaDjeca"})
//@DiscriminatorValue("Majka")
public class RoditeljMajka extends Korisnik{
	
	@JsonIgnore
	@JsonBackReference
	@OneToMany(mappedBy="pin", fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private Set<Ucenik> maminaDjeca= new HashSet<Ucenik>();
	
	
	public Set<Ucenik> getMaminaDjeca() {
		return maminaDjeca;
	}

	public void setMaminaDjeca(Set<Ucenik> maminaDjeca) {
		this.maminaDjeca = maminaDjeca;
	}

	private Integer brojDjece=0;
	
	public RoditeljMajka() {
		
	}

	public Integer getBrojDjece() {
		return brojDjece;
	}

	public void setBrojDjece(Integer brojDjece) {
		this.brojDjece = brojDjece;
	}

}
