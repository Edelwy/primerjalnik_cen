package si.fri.prpo.skupina3.entitete;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.*;

// zna bit da bo to useless entiteta :(

@Entity(name="primerjalnik")
@NamedQueries(value =
        {
                @NamedQuery(name="Primerjalnik.getAll", query="SELECT o FROM primerjalnik o"),
                @NamedQuery(name="Primerjalnik.getById", query="SELECT o FROM primerjalnik o WHERE o.id = :id"),
                @NamedQuery(name="Primerjalnik.getByKolicina", query="SELECT o FROM primerjalnik o WHERE o.stevilo = :stevilo"),
                @NamedQuery(name="Primerjalnik.getKosarice", query="SELECT o.kosarice FROM primerjalnik o WHERE o.kosarice IS NOT EMPTY ")
        })

public class Primerjalnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="primerjalnik_id")
    private Integer id;
    private Integer stevilo;

    @OneToMany(mappedBy="Primerjalnik", cascade=CascadeType.ALL)
    private List<Kosarica> kosarice;

    @OneToOne(mappedBy="primerjalnik", cascade=CascadeType.ALL)
    private Uporabnik uporabnik;

    // getter metode:
    public Integer getId() {return id;}
    public Integer getSteviloKosaric() {return stevilo;}
    public List<Kosarica> getKosarice() {return kosarice;}
    public Uporabnik getUporabnik() {return uporabnik;}

    // setter metode:
    public void setId(Integer id) {this.id = id;}
    public void setSteviloKosaric(Integer st) {this.stevilo = st;}
    public void setUporabnik(Uporabnik uporabnik) {this.uporabnik = uporabnik;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Primerjalnik osebe: ");
        sb.append(this.uporabnik.getIme() + "\n");
        sb.append(" Kolicina izdelkov: ");
        sb.append(this.stevilo + "\n");

        return sb.toString();
    }

}
