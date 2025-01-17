package si.fri.prpo.skupina3.storitve.zrna;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
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

    @BeleziKlice
    public List<Primerjalnik> pridobiPrimerjalnike() {
        List<Primerjalnik> primerjalniki = em.createNamedQuery("Primerjalnik.getAll").getResultList();
        return primerjalniki;
    }

    public List<Primerjalnik> pridobiPrimerjalnike(QueryParameters query) {
        return JPAUtils.queryEntities(em, Primerjalnik.class, query);
    }

    public Long pridobiPrimerjalnikeCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Primerjalnik.class, query);
    }

    public List<Primerjalnik> pridobiPrimerjalnikeCriteriaAPI() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Primerjalnik> query = criteriaBuilder.createQuery(Primerjalnik.class);
        Root<Primerjalnik> from = query.from(Primerjalnik.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    @BeleziKlice
    public Primerjalnik pridobiPrimerjalnik(int primerjalnikId) {
        Primerjalnik primerjalnik = em.find(Primerjalnik.class, primerjalnikId);
        return primerjalnik;
    }

    @BeleziKlice
    @Transactional
    public Primerjalnik dodajPrimerjalnik(Integer kolicina) {
        Primerjalnik novPrimerjalnik = new Primerjalnik();
        novPrimerjalnik.setSteviloKosaric(kolicina);
        em.persist(novPrimerjalnik);
        return novPrimerjalnik;
    }

    @BeleziKlice
    @Transactional
    public boolean posodobiPrimerjalnik(int id) {
        Primerjalnik primerjalnik = pridobiPrimerjalnik(id);
        if(primerjalnik == null) return false;
        em.merge(primerjalnik);
        return true;
    }

    @BeleziKlice
    @Transactional
    public boolean odstraniPrimerjalnik(int primerjalnikId) {
        Primerjalnik primerjalnik = pridobiPrimerjalnik(primerjalnikId);
        if (primerjalnik != null) {
            em.remove(primerjalnik);
            return true;
        }
        return false;
    }
}
