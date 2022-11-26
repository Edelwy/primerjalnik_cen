package si.fri.prpo.skupina3.api.servleti;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina3.entitete.*;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.zrna.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private ProduktZrno produktZrno;

    @Inject
    private KosaricaZrno kosaricaZrno;

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private PrimerjalnikZrno primerjalnikZrno;

    @Inject
    private TrgovinaZrno trgovinaZrno;

    @Inject
    private ZrnoApplicationScoped zrnoApplicationScoped;

    @Inject
    private ZrnoRequestScoped zrnoRequestScoped;

    @Inject
    private UpravljanjeProduktovZrno upravljanjeProduktovZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        //prvi izpis @RequestScoped in @ApplicationScoped
        zrnoRequestScoped.izpisId();
        zrnoApplicationScoped.izpisId();

        //branje konfiguracije
        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("Izpis generiran v mikrostoritvi " + s + "\n"));

        List<Produkt> produkti = produktZrno.pridobiProdukte();
        //izpis se na konzolo:
        System.out.println(Arrays.toString(produkti.toArray()));

        //klasicen izpis
        writer.append("Seznam obstojecih produktov:\n");
        produkti.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //zapis produktov cenejsih od 40 evrov
        produkti = produktZrno.getCheaper();
        writer.append("\nSeznam produktov pod 40 evri:\n");
        produkti.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //izpis z Criteria API
        produkti = produktZrno.getProduktCriteriaAPI();
        writer.append("\nSeznam produktov izpisan z Criteria API:\n");
        produkti.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //izpis metod iz UpravljanjeProduktov
        List<Kosarica> kosarice = kosaricaZrno.pridobiKosarice();
        List<Uporabnik> uporabniki = uporabnikZrno.pridobiUporabnike();
        List<Primerjalnik> primerjalniki = primerjalnikZrno.pridobiPrimerjalnike();
        List<Trgovina> trgovine = trgovinaZrno.pridobiTrgovine();
        writer.append("\nSeznam kosaric:\n");
        kosarice.stream().forEach(u -> writer.append(u.toString() + "\n"));
        writer.append("\nSeznam uporabnikov:\n");
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));
        writer.append("\nSeznam trgovin:\n");
        trgovine.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo novega uporabnika 'Ana Blazic'
        Uporabnik novUporabnik = uporabnikZrno.dodajUporabnika(new Uporabnik());
        novUporabnik.setId(3);
        novUporabnik.setIme("Ana");
        novUporabnik.setPriimek("Blazic");
        novUporabnik.setUsername("anablazic123");
        uporabniki.add(novUporabnik);
        writer.append("\nSeznam uporabnikov - dodali smo novega uporabnika 'Ana Blazic':\n");
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo novo kosarico
        Kosarica novaKosarica = kosaricaZrno.dodajKosarico(new Kosarica());
        novaKosarica.setId(2);
        novaKosarica.setUporabnik(novUporabnik);
        novaKosarica.setPopust(25);
        novaKosarica.setPostnina(9);
        novaKosarica.setKolicinaProduktov(1);
        kosarice.add(novaKosarica);
        writer.append("\nSeznam kosaric - dodali smo kosrico za Ano:\n");
        kosarice.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo novo trgovino
        Trgovina novaTrgovina = trgovinaZrno.dodajTrgovino(new Trgovina());
        novaTrgovina.setId(2);
        novaTrgovina.setIme("Primark");
        trgovine.add(novaTrgovina);
        writer.append("\nSeznam trgovin - dodali smo trgovino Primark:\n");
        trgovine.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo kosarico z uporabo UpravljanjeProduktovZrno za novega uporabnika "Lucija"
        Kosarica ustvari = upravljanjeProduktovZrno.ustvariKosarico(new KosaricaDto(1, 1));
        Uporabnik lucija = new Uporabnik();
        lucija.setIme("Lucija");
        lucija.setPriimek("Malensek");
        lucija.setUsername("lucijamal");
        ustvari.setUporabnik(lucija);
        ustvari.setKolicinaProduktov(12);
        ustvari.setPopust(23);
        ustvari.setPostnina(2);
        kosarice.add(ustvari);
        writer.append("\nSeznam kosaric - dodali smo kosrico za Lucijo z uporabo UpravljanjeProduktovZrno:\n");
        kosarice.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //izbrisemo kosarico od Lucije
        upravljanjeProduktovZrno.izbrisiKosarico(1, 1);
        kosarice.remove(ustvari);
        writer.append("\nSeznam kosaric - odstranili smo kosrico za Lucijo z uporabo UpravljanjeProduktovZrno:\n");
        kosarice.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //drugi izpis @RequestScoped in @ApplicationScoped
        zrnoRequestScoped.izpisId();
        zrnoApplicationScoped.izpisId();
    }
}
