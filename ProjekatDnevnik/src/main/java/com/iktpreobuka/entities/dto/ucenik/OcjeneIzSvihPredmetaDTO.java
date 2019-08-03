package com.iktpreobuka.entities.dto.ucenik;

import java.util.ArrayList;

public class OcjeneIzSvihPredmetaDTO {
	
	private String imePredmeta;
	
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
