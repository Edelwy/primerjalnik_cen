package si.fri.prpo.skupina3.entitete;
import javax.persistence.*;
import java.util.*;

@Entity(name="trgovina")
@NamedQueries(value =
        {
                @NamedQuery(name="Trgoivna.getAll", query="SELECT o FROM trgovina o")
        })

public class Trgovina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_trgovina")
    private Integer id;
    private String ime;

    @OneToMany(mappedBy="seznamKosaric", cascade=CascadeType.ALL)
    private List<Kosarica> kosarice;

    @OneToMany(mappedBy="seznamProduktov", cascade=CascadeType.ALL)
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
    public void addKosarica(Kosarica kosarica) {kosarice.add(kosarica);}
    public void deleteKosarica(Kosarica kosarica) {kosarice.remove(kosarica);}

    //dodamo ali izbrisemo produkt:
    public void addProdukt(Produkt produkt) {produkti.add(produkt);}
    public void deleteProdukt(Produkt produkt) {produkti.remove(produkt);}
}