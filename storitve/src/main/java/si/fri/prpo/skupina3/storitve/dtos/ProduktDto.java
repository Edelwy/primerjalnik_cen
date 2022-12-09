package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Trgovina;

public class ProduktDto {
    private Integer id;
    private String ime;
    private Integer cena;
    private String opis;

    private Trgovina trgovina;
    private Kosarica kosarica;

    public ProduktDto(Trgovina trgovina, String ime, Integer cena, String opis) {
        this.trgovina = trgovina;
        this.ime = ime;
        this.cena = cena;
        this.opis = opis;
    }

    public ProduktDto(Produkt produkt) {
        this.trgovina = produkt.getTrgovina();
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

    public Trgovina getTrgovina() { return trgovina; }
    public void setTrgovina(Trgovina trgovina) { this.trgovina = trgovina; }

    public Kosarica getKosarica() { return kosarica; }
    public void setKosarica(Kosarica kosarica) { this.kosarica = kosarica; }
}
