package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.Trgovina;

public class TrgovinaDto {
    private String ime;

    public TrgovinaDto() {
    }

    public TrgovinaDto(Trgovina trgovina) {
        this.ime = trgovina.getIme();
    }

    public String getIme() { return ime;}
    public void setIme(String ime) {this.ime = ime;}
}
