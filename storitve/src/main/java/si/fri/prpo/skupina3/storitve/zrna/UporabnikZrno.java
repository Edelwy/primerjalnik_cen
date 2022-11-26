package si.fri.prpo.skupina3.storitve.zrna;
import java.util.List;
import java.util.logging.Logger;
import si.fri.prpo.skupina3.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@ApplicationScoped
public class UporabnikZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UporabnikZrno.class.getSimpleName());

        //inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UporabnikZrno.class.getSimpleName());
        // zapiranje virov
    }

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Uporabnik> pridobiUporabnike() {
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();
        return uporabniki;
    }

    public List<Uporabnik> pridobiUporabnikeCriteriaAPI() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Uporabnik> query = criteriaBuilder.createQuery(Uporabnik.class);
        Root<Uporabnik> from = query.from(Uporabnik.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    public Uporabnik pridobiUporabnika(int uporabnikId) {
        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);
        return uporabnik;
    }

    @Transactional
    public Uporabnik dodajUporabnika(Uporabnik uporabnik) {
        if (uporabnik != null) {
            em.persist(uporabnik);
        }
        return uporabnik;
    }

    @Transactional
    public Uporabnik posodobiUporabnika(int uporabnikId, Uporabnik uporabnik) {
        Uporabnik u = em.find(Uporabnik.class, uporabnikId);
        uporabnik.setId(u.getId());
        em.merge(uporabnik);
        return  uporabnik;
    }

    @Transactional
    public boolean odstraniUporabnika(int uporabnikId) {
        Uporabnik uporabnik = pridobiUporabnika(uporabnikId);
        if (uporabnik != null) {
            em.remove(uporabnik);
            return true;
        }
        return false;
    }
}
