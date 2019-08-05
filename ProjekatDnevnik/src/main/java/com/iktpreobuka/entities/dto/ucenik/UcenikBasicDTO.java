package com.iktpreobuka.entities.dto.ucenik;

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