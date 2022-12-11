package si.fri.prpo.skupina3.storitve.zrna;
import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Primerjalnik;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina3.storitve.dtos.UporabnikDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(UpravljanjeUporabnikovZrno.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private PrimerjalnikZrno primerjalnikZrno;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() { log.info("Deinicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName()); }

    @BeleziKlice
    @Transactional
    public Uporabnik ustvariUporabnika(UporabnikDto uporabnikDto) {
        String ime = uporabnikDto.getIme();
        String priimek = uporabnikDto.getPriimek();
        String username = uporabnikDto.getUsername();

        Query q = em.createNamedQuery("Uporabnik.getByUsername");
        q.setParameter("username", username);

        // ce uporabnik z tem uporaabniskim imenom ze obstaja, vrnemo tistega!
        if(!q.getResultList().isEmpty()) {
            log.info("Uporabnik s tem uporabniskim imenom ze obstaja.");
            return (Uporabnik) q.getResultList().get(0);
        }

        Uporabnik uporabnik = uporabnikZrno.dodajUporabnika(ime, priimek, username);
        Primerjalnik primerjalnik = primerjalnikZrno.dodajPrimerjalnik(0);
        uporabnik.setPrimerjalnik(primerjalnik);
        primerjalnik.setUporabnik(uporabnik);

        return uporabnik;
    }

    @BeleziKlice
    public boolean izbrisiUporabnika(UporabnikDto u) {
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(u.getId());

        if (uporabnik == null) {
            log.info("Ne morem izbrisati uporabnika, ker ne obstaja.");
            return false;
        }

        Primerjalnik primerjalnik = uporabnik.getPrimerjalnik();
        if(primerjalnik != null) primerjalnikZrno.odstraniPrimerjalnik(primerjalnik.getId());
        uporabnikZrno.odstraniUporabnika(uporabnik.getId());
        return true;
    }
}
