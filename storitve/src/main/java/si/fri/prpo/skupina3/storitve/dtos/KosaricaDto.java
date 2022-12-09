package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class KosaricaDto {
    private Integer id;
    private Integer popust;
    private Integer postnina;

    private Primerjalnik primerjalnik;
    private Trgovina trgovina;
    private Uporabnik uporabnik;

    public KosaricaDto(Uporabnik uporabnik, Trgovina trgovina, int popust, int postnina) {
        this.uporabnik = uporabnik;
        this.popust = popust;
        this.postnina = postnina;
        this.trgovina = trgovina;
    }

    public KosaricaDto(Kosarica kosarica) {
        this.id = kosarica.getId();
        this.uporabnik = kosarica.getUporabnik();
        this.popust = kosarica.getPopust();
        this.postnina = kosarica.getPostnina();
        this.trgovina = kosarica.getTrgovina();
        this.primerjalnik = kosarica.getPrimerjalnik();
    }

    public Integer getKosaricaId() { return id; }
    public void setKosaricaId(Integer kosaricaId) { this.id = kosaricaId; }

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
