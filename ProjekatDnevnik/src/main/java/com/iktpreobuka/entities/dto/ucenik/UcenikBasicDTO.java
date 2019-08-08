package com.iktpreobuka.entities.dto.ucenik;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonPropertyOrder({ "Id","Prezime", "Ime","Odeljenje","Id oca","Id majke" })
@JsonRootName(value = "Ucenik")
public class UcenikBasicDTO {

	@JsonProperty("Id")
	Integer id;
	
	@JsonProperty ("Ime")
	private String ime;
	
	@JsonProperty ("Prezime")
	private String prezime;
	
	@JsonProperty ("Odeljenje")
	private String odeljenje;
	
	@JsonProperty ("Id oca")
	private Integer idOca;
	
	@JsonProperty ("Id majke")
	private Integer idMajke;
	
	@JsonProperty ("JMBG")
	private String jmbg;
	
	@JsonProperty ("Korisnicko ime")
	private String username;
	
	@JsonProperty ("Email")
	private String email;
	
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

	public UcenikBasicDTO() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getIdOca() {
		return idOca;
	}

	public void setIdOca(Integer idOca) {
		this.idOca = idOca;
	}

	public Integer getIdMajke() {
		return idMajke;
	}

	public void setIdMajke(Integer idMajke) {
		this.idMajke = idMajke;
	}
}