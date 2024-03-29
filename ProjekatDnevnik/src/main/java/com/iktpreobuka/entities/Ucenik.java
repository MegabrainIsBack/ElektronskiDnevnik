package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.JoinTables.UPO;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ucenik extends Korisnik {
	
	

	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "IdOdeljenja")
    private Odeljenje odeljenjeU;
	
	public Odeljenje getOdeljenjeU() {
		return odeljenjeU;
	}

	public void setOdeljenjeU(Odeljenje odeljenjeU) {
		this.odeljenjeU = odeljenjeU;
	}


	//@JsonBackReference
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="IdOca")
	private RoditeljOtac tata;
	
	//@JsonBackReference
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="IdMajke")
	private RoditeljMajka mama;
	
	
	public RoditeljMajka getMama() {
		return mama;
	}

	public void setMama(RoditeljMajka mama) {
		this.mama = mama;
	}

	public RoditeljOtac getTata() {
		return tata;
	}

	public void setTata(RoditeljOtac tata) {
		this.tata = tata;
	}
	
	@OneToMany(mappedBy = "ucenik",
		    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<UPO> upo= new ArrayList<UPO>();
	
	public List<UPO> getUpo() {
		return upo;
	}

	public void setUpo(List<UPO> upo) {
		this.upo = upo;
	}
	
	
	@Column(name="ImeOca")
	private String imeOca;
	

	@Column(name="ImeMajke")
	private String imeMajke;
	
	@NotNull(message = "Morate unijeti odeljenje.")
	@Column(name="Odeljenje")
	private String odeljenje;
	
	public Ucenik() {
		
	}

	public String getImeOca() {
		return imeOca;
	}

	public void setImeOca(String imeOca) {
		this.imeOca = imeOca;
	}

	public String getImeMajke() {
		return imeMajke;
	}

	public void setImeMajke(String imeMajke) {
		this.imeMajke = imeMajke;
	}

	public String getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(String odeljenje) {
		this.odeljenje = odeljenje;
	}

}
