package si.fri.prpo.skupina3.entitete;
import javax.persistence.*;
import java.util.*;
import  javax.json.bind.annotation.*;


@Entity(name="produkt")
@NamedQueries(value={
                @NamedQuery(name="Produkt.getAll", query="SELECT p FROM produkt p"),
                @NamedQuery(name="Produkt.getCheaperThan", query="SELECT p FROM produkt p WHERE p.cena < :cena"),
                @NamedQuery(name="Produkt.getByName", query="SELECT p FROM produkt p WHERE p.ime = :ime"),
                @NamedQuery(name="Produkt.getById", query="SELECT p FROM produkt p WHERE p.id = :id"),
                @NamedQuery(name="Produkt.getByNameAndStore", query="SELECT p FROM produkt p WHERE p.ime = :ime AND p.trgovina = :trgovina")
        })

public class Produkt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_produkt")
    private Integer id;
    private String ime;
    private Integer cena;
    private String opis;

    @JsonbTransient
    @ManyToMany(mappedBy="produkti", cascade=CascadeType.MERGE)
    private List<Kosarica> kosarice;

    @ManyToOne
    @JoinColumn(name="trgovina_id")
    private Trgovina trgovina;

    //getter metode:
    public Integer getId() {return id;}
    public String getIme() {return ime;}
    public Integer getCena() {return cena;}
    public String getOpis() {return opis;}
    public Trgovina getTrgovina() {return trgovina;}
    public List<Kosarica> getKosarice() {return kosarice;}

    //setter metode:
    public void setId(Integer id) {this.id = id;}
    public void setIme(String ime) {this.ime = ime;}
    public void setCena(Integer cena) {this.cena = cena;}
    public void setOpis(String opis) {this.opis = opis;}
    public void setTrgovina(Trgovina trgovina) {this.trgovina = trgovina;}

    //dodamo in brisemo kosarice
    //public void addKosarica(Kosarica kosarica) {kosarice.add(kosarica);}
    //public void deleteKosarica(Kosarica kosarica) {kosarice.remove(kosarica);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ime: ");
        sb.append(this.ime + " | ");
        sb.append(" Cena: ");
        sb.append(this.cena + " \n ");
        sb.append("Opis: ");
        sb.append(this.opis);
        int kolicina = this.kosarice.toArray().length;
        sb.append("\n Produkt je v toliko kosaricah: " + kolicina + "\n ");
        sb.append("Trgovina: ");
        sb.append(this.trgovina.getIme() + "\n");

        return sb.toString();
    }
}
