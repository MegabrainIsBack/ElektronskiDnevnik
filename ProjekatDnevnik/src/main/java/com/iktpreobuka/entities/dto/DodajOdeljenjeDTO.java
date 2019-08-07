package com.iktpreobuka.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DodajOdeljenjeDTO {
	
	@Min(value=1, message="Godina mora biti izmedju 1 i 8.")
	@Max(value=8, message="Godina mora biti izmedju 1 i 8.")
	@NotNull(message = "Morate unijeti razred.")
	@Column(name="Razred")
	private Integer godina;
	
	@Size(min=1, max=1, message = "Ime odeljenja sastoji se od jednog slova.")
	@NotNull(message = "Morate unijeti odeljenje.")
	@Column(name="Odeljenje")
	private String ime;

	public DodajOdeljenjeDTO() {
		super();
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
	
	

}
