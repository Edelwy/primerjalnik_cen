package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.Primerjalnik;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Trgovina;
import si.fri.prpo.skupina3.entitete.Uporabnik;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class KosaricaDto {
    private Integer kosaricaId;
    private Integer uporabnikId;
    private Integer popust;
    private Integer postnina;
    private Primerjalnik primerjalnik;
    private Trgovina trgovina;
    private Uporabnik uporabnik;

    public KosaricaDto(Integer kosaricaId, Integer uporabnikId) {
        this.kosaricaId = kosaricaId;
        this.uporabnikId = uporabnikId;
    }

    public Integer getKosaricaId() { return kosaricaId; }
    public void setKosaricaId(Integer kosaricaId) {
        this.kosaricaId = kosaricaId;
    }

    public Integer getUporabnikId() { return uporabnikId; }
    public void setUporabnikId(Integer uporabnikId) {
        this.uporabnikId = uporabnikId;
    }

    public Integer getPopust() {return popust;}
    public void setPopust(Integer popust) {this.popust = popust;}


    public Integer getPostnina() {return postnina;}
    public void setPostnina(Integer postnina) {this.postnina = postnina;}

    public Primerjalnik getPrimerjalnik() {return primerjalnik;}
    public void setPrimerjalnik(Primerjalnik primerjalnik) {this.primerjalnik = primerjalnik;}

    public Trgovina getTrgovina() {return trgovina;}
    public void setTrgovina(Trgovina trgovina) {this.trgovina = trgovina;}

    public Uporabnik getUporabnik() {return uporabnik;}
    public void setUporabnik(Uporabnik uporabnik) {this.uporabnik = uporabnik;}
}
