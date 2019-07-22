package com.iktpreobuka.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("Ucenik")
public class Ucenik extends Korisnik{
	
	
	@Column(name="ImeOca")
	private String imeOca;
	
	@Column(name="ImeMajke")
	private String imeMajke;
	
	@NotNull(message = "Morate unijeti odeljenje.")
	@Column(name="Odeljenje")
	private String odeljenje;
	
	public Ucenik() {
		
	}

	public String getImeOca() {
		return imeOca;
	}

	public void setImeOca(String imeOca) {
		this.imeOca = imeOca;
	}

	public String getImeMajke() {
		return imeMajke;
	}

	public void setImeMajke(String imeMajke) {
		this.imeMajke = imeMajke;
	}

	public String getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(String odeljenje) {
		this.odeljenje = odeljenje;
	}

}
