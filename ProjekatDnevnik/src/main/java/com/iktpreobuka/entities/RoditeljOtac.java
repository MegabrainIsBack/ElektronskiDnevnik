package com.iktpreobuka.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoditeljOtac extends Korisnik{
	
	
	@JsonIgnore
	@JsonBackReference
	@OneToMany(mappedBy="id", fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private Set<Ucenik> tatinaDjeca= new HashSet<Ucenik>();
	
	public Set<Ucenik> getTatinaDjeca() {
		return tatinaDjeca;
	}

	public void setTatinaDjeca(Set<Ucenik> tatinaDjeca) {
		this.tatinaDjeca = tatinaDjeca;
	}
	
	private Integer brojDjece=0;
	
	public RoditeljOtac() {
		
	}

	public Integer getBrojDjece() {
		return brojDjece;
	}

	public void setBrojDjece(Integer brojDjece) {
		this.brojDjece = brojDjece;
	}

}
