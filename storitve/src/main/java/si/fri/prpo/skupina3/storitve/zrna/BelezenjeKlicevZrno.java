package si.fri.prpo.skupina3.storitve.zrna;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {
    private Logger log = Logger.getLogger(UpravljanjeUporabnikovZrno.class.getName());
    private HashMap<String, Integer> stKlicev;
    public BelezenjeKlicevZrno() {
        stKlicev = new HashMap<>();
    }
    public int pridobiStKlicev(String imeMetode) {
        return stKlicev.get(imeMetode);
    }
    public boolean vsebuje(String key) {
        if (stKlicev.containsKey(key))
            return true;
        else return false;
    }
    public void nastaviStKlicev(String imeMetode, int st) {
        log.info("Metoda: " + imeMetode + " je bila klicana: " + st + "-krat.");
        stKlicev.put(imeMetode, st);
    }
}
