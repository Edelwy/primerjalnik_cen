package si.fri.prpo.skupina3.api.v1.viri;
import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.dtos.UporabnikDto;
import si.fri.prpo.skupina3.storitve.zrna.KosaricaZrno;
import si.fri.prpo.skupina3.storitve.zrna.UporabnikZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeProduktovZrno;

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

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private UpravljanjeProduktovZrno upravljalecProduktovZrno;

    @GET
    public Response izpisKosaric() {
        List<Kosarica> kosarice = kosaricaZrno.pridobiKosarice();
        return Response.status(Response.Status.OK).entity(kosarice).build();
    }

    @GET
    @Path("{id}")
    public Response izpisKosarice(@PathParam("id") Integer id) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(id);
        if(kosarica == null)  return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }

    @POST
    @Path("{imeTrgovine}/{idUporabnika}")
    public Response ustvariKosarico(KosaricaDto kosarica,
                                   @PathParam("imeTrgovine") String imeTrgovine,
                                   @PathParam("idUporabnika") Integer id) {

        TrgovinaDto trgovina = new TrgovinaDto(imeTrgovine);
        Uporabnik u = uporabnikZrno.pridobiUporabnika(id);
        if(u == null)  return Response.status(Response.Status.NOT_FOUND).build();

        UporabnikDto uporabnik = new UporabnikDto(u);
        Kosarica novaKosarica = upravljalecProduktovZrno.ustvariKosarico(kosarica, uporabnik, trgovina);
        return Response.status(Response.Status.OK).entity(novaKosarica).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiKosarico(@PathParam("id") Integer id) {
        if(!kosaricaZrno.posodobiKosarico(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response izbrisiKosarico(@PathParam("id") Integer id) {
        Kosarica k = kosaricaZrno.pridobiKosarico(id);
        if(k == null)  return Response.status(Response.Status.NOT_FOUND).build();

        KosaricaDto kosarica = new KosaricaDto(k);
        upravljalecProduktovZrno.izbrisiKosarico(kosarica);
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }
}
