package com.iktpreobuka.entities.dto.ocjene;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iktpreobuka.controllers.utilities.BrojcanaOcjenaITimestamp;

@JsonPropertyOrder({"Ime i prezime","Odeljenje","Predmet","Ocjena:Datum ocjenjivanja"})
@JsonRootName(value = "Ocjene ucenika")
public class OcjeneIzJednogPredmetaDTO {
	
	@JsonProperty ("Ime i prezime")
	private String imeIPrezime;
	
	@JsonProperty ("Odeljenje")
	private String odeljenje;
	
	@JsonProperty ("Predmet")
	private String imePredmeta;
	
	private List<BrojcanaOcjenaITimestamp> OcjenaIDatum;

	public List<BrojcanaOcjenaITimestamp> getOcjenaIDatum() {
		return OcjenaIDatum;
	}

	public void setOcjenaIDatum(List<BrojcanaOcjenaITimestamp> ocjenaIDatum) {
		OcjenaIDatum = ocjenaIDatum;
	}

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

	
}
