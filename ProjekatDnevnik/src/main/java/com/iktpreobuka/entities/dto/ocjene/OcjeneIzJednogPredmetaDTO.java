package com.iktpreobuka.entities.dto.ocjene;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonPropertyOrder({"Ime i prezime","Odeljenje","Predmet","Ocjene"})
@JsonRootName(value = "Ocjene ucenika")
public class OcjeneIzJednogPredmetaDTO {
	
	@JsonProperty ("Ime i prezime")
	private String imeIPrezime;
	
	@JsonProperty ("Odeljenje")
	private String odeljenje;
	
	@JsonProperty ("Predmet")
	private String imePredmeta;
	
	@JsonProperty ("Ocjene")
	private ArrayList<Integer> ocjene;

	public OcjeneIzJednogPredmetaDTO() {
		super();
	}

	public String getImeIPrezime() {
		return imeIPrezime;
	}

	public void setImeIPrezime(String imeIPrezime) {
		this.imeIPrezime = imeIPrezime;
	}

	public String getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(String odeljenje) {
		this.odeljenje = odeljenje;
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
