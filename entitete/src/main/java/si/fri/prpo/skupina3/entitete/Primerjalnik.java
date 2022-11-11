package si.fri.prpo.skupina3.entitete;
import javax.persistence.*;
import java.util.*;

@Entity(name="primerjalnik")
@NamedQueries(value =
        {
                @NamedQuery(name="Primerjalnik.getAll", query="SELECT o FROM primerjalnik o"),
                @NamedQuery(name="Primerjalnik.getById", query="SELECT o FROM primerjalnik o WHERE o.id = :id"),
                @NamedQuery(name="Primerjalnik.getByKolicina", query="SELECT o FROM primerjalnik o WHERE o.steviloKosaric = :stevilo"),
                @NamedQuery(name="Primerjalnik.getKosarice", query="SELECT o.kosarice FROM primerjalnik o WHERE o.kosarice IS NOT EMPTY ")
        })

public class Primerjalnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_primerjalnik")
    private Integer id;
    private Integer steviloKosaric;

    @OneToMany(mappedBy="seznamKosaric", cascade=CascadeType.ALL)
    private List<Kosarica> kosarice;

    // getter metode:
    public Integer getId() {return id;}
    public Integer getSteviloKosaric() {return steviloKosaric;}
    public List<Kosarica> getKosarice() {return kosarice;}

    // setter metode:
    public void setId(Integer id) {this.id = id;}
    public void setSteviloKosaric(Integer st) {this.steviloKosaric = st;}

    //dodamo in brisemo kosarice
    public void addKosarica(Kosarica kosarica) {kosarice.add(kosarica);}
    public void deleteKosarica(Kosarica kosarica) {kosarice.remove(kosarica);}
}
