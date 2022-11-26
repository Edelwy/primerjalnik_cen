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
public class PrimerjalnikZrno {
    private Logger log = Logger.getLogger(PrimerjalnikZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + PrimerjalnikZrno.class.getSimpleName());

        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + PrimerjalnikZrno.class.getSimpleName());
        // zapiranje virov
    }
    @PersistenceContext(unitName="primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Primerjalnik> pridobiPrimerjalnike() {
        List<Primerjalnik> primerjalniki = em.createNamedQuery("Primerjalnik.getAll").getResultList();
        return primerjalniki;
    }

    public List<Primerjalnik> pridobiPrimerjalnikeCriteriaAPI() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Primerjalnik> query = criteriaBuilder.createQuery(Primerjalnik.class);
        Root<Primerjalnik> from = query.from(Primerjalnik.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    public Primerjalnik pridobiPrimerjalnik(int primerjalnikId) {
        Primerjalnik primerjalnik = em.find(Primerjalnik.class, primerjalnikId);
        return primerjalnik;
    }

    @Transactional
    public Primerjalnik dodajPrimerjalnik(Primerjalnik primerjalnik) {
        if (primerjalnik != null) {
            em.persist(primerjalnik);
        }
        return primerjalnik;
    }

    @Transactional
    public Primerjalnik posodobiPrimerjalnik(int primerjalnikId, Primerjalnik primerjalnik) {
        Produkt p = em.find(Produkt.class, primerjalnikId);
        primerjalnik.setId(p.getId());
        em.merge(primerjalnik);
        return primerjalnik;
    }

    @Transactional
    public boolean odstraniPrimerjalnik(int primerjalnikId) {
        Primerjalnik primerjalnik = pridobiPrimerjalnik(primerjalnikId);
        if (primerjalnik != null) {
            em.remove(primerjalnikId);
            return true;
        }
        return false;
    }
}