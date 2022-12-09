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
@Path("produkti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProduktVir {

    @Inject
    private ProduktZrno produktZrno;

    @GET
    public Response izpisProduktov() {
        List<Produkt> produkti = produktZrno.pridobiProdukte();
        return Response.status(Response.Status.OK).entity(produkti).build();
    }

    @GET
    @Path("{id}")
    public Response izpisProdukta(@PathParam("id") Integer id) {
        Produkt produkt = produktZrno.pridobiProdukt(id);
        return Response.status(Response.Status.OK).entity(produkt).build();
    }

    /*@POST
    public Response createProduct(@Param("ime") String ime,
                                  @FormDataParam("cena") int cena,
                                  @FormDataParam("opis") String opis) {

        Produkt produkt = produktZrno.dodajProdukt(ime,cena,opis);
        return Response.status(Response.Status.OK).entity(produkt).build();
    }*/
}
