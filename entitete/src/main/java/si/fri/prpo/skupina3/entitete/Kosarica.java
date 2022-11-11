package si.fri.prpo.skupina3.entitete;
import javax.persistence.*;
import java.util.*;
@Entity(name="kosarica")
@NamedQueries(value =
        {
                @NamedQuery(name="Kosarica.getAll", query="SELECT o FROM kosarica o")
        })

public class Kosarica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_kosarica")
    private Integer id;
    private Integer kolicinaProduktov;
    private Integer popust;
    private Integer postnina;
    private Integer total;

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
    public Integer getKolicinaProduktov() {return kolicinaProduktov;}
    public Integer getPopust() {return popust;}
    public Integer getPostnina() {return postnina;}
    public Integer getTotal() {return total;}
    public Primerjalnik getPrimerjalnik() {return primerjalnik;}
    public Trgovina getTrgovina() {return trgovina;}
    public Uporabnik getUporabnik() {return uporabnik;}
    public List<Produkt> getProdukti() {return produkti;}

    // setter metode:
    public void setId(Integer id) {this.id = id;}
    public void setKolicinaProduktov(Integer kolicinaProduktov) {this.kolicinaProduktov = kolicinaProduktov;}
    public void setPopust(Integer popust) {this.popust = popust;}
    public void setPostnina(Integer postnina) {this.postnina = postnina;}
    public void setPrimerjalnik(Primerjalnik primerjalnik) {this.primerjalnik = primerjalnik;}
    public void setTotal(Integer total) {this.total = total;}
    public void setTrgovina(Trgovina trgovina) {this.trgovina = trgovina;}
    public void setUporabnik(Uporabnik uporabnik) {this.uporabnik = uporabnik;}

    //dodamo ali izbrisemo produkt:
    public void addProdukt(Produkt produkt) {produkti.add(produkt);}
    public void deleteProdukt(Produkt produkt) {produkti.remove(produkt);}
}
