package si.fri.prpo.skupina3.storitve.dtos;

import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Primerjalnik;
import si.fri.prpo.skupina3.entitete.Uporabnik;

import java.util.List;

public class UporabnikDto {
    private Integer id;
    private String ime;
    private String priimek;
    private String username;
    private Primerjalnik primerjalnik;

    public UporabnikDto() {
    }

    public UporabnikDto(String ime, String priimek, String username) {
        this.ime = ime;
        this.priimek = priimek;
        this.username = username;
    }

    public  UporabnikDto(Uporabnik uporabnik) {
        this.id = uporabnik.getId();
        this.ime = uporabnik.getIme();
        this.priimek = uporabnik.getPriimek();
        this.username = uporabnik.getUsername();
        this.primerjalnik = uporabnik.getPrimerjalnik();
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getIme() {return ime;}
    public void setIme(String ime) {this.ime = ime;}

    public String getPriimek() {return priimek;}
    public void setPriimek(String priimek) {this.priimek = priimek;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public Primerjalnik getPrimerjalnik() {return primerjalnik;}
    public void setPrimerjalnik(Primerjalnik primerjalnik) {this.primerjalnik = primerjalnik;}

}
