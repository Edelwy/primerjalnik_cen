package si.fri.prpo.skupina3.api.servleti;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.storitve.zrna.ProduktZrno;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        //branje konfiguracije
        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("Izpis generiran v mikrostoritvi " + s + "\n"));

        List<Produkt> produkti = produktZrno.getProdukt();
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

    }
}
