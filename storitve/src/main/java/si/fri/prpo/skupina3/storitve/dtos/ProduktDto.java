package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Trgovina;

public class ProduktDto {
    private Integer id;
    private String ime;
    private Integer cena;
    private String opis;

    public ProduktDto() {
    }

    public ProduktDto(String ime, Integer cena, String opis) {
        this.ime = ime;
        this.cena = cena;
        this.opis = opis;
    }

    public ProduktDto(Produkt produkt) {
        this.id = produkt.getId();
        this.ime = produkt.getIme();
        this.cena = produkt.getCena();
        this.opis = produkt.getOpis();
    }

    public Integer getProduktId() { return id; }
    public void setProduktId(Integer produktId) {
        this.id = produktId;
    }

    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }

    public Integer getCena() {
        return cena;
    }
    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }
    public void setOpis(String opis) { this.opis = opis; }
}
