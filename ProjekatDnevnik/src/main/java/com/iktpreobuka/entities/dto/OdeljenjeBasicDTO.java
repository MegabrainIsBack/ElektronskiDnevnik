package com.iktpreobuka.entities.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OdeljenjeBasicDTO {
	
	@JsonProperty ("Odeljenje")
	private String ime;
	
	@JsonProperty("Predmet : Nastavnik")
	Map <String,String> predmetNastavnik;

	public OdeljenjeBasicDTO() {
		super();
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public Map<String, String> getPredmetNastavnik() {
		return predmetNastavnik;
	}

	public void setPredmetNastavnik(Map<String, String> predmetNastavnik) {
		this.predmetNastavnik = predmetNastavnik;
	}
}
