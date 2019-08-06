package com.iktpreobuka.entities.dto.ocjene;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.iktpreobuka.controllers.utilities.BrojcanaOcjenaITimestamp;

@JsonPropertyOrder({"Predmet"})
public class OcjeneIzSvihPredmetaDTO {
	
	@JsonProperty("Predmet")
	private String imePredmeta;
	
	
	private List<BrojcanaOcjenaITimestamp> OcjenaIDatum;

	public OcjeneIzSvihPredmetaDTO() {
		super();
	}

	public String getImePredmeta() {
		return imePredmeta;
	}

	public void setImePredmeta(String imePredmeta) {
		this.imePredmeta = imePredmeta;
	}

	public List<BrojcanaOcjenaITimestamp> getOcjenaIDatum() {
		return OcjenaIDatum;
	}

	public void setOcjenaIDatum(List<BrojcanaOcjenaITimestamp> ocjenaIDatum) {
		OcjenaIDatum = ocjenaIDatum;
	}

}
