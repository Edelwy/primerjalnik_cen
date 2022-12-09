package si.fri.prpo.skupina3.api.v1.viri;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Trgovina;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.zrna.TrgovinaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped
@Path("trgovine")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrgovinaVir {

    @Inject
    private TrgovinaZrno trgovinaZrno;

    @GET
    public Response izpisTrgovin() {
        List<Trgovina> trgovine = trgovinaZrno.pridobiTrgovine();
        return Response.status(Response.Status.OK).entity(trgovine).build();
    }

    @GET
    @Path("{id}")
    public Response izpisTrgovine(@PathParam("id") Integer id) {
        Trgovina trgovina = trgovinaZrno.pridobiTrgovino(id);
        if(trgovina == null)  return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }

    @POST
    public Response ustvariTrgovino(TrgovinaDto trgovina) {
        Trgovina novaTrgovina = trgovinaZrno.dodajTrgovino(trgovina.getIme());
        return Response.status(Response.Status.OK).entity(novaTrgovina).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiTrgovino(@PathParam("id") Integer id) {
        if(!trgovinaZrno.posodobiTrgovino(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response izbrisiTrgovino(@PathParam("id") Integer id) {
        Trgovina trgovina = trgovinaZrno.pridobiTrgovino(id);
        if(trgovina == null)  return Response.status(Response.Status.NOT_FOUND).build();
        trgovinaZrno.odstraniTrgovino(id);
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }
}
