package com.iktpreobuka.entities.dto.ucenik;

import java.util.ArrayList;

public class OcjeneIzJednogPredmetaDTO {
	
	private String imeIPrezime;
	
	private String Odeljenje;
	
	private String imePredmeta;
	
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
		return Odeljenje;
	}

	public void setOdeljenje(String odeljenje) {
		Odeljenje = odeljenje;
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
