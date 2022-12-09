package si.fri.prpo.skupina3.api.v1.viri;
import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.storitve.zrna.KosaricaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped
@Path("kosarice")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KosaricaVir {

    @Inject
    private KosaricaZrno kosaricaZrno;

    @GET
    public Response izpisProduktov() {
        List<Kosarica> kosarice = kosaricaZrno.pridobiKosarice();
        return Response.status(Response.Status.OK).entity(kosarice).build();
    }

    @GET
    @Path("{id}")
    public Response izpisProdukta(@PathParam("id") Integer id) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(id);
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }
}
