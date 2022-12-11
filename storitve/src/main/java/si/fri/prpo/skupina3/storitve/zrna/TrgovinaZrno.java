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

    @BeleziKlice
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

    @BeleziKlice
    public Trgovina pridobiTrgovino(int trgovinaId) {
        Trgovina trgovina = em.find(Trgovina.class, trgovinaId);
        return trgovina;
    }

    @BeleziKlice
    @Transactional
    public Trgovina dodajTrgovino(String ime) {
        Query q = em.createNamedQuery("Trgovina.getByName");
        q.setParameter("ime", ime);


        if(!q.getResultList().isEmpty()) {
            log.info("Trgovina s tem imenom ze obstaja.");
            return (Trgovina) q.getResultList().get(0);
        }

        Trgovina novaTrgovina = new Trgovina();
        novaTrgovina.setIme(ime);
        em.persist(novaTrgovina);
        return novaTrgovina;
    }

    @BeleziKlice
    @Transactional
    public boolean posodobiTrgovino(int id) {
        Trgovina trgovina = pridobiTrgovino(id);
        if(trgovina == null) return false;
        em.merge(trgovina);
        return true;
    }

    @BeleziKlice
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
