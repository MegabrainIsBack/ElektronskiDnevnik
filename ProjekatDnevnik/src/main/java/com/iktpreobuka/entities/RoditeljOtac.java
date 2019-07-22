package com.iktpreobuka.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("Otac")
public class RoditeljOtac extends Korisnik{
	
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
