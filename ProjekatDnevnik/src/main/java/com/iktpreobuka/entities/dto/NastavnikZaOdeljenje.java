package com.iktpreobuka.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NastavnikZaOdeljenje {
	
	@JsonProperty("Ime")
	private String ime;
	
	@JsonProperty("Prezime")
	private String prezime;
	
	@JsonProperty("Predmet")
	private String predmet;
	
	@JsonProperty("Razred")
	private Integer godina;
	
	@JsonProperty("Odeljenje")
	private String odeljenje;
	
	NastavnikZaOdeljenje(){
		
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getPredmet() {
		return predmet;
	}

	public void setPredmet(String predmet) {
		this.predmet = predmet;
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public String getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(String odeljenje) {
		this.odeljenje = odeljenje;
	}

}
