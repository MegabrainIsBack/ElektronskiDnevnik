package com.iktpreobuka.entities.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NastavnikZaOdeljenjeDTO {
	
	@NotNull(message = "Morate unijeti JMBG.")
	@JsonProperty("JMBG")
	private String jmbg;
	
	@NotNull(message = "Morate unijeti predmet.")
	@JsonProperty("Predmet")
	private String predmet;
	
	@NotNull(message = "Morate unijeti razred.")
	@JsonProperty("Razred")
	private Integer godina;
	
	@NotNull(message = "Morate unijeti odeljenje.")
	@JsonProperty("Odeljenje")
	private String odeljenje;
	
	public NastavnikZaOdeljenjeDTO(){
		
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	

}
