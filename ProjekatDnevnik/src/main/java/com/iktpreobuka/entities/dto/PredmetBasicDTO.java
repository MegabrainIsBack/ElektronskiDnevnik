package com.iktpreobuka.entities.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PredmetBasicDTO {
	
	@JsonProperty("Id predmeta")
	private Integer idPredmeta;
	
	@JsonProperty("Ime predmeta")
	private String imePredmeta;
	
	@JsonProperty("Razredi u kojima se predmet slusa:")
	private List<Integer> godina;
	
	@JsonProperty("Nastavnici koji predaju predmet:")
	private List<String> nastavnici;

	public PredmetBasicDTO() {
		super();
	}

	public Integer getIdPredmeta() {
		return idPredmeta;
	}

	public void setIdPredmeta(Integer idPredmeta) {
		this.idPredmeta = idPredmeta;
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}

	public List<Integer> getGodina() {
		return godina;
	}

	public void setGodina(List<Integer> godina) {
		this.godina = godina;
	}

	public List<String> getNastavnici() {
		return nastavnici;
	}

	public void setNastavnici(List<String> nastavnici) {
		this.nastavnici = nastavnici;
	}

}
