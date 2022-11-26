package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.Trgovina;

public class ProduktDto {
    private Integer produktId;
    private Integer uporabnikId;
    private String ime;
    private Integer cena;
    private String opis;
    private Trgovina trgovina;

    public Integer getProduktId() { return produktId; }
    public void setProduktId(Integer produktId) {
        this.produktId = produktId;
    }

    public Integer getUporabnikId() { return uporabnikId; }
    public void setUporabnikId(Integer uporabnikId) {
        this.uporabnikId = uporabnikId;
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
}
