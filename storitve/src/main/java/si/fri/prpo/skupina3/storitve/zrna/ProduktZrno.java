package si.fri.prpo.skupina3.storitve.zrna;
import si.fri.prpo.skupina3.entitete.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.logging.Logger;

@ApplicationScoped
public class ProduktZrno {
    private Logger log = Logger.getLogger(ProduktZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + ProduktZrno.class.getSimpleName());

        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + ProduktZrno.class.getSimpleName());
        // zapiranje virov
    }
    @PersistenceContext(unitName="primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Produkt> pridobiProdukte() {
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

    public Produkt pridobiProdukt(int produktId) {
        Produkt produkt = em.find(Produkt.class, produktId);
        return produkt;
    }

    @Transactional
    public Produkt dodajProdukt(Produkt produkt) {
        if (produkt != null) {
            em.persist(produkt);
        }
        return produkt;
    }

    @Transactional
    public Produkt posodobiProdukt(int produktId, Produkt produkt) {
        Produkt p = em.find(Produkt.class, produktId);
        produkt.setId(p.getId());
        em.merge(produkt);
        return  produkt;
    }

    @Transactional
    public boolean odstraniProdukt(int produktId) {
        Produkt produkt = pridobiProdukt(produktId);
        if (produkt != null) {
            em.remove(produkt);
            return true;
        }
        return false;
    }
}
