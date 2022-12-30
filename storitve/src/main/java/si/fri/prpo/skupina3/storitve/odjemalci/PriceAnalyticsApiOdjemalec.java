package si.fri.prpo.skupina3.storitve.odjemalci;

import si.fri.prpo.skupina3.storitve.dtos.PriceAnalyticsDto;
import si.fri.prpo.skupina3.storitve.zrna.UporabnikZrno;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Logger;

@ApplicationScoped
public class PriceAnalyticsApiOdjemalec {

    private Client httpClient;
    private String baseUrl;

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + PriceAnalyticsApiOdjemalec.class.getSimpleName());

        httpClient = ClientBuilder.newClient();
        baseUrl = "price-analytics.p.rapidapi.com";

    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + PriceAnalyticsApiOdjemalec.class.getSimpleName());
        // zapiranje virov
    }

    public void isciProdukt(String trgovina, String drzava, String ime) {

        Response response = null;
        PriceAnalyticsDto priceAnalyticsDto = new PriceAnalyticsDto(trgovina, drzava, ime);

        try {
            response = httpClient
                    .target("https://" + baseUrl + "/search-by-term")
                    .request(MediaType.APPLICATION_JSON)
                    .header("x-rapidapi-host","price-analytics.p.rapidapi.com")
                    .header("x-rapidapi-key", "15a912feffmshcc55acad0d46266p1d7c1fjsned0fb7c5ccf8")
                    .post(Entity.entity(priceAnalyticsDto, MediaType.APPLICATION_JSON));

        } catch(WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }


        String jobId = response.readEntity(String.class).split("\"")[5];
        String test = null;
        String result = null;

        do {
            try {
                response = httpClient
                        .target("https://" + baseUrl + "/poll-job/" + jobId)
                        .request(MediaType.APPLICATION_JSON)
                        .header("x-rapidapi-host", "price-analytics.p.rapidapi.com")
                        .header("x-rapidapi-key", "15a912feffmshcc55acad0d46266p1d7c1fjsned0fb7c5ccf8")
                        .get();

            } catch (WebApplicationException | ProcessingException e) {
                log.severe(e.getMessage());
                throw new InternalServerErrorException(e);
            }


            result = response.readEntity(String.class);
            test = result.split("\"")[3];
        } while(test.equals("working"));

        String[] offers = result.split("\"");
        System.out.println(Arrays.toString(offers) + "\n");

        for(int i = 0; i < offers.length; i++) {
            if(offers[i].equals("price")) {
                System.out.println("PRICE: " + offers[i + 1].substring(1,offers[i + 1].length() - 1));
            } else if(offers[i].equals("href")) {
                System.out.println("LINK: " + offers[i + 2]);
            } else if(offers[i].equals("name")) {
                System.out.println("NAME: " + offers[i + 2] + "\n");
            }
        }
    }

}
