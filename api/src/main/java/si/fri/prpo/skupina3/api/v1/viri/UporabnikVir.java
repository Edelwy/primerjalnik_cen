package si.fri.prpo.skupina3.api.v1.viri;
import si.fri.prpo.skupina3.entitete.Trgovina;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.dtos.UporabnikDto;
import si.fri.prpo.skupina3.storitve.zrna.UporabnikZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeUporabnikovZrno;

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

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;

    @GET
    public Response izpisUporabnikov() {
        List<Uporabnik> uporabniki = uporabnikZrno.pridobiUporabnike();
        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @GET
    @Path("{id}")
    public Response izpisUporabnika(@PathParam("id") Integer id) {
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(id);
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @POST
    public Response ustvariUporabnika(UporabnikDto uporabnik) {
        Uporabnik novUporabnik = upravljanjeUporabnikovZrno.ustvariUporabnika(uporabnik);
        return Response.status(Response.Status.OK).entity(novUporabnik).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(@PathParam("id") Integer id) {
        if(!uporabnikZrno.posodobiUporabnika(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response izbrisiUporabnika(@PathParam("id") Integer id) {
        Uporabnik u = uporabnikZrno.pridobiUporabnika(id);
        if(u == null)  return Response.status(Response.Status.NOT_FOUND).build();
        UporabnikDto uporabnik = new UporabnikDto(u);
        upravljanjeUporabnikovZrno.izbrisiUporabnika(uporabnik);
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }
}
