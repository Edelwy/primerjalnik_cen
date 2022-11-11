package si.fri.prpo.skupina3.storitve.zrna;
import si.fri.prpo.skupina3.entitete.*;
import javax.enterprise.context.*;
import javax.persistence.*;
import java.util.*;

@ApplicationScoped
public class ProduktZrno {
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Produkt> getIzdelki() {
        List<Produkt> produkti = em.createNamedQuery("Uporabnik.getAll").getResultList();
        return  produkti;
    }
}
