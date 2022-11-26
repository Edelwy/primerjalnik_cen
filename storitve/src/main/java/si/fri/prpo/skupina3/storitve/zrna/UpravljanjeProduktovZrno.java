package si.fri.prpo.skupina3.storitve.zrna;

import si.fri.prpo.skupina3.entitete.*;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeProduktovZrno {
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
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(kosaricaDto.getUporabnikId());
        if (uporabnik == null) {
            log.info("Ne morem dodati produkta, ker uporabnik ne obstaja.");
            return null;
        }

        Kosarica kosarica = new Kosarica();
        kosarica.setUporabnik(uporabnik);
        uporabnik.getKosarice().add(kosarica);
        kosarica.setId(kosaricaDto.getKosaricaId());
        kosarica.setPopust(kosaricaDto.getPopust());
        kosarica.setPostnina(kosaricaDto.getPostnina());
        kosarica.setPrimerjalnik(kosaricaDto.getPrimerjalnik());
        kosarica.setTrgovina(kosaricaDto.getTrgovina());

        return kosaricaZrno.dodajKosarico(kosarica);
    }

    @Transactional
    public void izbrisiKosarico(int kosaricaId, int uporabnikId) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(kosaricaId);
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(uporabnikId);
        if (kosarica == null) {
            log.info("Ne morem izbrisati kosarice, ker kosarica ne obstaja.");
            return;
        }
        if (uporabnik == null) {
            log.info("Ne morem izbrisati kosarice, ker uporabnik ne obstaja.");
            return;
        }

        uporabnik.getKosarice().remove(kosarica);
        kosaricaZrno.odstraniKosarico(kosaricaId);
        kosaricaZrno.pridobiKosarice().remove(kosarica);
    }

    @Transactional
    public Produkt dodajProduktVKosarico(int kosaricaId, ProduktDto produktDto) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(kosaricaId);
        if (kosarica == null) {
            log.info("Ne morem dodati novega progukta, ker kosarica ne obstaja.");
            return null;
        }

        Produkt produkt = new Produkt();
        produkt.setId(produktDto.getProduktId());
        produkt.setIme(produktDto.getIme());
        produkt.setOpis(produktDto.getOpis());
        produkt.setCena(produktDto.getCena());
        produkt.setTrgovina(produktDto.getTrgovina());
        kosarica.getProdukti().add(produkt);

        return produktZrno.dodajProdukt(produkt);
    }

    @Transactional
    public boolean odstraniProduktIzKosarice(int kosaricaId, int produktId) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(kosaricaId);
        Produkt produkt = produktZrno.pridobiProdukt(produktId);
        if (kosarica == null) {
            log.info("Ne morem odstraniti produkta, ker kosarica ne obstaja.");
            return false;
        }
        if (produkt == null) {
            log.info("Ne morem odstraniti produkta, ker produkt ne obstaja.");
            return false;
        }

        kosarica.getProdukti().remove(produkt);

        return produktZrno.odstraniProdukt(produktId);
    }

    @Transactional
    public Kosarica dodajKosaricoVTrgovino(int trgovinaId, KosaricaDto kosaricaDto) {
        Trgovina trgovina = trgovinaZrno.pridobiTrgovino(trgovinaId);
        if (trgovina == null) {
            log.info("Ne morem dodati kosarice, ker trgovina ne obstaja.");
            return null;
        }

        Kosarica kosarica = new Kosarica();
        kosarica.setId(kosaricaDto.getKosaricaId());
        kosarica.setUporabnik(kosaricaDto.getUporabnik());
        kosarica.setPrimerjalnik(kosaricaDto.getPrimerjalnik());
        kosarica.setPopust(kosaricaDto.getPopust());
        kosarica.setPostnina(kosaricaDto.getPostnina());
        trgovina.getKosarice().add(kosarica);

        return kosaricaZrno.dodajKosarico(kosarica);
    }

    @Transactional
    public boolean odstraniKosaricoIzTrgovine(int trgovinaId, int kosaricaId) {
        Trgovina trgovina = trgovinaZrno.pridobiTrgovino(trgovinaId);
        if (trgovina == null) {
            log.info("Ne morem odstranniti kosarice, ker trgovina ne obstaja.");
            return false;
        }
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(kosaricaId);
        if (kosarica == null) {
            log.info("Ne morem odstranniti kosarice, ker kosarica ne obstaja.");
            return false;
        }

        trgovina.getKosarice().remove(kosarica);
        return kosaricaZrno.odstraniKosarico(kosaricaId);
    }

        @Transactional
        public Kosarica dodajKosaricoVPrimerjalnik(int primerjalnikId, KosaricaDto kosaricaDto) {
            Primerjalnik primerjalnik = primerjalnikZrno.pridobiPrimerjalnik(primerjalnikId);
            if (primerjalnik == null) {
                log.info("Ne morem dodati kosarice, ker primerjalnik ne obstaja.");
                return null;
        }
        Kosarica kosarica = new Kosarica();
        kosarica.setId(kosaricaDto.getKosaricaId());
        kosarica.setUporabnik(kosaricaDto.getUporabnik());
        kosarica.setPrimerjalnik(kosaricaDto.getPrimerjalnik());
        kosarica.setPopust(kosaricaDto.getPopust());
        kosarica.setPostnina(kosaricaDto.getPostnina());
        primerjalnik.getKosarice().add(kosarica);

        return kosaricaZrno.dodajKosarico(kosarica);
    }

    @Transactional
    public boolean odstraniKosaricoIzPrimerjalnika(int primerjalnikId, int kosaricaId) {
        Primerjalnik primerjalnik = primerjalnikZrno.pridobiPrimerjalnik(primerjalnikId);
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(kosaricaId);
        if (primerjalnik == null) {
            log.info("Ne morem odstraniti kosarice, ker primerjalnik ne obstaja.");
            return false;
        }
        if (kosarica == null) {
            log.info("Ne morem odstraniti kosarice, ker kosarica ne obstaja.");
            return false;
        }

        primerjalnik.getKosarice().remove(kosarica);

        return kosaricaZrno.odstraniKosarico(kosaricaId);
    }

}
