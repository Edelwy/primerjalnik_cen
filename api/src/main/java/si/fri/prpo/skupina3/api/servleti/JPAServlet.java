package si.fri.prpo.skupina3.api.servleti;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina3.entitete.*;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.dtos.UporabnikDto;
import si.fri.prpo.skupina3.storitve.interceptorji.BelezenjeKlicevInterceptor;
import si.fri.prpo.skupina3.storitve.odjemalci.PriceAnalyticsApiOdjemalec;
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

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        //prvi izpis @RequestScoped in @ApplicationScoped
        zrnoRequestScoped.izpisId();
        zrnoApplicationScoped.izpisId();

        //branje konfiguracije
        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("Izpis generiran v mikrostoritvi " + s + "\n"));

        //IZPIS ZA 5 VAJE:
        List<Kosarica> kosarice = kosaricaZrno.pridobiKosarice();
        List<Uporabnik> uporabniki = uporabnikZrno.pridobiUporabnike();
        List<Trgovina> trgovine = trgovinaZrno.pridobiTrgovine();
        List<Produkt> produkti = produktZrno.pridobiProdukte();

        writer.append("\nSeznam kosaric:\n");
        kosarice.stream().forEach(u -> writer.append(u.toString() + "\n"));
        writer.append("\nSeznam uporabnikov:\n");
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));
        writer.append("\nSeznam trgovin:\n");
        trgovine.stream().forEach(u -> writer.append(u.toString() + "\n"));
        writer.append("Seznam produktov:\n");
        produkti.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo novega uporabnika 'Ana Blazic'
        UporabnikDto uporabnik = new UporabnikDto("Ana", "Blazic","anablazic123");
        Uporabnik novUporabnik = upravljanjeUporabnikovZrno.ustvariUporabnika(uporabnik);
        uporabniki = uporabnikZrno.pridobiUporabnike();
        writer.append("\nSeznam uporabnikov - dodali smo novega uporabnika 'Ana Blazic':\n");
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo novo trgovino
        Trgovina novaTrgovina = trgovinaZrno.dodajTrgovino("Primark");
        TrgovinaDto trgovina = new TrgovinaDto(novaTrgovina);
        writer.append("\nSeznam trgovin - dodali smo trgovino Primark:\n");
        trgovine = trgovinaZrno.pridobiTrgovine();
        trgovine.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo novo kosarico
        KosaricaDto kosarica = new KosaricaDto(25, 9);
        Kosarica novaKosarica = upravljanjeProduktovZrno.ustvariKosarico(kosarica, uporabnik, trgovina);
        kosarice = kosaricaZrno.pridobiKosarice();
        writer.append("\nSeznam kosaric - dodali smo kosarico za Ano:\n");
        kosarice.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //ustvarimo nov produkt
        ProduktDto produkt = new ProduktDto("Rdec sal", 10, "Topel sal za zimo.");
        Produkt novProdukt = upravljanjeProduktovZrno.ustvariProdukt(produkt, trgovina);
        produkti = produktZrno.pridobiProdukte();
        writer.append("\nSeznam produktov - dodali smo rdec sal:\n");
        produkti.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //produkt dodamo v kosarico
        produkt.setProduktId(novProdukt.getId());
        kosarica.setKosaricaId(novaKosarica.getId());
        List<Produkt> produktiKosarice2 = upravljanjeProduktovZrno.dodajProduktVKosarico(kosarica, produkt);
        writer.append("\nSeznam produktov v kosarici od Ane:\n");
        produktiKosarice2.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //izbrisemo novo kosarico
        upravljanjeProduktovZrno.izbrisiKosarico(kosarica);
        kosarice = kosaricaZrno.pridobiKosarice();
        writer.append("\nSeznam kosaric - izbrisali smo kosrico za Ano:\n");
        kosarice.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //izpis primerjalnikov
        List<Primerjalnik> primerjalniki = primerjalnikZrno.pridobiPrimerjalnike();
        writer.append("\nSeznam primerjalnikov:\n");
        primerjalniki.stream().forEach(u -> writer.append(u.toString() + "\n"));

        //drugi izpis @RequestScoped in @ApplicationScoped
        zrnoRequestScoped.izpisId();
        zrnoApplicationScoped.izpisId();

        //ustvarimo novo trgovino
        Trgovina testTrgovina = trgovinaZrno.dodajTrgovino("ebay");
        TrgovinaDto testTrgovinaDto = new TrgovinaDto(testTrgovina);

        //ustvarimo nov produkt
        ProduktDto testProduktDto = new ProduktDto("iphone", 500, "Nov telefon znamke Apple");
        Produkt testProdukt = upravljanjeProduktovZrno.ustvariProdukt(testProduktDto, testTrgovinaDto);

    }
}
