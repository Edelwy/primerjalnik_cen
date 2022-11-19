package si.fri.prpo.skupina3.storitve.zrna;
import si.fri.prpo.skupina3.entitete.*;
import javax.enterprise.context.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

@ApplicationScoped
public class ProduktZrno {
    @PersistenceContext(unitName="primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Produkt> getProdukt() {
        List<Produkt> produkti = em.createNamedQuery("Produkt.getAll").getResultList();
        return  produkti;
    }

    public List<Produkt> getCheaper() {
        Query q = em.createNamedQuery("Produkt.getCheaperThan");
        q.setParameter("cena", 40);
        List<Produkt> produkti = q.getResultList();
        return  produkti;
    }

    public List<Produkt> getProduktCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Produkt> productQuery = cb.createQuery(Produkt.class);

        Root<Produkt> produkt = productQuery.from(Produkt.class);
        productQuery.select(produkt);
        TypedQuery<Produkt> typedQuery = em.createQuery(productQuery);
        List<Produkt> produkti = typedQuery.getResultList();
        return  produkti;
    }
}
