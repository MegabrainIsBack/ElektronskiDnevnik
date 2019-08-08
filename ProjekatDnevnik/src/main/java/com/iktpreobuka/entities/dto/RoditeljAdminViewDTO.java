package com.iktpreobuka.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoditeljAdminViewDTO {
	
	@JsonProperty("Id")
	private Integer id;
	
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
	
	@JsonProperty("Aktivan")
	private Boolean aktivan=true;

	public RoditeljAdminViewDTO() {
		super();
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

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}
	
	

}
