package com.iktpreobuka.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ucenik extends Korisnik{
	
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="IdOca")
	private RoditeljOtac tata;
	
	
	public RoditeljOtac getTata() {
		return tata;
	}

	public void setTata(RoditeljOtac tata) {
		this.tata = tata;
	}
	
	

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
