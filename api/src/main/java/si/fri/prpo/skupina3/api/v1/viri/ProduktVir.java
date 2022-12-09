package si.fri.prpo.skupina3.api.v1.viri;

import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.storitve.zrna.ProduktZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped
@Path("produkt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProduktVir {

    @Inject
    private ProduktZrno ProduktZrno;

    @GET
    public Response izpisProduktov() {
        List<Produkt> produkti = ProduktZrno.pridobiProdukte();
        return Response.status(Response.Status.OK).entity(produkti).build();
    }

    @GET()
    @Path("{id}")
    public Response izpisProdukta(@PathParam("id") Integer id) {
        Produkt produkt = ProduktZrno.pridobiProdukt(id);
        return Response.status(Response.Status.OK).entity(produkt).build();
    }
}
