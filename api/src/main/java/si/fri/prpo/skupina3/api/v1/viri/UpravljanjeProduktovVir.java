package si.fri.prpo.skupina3.api.v1.viri;

import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;
import si.fri.prpo.skupina3.storitve.zrna.KosaricaZrno;
import si.fri.prpo.skupina3.storitve.zrna.ProduktZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeProduktovZrno;
import com.kumuluz.ee.cors.annotations.CrossOrigin;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("dodaj")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, DELETE")
public class UpravljanjeProduktovVir {

    @Context
    protected UriInfo uriInfo;

    private Logger log = Logger.getLogger(KosaricaZrno.class.getName());

    @Inject
    private KosaricaZrno kosaricaZrno;

    @Inject
    private ProduktZrno produktZrno;

    @Inject
    private UpravljanjeProduktovZrno upravljalecProduktovZrno;

    @POST
    @Path("{idKosarice}/{idProdukta}")
    public Response dodajProduktKosarici(@PathParam("idProdukta") Integer idProdukta,
                                         @PathParam("idKosarice") Integer idKosarice) {

        Kosarica k = kosaricaZrno.pridobiKosarico(idKosarice);
        Produkt p = produktZrno.pridobiProdukt(idProdukta);
        if(k == null)  return Response.status(Response.Status.NOT_FOUND).build();
        if(p == null)  return Response.status(Response.Status.NOT_FOUND).build();

        KosaricaDto kosarica = new KosaricaDto(k);
        ProduktDto produkt = new ProduktDto(p);
        List<Produkt> produktiKosarice = upravljalecProduktovZrno.dodajProduktVKosarico(kosarica, produkt);
        return Response.status(Response.Status.OK).entity(produktiKosarice).build();
    }

    @DELETE
    @Path("{idKosarice}/{idProdukta}")
    public Response izbrisiProduktKosarici(@PathParam("idProdukta") Integer idProdukta,
                                           @PathParam("idKosarice") Integer idKosarice) {

        Kosarica k = kosaricaZrno.pridobiKosarico(idKosarice);
        Produkt p = produktZrno.pridobiProdukt(idProdukta);
        if(k == null)  return Response.status(Response.Status.NOT_FOUND).build();
        if(p == null)  return Response.status(Response.Status.NOT_FOUND).build();

        KosaricaDto kosarica = new KosaricaDto(k);
        ProduktDto produkt = new ProduktDto(p);
        List<Produkt> produktiKosarice = upravljalecProduktovZrno.odstraniProduktIzKosarice(kosarica, produkt);
        return Response.status(Response.Status.OK).entity(produktiKosarice).build();
    }
}
