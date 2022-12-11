package si.fri.prpo.skupina3.storitve.zrna;
import si.fri.prpo.skupina3.entitete.*;
import si.fri.prpo.skupina3.storitve.anotacije.BeleziKlice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.logging.Logger;

@ApplicationScoped
public class KosaricaZrno {
    private Logger log = Logger.getLogger(KosaricaZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KosaricaZrno.class.getSimpleName());
        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KosaricaZrno.class.getSimpleName());
        // zapiranje virov
    }
    @PersistenceContext(unitName="primerjalnik-cen-jpa")
    private EntityManager em;

    @BeleziKlice
    public List<Kosarica> pridobiKosarice() {
        List<Kosarica> kosarice = em.createNamedQuery("Kosarica.getAll").getResultList();
        return kosarice;
    }

    public List<Kosarica> pridobiKosariceCriteriaAPI() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Kosarica> query = criteriaBuilder.createQuery(Kosarica.class);
        Root<Kosarica> from = query.from(Kosarica.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    @BeleziKlice
    public Kosarica pridobiKosarico(int kosaricaId) {
        Kosarica kosarica = em.find(Kosarica.class, kosaricaId);
        return kosarica;
    }

    @BeleziKlice
    @Transactional
    public Kosarica dodajKosarico(int kolicina, int popust, int postnina) {
        Kosarica novaKosarica = new Kosarica();
        novaKosarica.setPopust(popust);
        novaKosarica.setPostnina(postnina);
        novaKosarica.setKolicinaProduktov(kolicina);
        em.persist(novaKosarica);
        return novaKosarica;
    }

    @BeleziKlice
    @Transactional
    public boolean posodobiKosarico(int id) {
        Kosarica kosarica = pridobiKosarico(id);
        if(kosarica == null) return false;
        em.merge(kosarica);
        return true;
    }

    @BeleziKlice
    @Transactional
    public boolean odstraniKosarico(int id) {
        Kosarica kosarica = pridobiKosarico(id);
        if (kosarica != null) {
            em.remove(kosarica);
            return true;
        }
        return false;
    }
}
