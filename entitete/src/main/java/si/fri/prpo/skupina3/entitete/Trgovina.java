package si.fri.prpo.skupina3.entitete;
import javax.persistence.*;
import java.util.*;

@Entity(name="trgovina")
@NamedQueries(value={
                @NamedQuery(name="Trgovina.getAll", query="SELECT t FROM trgovina t"),
                @NamedQuery(name="Trgovina.getById", query="SELECT t FROM trgovina t WHERE t.id = :id"),
                @NamedQuery(name="Trgovina.getKosarice", query="SELECT t.kosarice FROM trgovina t WHERE t.kosarice IS NOT EMPTY"),
                @NamedQuery(name="Trgovina.getProdukti", query="SELECT t.produkti FROM trgovina t WHERE t.produkti IS NOT EMPTY")
        })

public class Trgovina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trgovina_id")
    private Integer id;
    private String ime;

    @OneToMany(mappedBy="Trgovina", cascade=CascadeType.ALL)
    private List<Kosarica> kosarice;

    @OneToMany(mappedBy="Trgovina", cascade=CascadeType.ALL)
    private List<Produkt> produkti;

    //getter metode:
    public Integer getId() {return id;}
    public String getIme() {return ime;}
    public List<Produkt> getProdukti() {return produkti;}
    public List<Kosarica> getKosarice() {return kosarice;}

    //setter metode:
    public void setId(Integer id) {this.id = id;}
    public void setIme(String ime) {this.ime = ime;}

    //dodamo in brisemo kosarice
    //public void addKosarica(Kosarica kosarica) {kosarice.add(kosarica);}
    //public void deleteKosarica(Kosarica kosarica) {kosarice.remove(kosarica);}

    //dodamo ali izbrisemo produkt:
    //public void addProdukt(Produkt produkt) {produkti.add(produkt);}
    //public void deleteProdukt(Produkt produkt) {produkti.remove(produkt);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ime: ");
        sb.append(this.ime);
        return sb.toString();
    }
}
