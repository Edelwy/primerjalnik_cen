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

    public List<Produkt> getCheaper(int n) {
        Query q = em.createNamedQuery("Produkt.getCheaperThan");
        q.setParameter("cena", n);
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

    public Produkt pridobiProdukt(int id) {
        Produkt produkt = em.find(Produkt.class, id);
        return produkt;
    }

    @Transactional
    public Produkt dodajProdukt(String ime, Integer cena, String opis) {
        Produkt novProdukt = new Produkt();
        novProdukt.setIme(ime);
        novProdukt.setCena(cena);
        novProdukt.setOpis(opis);
        em.persist(novProdukt);
        return novProdukt;
    }

    @Transactional
    public boolean posodobiProdukt(int id) {
        Produkt produkt = pridobiProdukt(id);
        if(produkt == null) return false;
        em.merge(produkt);
        return true;
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
