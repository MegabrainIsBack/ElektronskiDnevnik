package com.iktpreobuka.entities.dto.ocjene;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OcjeneIzSvihPredmetaDTO {
	
	@JsonProperty("Predmet")
	private String imePredmeta;
	
	@JsonProperty("Ocjene")
	private ArrayList<Integer> ocjene;

	public OcjeneIzSvihPredmetaDTO() {
		super();
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}

	public ArrayList<Integer> getOcjene() {
		return ocjene;
	}

	public void setOcjene(ArrayList<Integer> ocjene) {
		this.ocjene = ocjene;
	}

}
