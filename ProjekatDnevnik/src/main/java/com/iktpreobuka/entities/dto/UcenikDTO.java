package com.iktpreobuka.entities.dto;

import com.iktpreobuka.entities.RoditeljMajka;
import com.iktpreobuka.entities.RoditeljOtac;

public class UcenikDTO {

	private String ime;
	private String prezime;
	private String odeljenje;
	private RoditeljOtac tata;
	private RoditeljMajka mama;
	
	public UcenikDTO() {
		
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

	public RoditeljOtac getTata() {
		return tata;
	}

	public void setTata(RoditeljOtac tata) {
		this.tata = tata;
	}

	public RoditeljMajka getMama() {
		return mama;
	}

	public void setMama(RoditeljMajka mama) {
		this.mama = mama;
	}

}