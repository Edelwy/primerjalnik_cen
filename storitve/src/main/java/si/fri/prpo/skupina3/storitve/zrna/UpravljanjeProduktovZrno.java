package si.fri.prpo.skupina3.storitve.zrna;

import si.fri.prpo.skupina3.entitete.*;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeProduktovZrno {
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;
    private Logger log = Logger.getLogger(UpravljanjeProduktovZrno.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private ProduktZrno produktZrno;

    @Inject
    private KosaricaZrno kosaricaZrno;

    @Inject
    private TrgovinaZrno trgovinaZrno;

    @Inject
    private PrimerjalnikZrno primerjalnikZrno;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeProduktovZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() { log.info("Deinicializacija zrna " + UpravljanjeProduktovZrno.class.getSimpleName()); }

    @Transactional
    public Kosarica ustvariKosarico(KosaricaDto kosaricaDto) {
        Uporabnik uporabnik = kosaricaDto.getUporabnik();
        if (uporabnik == null) {
            log.info("Ne morem ustvariti kosarice, ker uporabnik ne obstaja.");
            return null;
        }

        int popust = kosaricaDto.getPopust();
        int postnina = kosaricaDto.getPostnina();
        Kosarica kosarica = kosaricaZrno.dodajKosarico(0, popust, postnina);

        kosarica.setUporabnik(uporabnik);
        uporabnik.getKosarice().add(kosarica);

        Trgovina trgovina = kosaricaDto.getTrgovina();
        kosarica.setTrgovina(trgovina);
        return kosarica;
    }

    @Transactional
    public boolean izbrisiKosarico(KosaricaDto k) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(k.getKosaricaId());
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(kosarica.getUporabnik().getId());
        if (kosarica == null) {
            log.info("Ne morem izbrisati kosarice, ker kosarica ne obstaja.");
            return false;
        }
        if (uporabnik == null) {
            log.info("Izbrisana kosarica brez uporabnika.");
        }

        uporabnik.getKosarice().remove(kosarica);
        kosaricaZrno.odstraniKosarico(kosarica.getId());
        return true;
    }

    @Transactional
    public Produkt ustvariProdukt(ProduktDto produktDto) {
        Trgovina trgovina = produktDto.getTrgovina();
        if (trgovina == null) {
            log.info("Ne morem ustvariti kosarice, ker trgovina ne obstaja.");
            return null;
        }

        String ime = produktDto.getIme();
        int cena = produktDto.getCena();
        String opis = produktDto.getOpis();

        Query q = em.createNamedQuery("Produkt.getByNameAndStore");
        q.setParameter("ime", ime);
        q.setParameter("trgovina", trgovina);

        if(!q.getResultList().isEmpty()) {
            log.info("Produkt v trgovini ze obstaja.");
            return (Produkt) q.getResultList().get(0);
        }

        Produkt produkt = produktZrno.dodajProdukt(ime, cena, opis);

        produkt.setTrgovina(trgovina);
        trgovina.getProdukti().add(produkt);

        return produkt;
    }

    @Transactional
    public boolean izbrisiProdukt(ProduktDto produktDto) {
        Produkt produkt = produktZrno.pridobiProdukt(produktDto.getProduktId());
        Trgovina trgovina = produkt.getTrgovina();
        if (produkt == null) {
            log.info("Ne morem izbrisati produkta, ker ne obstaja.");
            return false;
        }
        if (trgovina == null) {
            log.info("Izbrisana produkt brez trgovine.");
        }

        trgovina.getProdukti().remove(produkt);
        produktZrno.odstraniProdukt(produkt.getId());
        return true;
    }

    @Transactional
    public List<Produkt> dodajProduktVKosarico(KosaricaDto k, ProduktDto p) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(k.getKosaricaId());
        Produkt produkt = produktZrno.pridobiProdukt(p.getProduktId());
        if (kosarica == null) {
            log.info("Ne morem dodati novega produkta, ker kosarica ne obstaja.");
            return null;
        }

        if (produkt == null) {
            log.info("Ne morem dodati novega produkta, ker ne obstaja.");
            return null;
        }

        kosarica.getProdukti().add(produkt);
        produkt.getKosarice().add(kosarica);
        int n = kosarica.getKolicinaProduktov();
        kosarica.setKolicinaProduktov(n + 1);

        return kosarica.getProdukti();
    }

    @Transactional
    public List<Produkt> odstraniProduktIzKosarice(KosaricaDto k, ProduktDto p) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(k.getKosaricaId());
        Produkt produkt = produktZrno.pridobiProdukt(p.getProduktId());
        if (kosarica == null) {
            log.info("Ne morem odstraniti produkta, ker kosarica ne obstaja.");
            return null;
        }
        if (produkt == null) {
            log.info("Ne morem odstraniti produkta, ker produkt ne obstaja.");
            return null;
        }

        kosarica.getProdukti().remove(produkt);
        produkt.getKosarice().remove(kosarica);
        int n = kosarica.getKolicinaProduktov();
        kosarica.setKolicinaProduktov(n - 1);
        return kosarica.getProdukti();
    }
}
