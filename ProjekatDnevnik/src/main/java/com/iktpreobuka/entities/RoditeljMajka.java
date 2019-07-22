package com.iktpreobuka.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("Majka")
public class RoditeljMajka extends Korisnik{
	
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
