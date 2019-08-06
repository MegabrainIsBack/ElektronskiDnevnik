package com.iktpreobuka.controllers.utilities;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BrojcanaOcjenaITimestamp {

	@JsonProperty("Ocjena")
	private Integer ocjenaBrojcana;
	
	@JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "dd-MM-yyyy")
	@JsonProperty("Datum unosa ocjene")
	private Timestamp timestamp;

	public BrojcanaOcjenaITimestamp() {
		super();
	}

	public Integer getOcjenaBrojcana() {
		return ocjenaBrojcana;
	}

	public void setOcjenaBrojcana(Integer ocjenaBrojcana) {
		this.ocjenaBrojcana = ocjenaBrojcana;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
