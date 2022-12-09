package si.fri.prpo.skupina3.api.v1.viri;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.zrna.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped
@Path("uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UporabnikVir {

    @Inject
    private UporabnikZrno uporabnikZrno;

    @GET
    public Response izpisUporabnikov() {
        List<Uporabnik> uporabniki = uporabnikZrno.pridobiUporabnike();
        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @GET
    @Path("{id}")
    public Response izpisProdukta(@PathParam("id") Integer id) {
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(id);
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }
}
