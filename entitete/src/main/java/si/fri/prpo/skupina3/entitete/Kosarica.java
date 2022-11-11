package si.fri.prpo.skupina3.entitete;
import javax.persistence.*;
import java.util.*;
@Entity(name="kosarica")
@NamedQueries(value={
                @NamedQuery(name="Kosarica.getAll", query="SELECT k FROM kosarica k"),
                @NamedQuery(name="Kosarica.geById", query="SELECT k FROM kosarica k WHERE k.id = :id"),
                @NamedQuery(name="Kosarica.geByKolicina", query="SELECT k FROM kosarica k WHERE k.kolicina = :kolicina"),
                @NamedQuery(name="Kosarica.getIfPopust", query="SELECT k FROM kosarica k WHERE k.popust IS NOT NULL"),
                @NamedQuery(name="Kosarica.getIfPostnina", query="SELECT k FROM kosarica k WHERE k.postnina IS NOT NULL")
        })

public class Kosarica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_kosarica")
    private Integer id;
    private Integer kolicina;
    private Integer popust;
    private Integer postnina;

    @ManyToOne
    @JoinColumn(name="id_uporabnik")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name="id_primerjalnik")
    private Primerjalnik primerjalnik;

    @ManyToOne
    @JoinColumn(name="id_trgovina")
    private Trgovina trgovina;

    @ManyToMany(mappedBy="seznamProduktov", cascade=CascadeType.ALL)
    private List<Produkt> produkti;

    // getter metode:
    public Integer getId() {return id;}
    public Integer getKolicinaProduktov() {return kolicina;}
    public Integer getPopust() {return popust;}
    public Integer getPostnina() {return postnina;}
    public Primerjalnik getPrimerjalnik() {return primerjalnik;}
    public Trgovina getTrgovina() {return trgovina;}
    public Uporabnik getUporabnik() {return uporabnik;}
    public List<Produkt> getProdukti() {return produkti;}

    // setter metode:
    public void setId(Integer id) {this.id = id;}
    public void setKolicinaProduktov(Integer kolicinaProduktov) {this.kolicina = kolicinaProduktov;}
    public void setPopust(Integer popust) {this.popust = popust;}
    public void setPostnina(Integer postnina) {this.postnina = postnina;}
    public void setPrimerjalnik(Primerjalnik primerjalnik) {this.primerjalnik = primerjalnik;}
    public void setTrgovina(Trgovina trgovina) {this.trgovina = trgovina;}
    public void setUporabnik(Uporabnik uporabnik) {this.uporabnik = uporabnik;}

    //dodamo ali izbrisemo produkt:
    public void addProdukt(Produkt produkt) {produkti.add(produkt);}
    public void deleteProdukt(Produkt produkt) {produkti.remove(produkt);}
}
