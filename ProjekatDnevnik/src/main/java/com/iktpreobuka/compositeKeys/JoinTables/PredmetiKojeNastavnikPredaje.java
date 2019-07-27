package com.iktpreobuka.compositeKeys.JoinTables;

/*import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.compositeKeys.Keys.PredmetiKojeNastavnikPredajeId;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Predmet;
 
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "PredmetiKojeNastavnikPredaje")
@AssociationOverrides({
    @AssociationOverride(name = "idPredmeta",
        joinColumns = @JoinColumn(name = "idPredmeta")),
    @AssociationOverride(name = "id",
        joinColumns = @JoinColumn(name = "id")) })
public class PredmetiKojeNastavnikPredaje {
    // composite-id key
	@EmbeddedId
    private PredmetiKojeNastavnikPredajeId primaryKey = new PredmetiKojeNastavnikPredajeId();
 
    public PredmetiKojeNastavnikPredajeId getPrimaryKey() {
        return primaryKey;
    }
 
    public void setPrimaryKey(PredmetiKojeNastavnikPredajeId primaryKey) {
        this.primaryKey = primaryKey;
    }
 
    @Transient
    public Nastavnik getNastavnik() {
        return getPrimaryKey().getNastavnik();
    }
 
    public void setNastavnik(Nastavnik nastavnik) {
        getPrimaryKey().setNastavnik(nastavnik);
    }
 
    @Transient
    public Predmet getPredmet() {
        return getPrimaryKey().getPredmet();
    }
 
    public void setPredmet(Predmet predmet) {
        getPrimaryKey().setPredmet(predmet);
    }

}*/

