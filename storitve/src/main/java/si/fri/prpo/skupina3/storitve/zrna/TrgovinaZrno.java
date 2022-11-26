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
public class TrgovinaZrno {
    private Logger log = Logger.getLogger(TrgovinaZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + TrgovinaZrno.class.getSimpleName());

        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + TrgovinaZrno.class.getSimpleName());
        // zapiranje virov
    }
    @PersistenceContext(unitName="primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Trgovina> pridobiTrgovine() {
        List<Trgovina> trgovine = em.createNamedQuery("Trgovina.getAll").getResultList();
        return trgovine;
    }

    public List<Trgovina> pridobiTrgovineCriteriaAPI() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Trgovina> query = criteriaBuilder.createQuery(Trgovina.class);
        Root<Trgovina> from = query.from(Trgovina.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    public Trgovina pridobiTrgovino(int trgovinaId) {
        Trgovina trgovina = em.find(Trgovina.class, trgovinaId);
        return trgovina;
    }

    @Transactional
    public Trgovina dodajTrgovino(Trgovina trgovina) {
        if (trgovina != null) {
            em.persist(trgovina);
        }
        return trgovina;
    }

    @Transactional
    public Trgovina posodobiTrgovino(int trgovinaId, Trgovina trgovina) {
        Trgovina t = em.find(Trgovina.class, trgovinaId);
        trgovina.setId(t.getId());
        em.merge(trgovina);
        return trgovina;
    }

    @Transactional
    public boolean odstraniTrgovino(int trgovinaId) {
        Trgovina trgovina = pridobiTrgovino(trgovinaId);
        if (trgovina != null) {
            em.remove(trgovina);
            return true;
        }
        return false;
    }
}
