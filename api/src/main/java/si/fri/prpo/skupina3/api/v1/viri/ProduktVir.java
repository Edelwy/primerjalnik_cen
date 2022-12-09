package si.fri.prpo.skupina3.api.v1.viri;

import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.zrna.ProduktZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeProduktovZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeUporabnikovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped
@Path("produkti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProduktVir {

    /*@Inject
    private ProduktZrno produktZrno;

    @Inject
    private UpravljanjeProduktovZrno upravljalecProduktovZrno;

    @GET
    public Response izpisProduktov() {
        List<Produkt> produkti = produktZrno.pridobiProdukte();
        return Response.status(Response.Status.OK).entity(produkti).build();
    }

    @GET
    @Path("{id}")
    public Response izpisProdukta(@PathParam("id") Integer id) {
        Produkt produkt = produktZrno.pridobiProdukt(id);
        if(produkt == null)  return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(produkt).build();
    }

    @POST
    public Response createProduct(ProduktDto produkt, TrgovinaDto trgovina) {

        Produkt novProdukt = upravljalecProduktovZrno.ustvariProdukt(produkt, trgovina);
        return Response.status(Response.Status.OK).entity(produkt).build();
    }

    @DELETE
    @Path("{id}")
    public Response izbrisiProdukt(@PathParam("id") Integer id) {
        Produkt p = produktZrno.pridobiProdukt(id);
        if(p == null)  return Response.status(Response.Status.NOT_FOUND).build();
        ProduktDto produkt = new ProduktDto(p);
        upravljalecProduktovZrno.izbrisiProdukt(produkt);
        return Response.status(Response.Status.OK).entity(produkt).build();
    }*/
}
