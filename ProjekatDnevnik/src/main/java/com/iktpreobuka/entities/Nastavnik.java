package com.iktpreobuka.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("Nastavnik")
public class Nastavnik extends Korisnik{
	
	@NotNull(message = "Morate unijeti predmet.")
	@Column(name="Predmet")
	protected String predmet;
	
	public Nastavnik() {
		
	}

	public String getPredmet() {
		return predmet;
	}

	public void setPredmet(String predmet) {
		this.predmet = predmet;
	}

}
