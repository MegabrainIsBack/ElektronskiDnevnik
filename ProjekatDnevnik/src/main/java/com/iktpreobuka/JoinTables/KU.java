package com.iktpreobuka.JoinTables;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Uloga;

@Entity
@Table(name = "KU")
public class KU {
	
	@Id
	@GeneratedValue
	@Column(name = "IdKU") 
	private Integer idku;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "IdKorisnika")
    private Korisnik korisnik;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "IdUloge")
    private Uloga uloga;
	
	public KU() {
		
	}

	public Integer getIdku() {
		return idku;
	}

	public void setIdku(Integer idku) {
		this.idku = idku;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

}
