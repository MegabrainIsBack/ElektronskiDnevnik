package com.iktpreobuka.entities.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "Ime", "Prezime","JMBG", "Korisnicko ime","Email","Osnovna uloga",
	"Predmet koji predaje","Aktivan","Odeljenja u kojima predaje"})
public class NastavnikAdminViewDTO {
	
	@JsonProperty("Ime")
	private String ime;
	
	@JsonProperty("Prezime")
	private String prezime;
	
	@JsonProperty("JMBG")
	private String jmbg;
	
	@JsonProperty("Korisnicko ime")
	private String username;
	
	@JsonProperty("Email")
	private String email;
	
	@JsonProperty("Osnovna uloga")
	private String osnovnaUloga;
	
	@JsonProperty("Predmet koji predaje")
	private String imePredmeta;
	
	@JsonProperty("Aktivan")
	private Boolean aktivan=true;
	
	@JsonProperty("Odeljenja u kojima predaje")
	List<String> odeljenja;

	public NastavnikAdminViewDTO() {
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOsnovnaUloga() {
		return osnovnaUloga;
	}

	public void setOsnovnaUloga(String osnovnaUloga) {
		this.osnovnaUloga = osnovnaUloga;
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}

	public List<String> getOdeljenja() {
		return odeljenja;
	}

	public void setOdeljenja(List<String> odeljenja) {
		this.odeljenja = odeljenja;
	}

}
