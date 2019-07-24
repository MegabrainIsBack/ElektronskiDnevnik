package com.iktpreobuka.compositeKeys.Keys;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Predmet;

@Embeddable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PredmetiKojeNastavnikPredajeId implements Serializable{
	
	@JsonIgnore
	@JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
	private Nastavnik nastavnik;
	
	@JsonIgnore
	@JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    private Predmet predmet;
    
    public Nastavnik getNastavnik() {
        return nastavnik;
    }
 
    public void setNastavnik(Nastavnik nastavnik) {
        this.nastavnik = nastavnik;
    }
  
    public Predmet getPredmet() {
        return predmet;
    }
 
    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

}
