package com.iktpreobuka.entities.dto.ucenik;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonPropertyOrder({ "Id","Prezime", "Ime","Odeljenje","Id oca","Id majke" })
@JsonRootName(value = "Ucenik")
public class UcenikZaRoditeljaBasicDTO {
	
	@JsonProperty ("Ime")
	private String ime;
	
	@JsonProperty ("Prezime")
	private String prezime;
	
	@JsonProperty ("Odeljenje")
	private String odeljenje;
	
	@JsonProperty("Predmet : Nastavnik")
	Map <String,String> predmetNastavnik;

	public UcenikZaRoditeljaBasicDTO() {
		super();
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

	public String getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(String odeljenje) {
		this.odeljenje = odeljenje;
	}

	public Map<String, String> getPredmetNastavnik() {
		return predmetNastavnik;
	}

	public void setPredmetNastavnik(Map<String, String> predmetNastavnik) {
		this.predmetNastavnik = predmetNastavnik;
	}

}
