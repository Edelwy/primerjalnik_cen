package si.fri.prpo.skupina3.api.v1.viri;

import si.fri.prpo.skupina3.entitete.Primerjalnik;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.storitve.zrna.PrimerjalnikZrno;
import si.fri.prpo.skupina3.storitve.zrna.ProduktZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped
@Path("primerjalniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrimerjalnikVir {

    @Inject
    private PrimerjalnikZrno primerjalnikZrno;

    @GET
    public Response izpisPrimerjalnikov() {
        List<Primerjalnik> primerjalniki = primerjalnikZrno.pridobiPrimerjalnike();
        return Response.status(Response.Status.OK).entity(primerjalniki).build();
    }

    @GET
    @Path("{id}")
    public Response izpisPrimerjalnika(@PathParam("id") Integer id) {
        Primerjalnik primerjalnik = primerjalnikZrno.pridobiPrimerjalnik(id);
        return Response.status(Response.Status.OK).entity(primerjalnik).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiPrimerjalnik(@PathParam("id") Integer id) {
        if(!primerjalnikZrno.posodobiPrimerjalnik(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    //primerjalnika ne moramo ustvarjati ali brisati: to se zgodi ko ustvarimo uporabnika!!!
}
