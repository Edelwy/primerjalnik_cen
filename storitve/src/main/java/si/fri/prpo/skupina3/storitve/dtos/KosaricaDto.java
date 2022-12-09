package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class KosaricaDto {
    private Integer id;
    private Integer popust;
    private Integer postnina;
    private Integer kolicina;

    public KosaricaDto() {
    }

    public KosaricaDto(int popust, int postnina) {
        this.kolicina = 0;
        this.popust = popust;
        this.postnina = postnina;
    }

    public KosaricaDto(Kosarica kosarica) {
        this.id = kosarica.getId();
        this.popust = kosarica.getPopust();
        this.postnina = kosarica.getPostnina();
        this.kolicina = kosarica.getKolicinaProduktov();
    }

    public Integer getKosaricaId() { return id; }
    public void setKosaricaId(Integer kosaricaId) { this.id = kosaricaId; }

    public Integer getPopust() {return popust;}
    public void setPopust(Integer popust) {this.popust = popust;}

    public Integer getPostnina() {return postnina;}
    public void setPostnina(Integer postnina) {this.postnina = postnina;}

}
