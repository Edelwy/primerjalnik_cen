package si.fri.prpo.skupina3.entitete;
import javax.persistence.*;
import java.util.*;

@Entity(name="uporabnik")
@NamedQueries(value={
        @NamedQuery(name="Uporabnik.getAll", query="SELECT u FROM uporabnik u"),
        @NamedQuery(name="Uporabnik.getByUsername", query="SELECT u FROM uporabnik u WHERE u.username = :username" ),
        @NamedQuery(name="Uporabnik.getById", query="SELECT u FROM uporabnik u WHERE u.id = :id" ),
        @NamedQuery(name="Uporabnik.getByLegalName", query="SELECT u FROM uporabnik u WHERE u.ime = :ime AND u.priimek = :priimek")
        })

public class Uporabnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_uporabnik")
    private Integer id;
    private String ime;
    private String priimek;
    @Column(unique=true)
    private String username;

    @OneToMany(mappedBy="seznamKosaric", cascade=CascadeType.ALL)
    private List<Kosarica> kosarice;

    @OneToOne
    @JoinColumn(name="id_primerjalnik")
    private Primerjalnik primerjalnik;


    // getter metode:
    public Integer getId() {return id;}
    public String getIme() {return ime;}
    public String getPriimek() {return priimek;}
    public String getUsername() {return username;}
    public List<Kosarica> getKosarice() {return kosarice;}
    public Primerjalnik getPrimerjalnik() {return primerjalnik;}

    // setter metode:
    public void setId(Integer id) {this.id = id;}
    public void setIme(String ime) {this.ime = ime;}
    public void setPriimek(String priimek) {this.priimek = priimek;}
    public void setUsername(String username) {this.username = username;}
    public void setPrimerjalnik(Primerjalnik primerjalnik) {this.primerjalnik = primerjalnik;}

    //dodajanje in brisanje kosarice:
    public void addKosarica(Kosarica kosarica) {kosarice.add(kosarica);}
    public void deleteKosarica(Kosarica kosarica) {kosarice.remove(kosarica);}
}
