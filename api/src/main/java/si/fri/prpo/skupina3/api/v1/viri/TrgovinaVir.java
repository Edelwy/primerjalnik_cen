package si.fri.prpo.skupina3.api.v1.viri;
import si.fri.prpo.skupina3.entitete.Trgovina;
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
    public Response izpisProduktov() {
        List<Trgovina> trgovine = trgovinaZrno.pridobiTrgovine();
        return Response.status(Response.Status.OK).entity(trgovine).build();
    }

    @GET
    @Path("{id}")
    public Response izpisProdukta(@PathParam("id") Integer id) {
        Trgovina trgovina = trgovinaZrno.pridobiTrgovino(id);
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }
}
