
package si.fri.prpo.skupina3.storitve.zrna;
import java.util.List;
import java.util.logging.Logger;

import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.anotacije.BeleziKlice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import com.kumuluz.ee.rest.beans.QueryParameters;

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

    @BeleziKlice
    public List<Uporabnik> pridobiUporabnike() {
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();
        return uporabniki;
    }

    public List<Uporabnik> pridobiUporabnike(QueryParameters query) {
        return JPAUtils.queryEntities(em, Uporabnik.class, query);
    }

    public Long pridobiUporabnikeCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Uporabnik.class, query);
    }

    public List<Uporabnik> pridobiUporabnikeCriteriaAPI() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Uporabnik> query = criteriaBuilder.createQuery(Uporabnik.class);
        Root<Uporabnik> from = query.from(Uporabnik.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    @BeleziKlice
    public Uporabnik pridobiUporabnika(int id) {
        Uporabnik uporabnik = em.find(Uporabnik.class, id);
        return uporabnik;
    }

    @BeleziKlice
    @Transactional
    public Uporabnik dodajUporabnika(String ime, String priimek, String username) {
        Uporabnik novUporabnik = new Uporabnik();
        novUporabnik.setIme(ime);
        novUporabnik.setPriimek(priimek);
        novUporabnik.setUsername(username);
        em.persist(novUporabnik);
        return novUporabnik;
    }

    @BeleziKlice
    @Transactional
    public boolean posodobiUporabnika(int id) {
        Uporabnik uporabnik = pridobiUporabnika(id);
        if(uporabnik == null) return false;
        em.merge(uporabnik);
        return true;
    }

    @BeleziKlice
    @Transactional
    public boolean odstraniUporabnika(int id) {
        Uporabnik uporabnik = pridobiUporabnika(id);
        if (uporabnik != null) {
            em.remove(uporabnik);
            return true;
        }
        return false;
    }
}
