package si.fri.prpo.skupina3.storitve.zrna;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class BelezenjeKlicevZrno {
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
        System.out.println("Metoda: " + imeMetode + " je bila klicana: " + st + "-krat.");
        stKlicev.put(imeMetode, st);
    }
}
