package si.fri.prpo.skupina3.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina3.api.v1.mappers.NeveljavenProduktMapper;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Trgovina;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.zrna.ProduktZrno;
import si.fri.prpo.skupina3.storitve.zrna.TrgovinaZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeProduktovZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeUporabnikovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@ApplicationScoped
@Path("produkti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProduktVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private ProduktZrno produktZrno;

    @Inject
    private UpravljanjeProduktovZrno upravljalecProduktovZrno;

    @Operation(description = "Vrne seznam produktov.", summary = "Seznam produktov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam produktov",
                    content = @Content(schema = @Schema(implementation = Produkt.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjnih produktov")}
            )
    })
    @GET
    public Response pridobiProdukte() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long produktiCount = produktZrno.pridobiProdukteCount(query);

        return Response
                .ok(produktZrno.pridobiProdukte(query))
                .header("X-Total-Count", produktiCount)
                .build();
    }
    public Response izpisProduktov() {
        List<Produkt> produkti = produktZrno.pridobiProdukte();
        return Response.status(Response.Status.OK).entity(produkti).build();
    }

    @Operation(description = "Vrne podrobnosti produkta.", summary = "Podrobnosti produkta")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti produkta",
                    content = @Content(schema = @Schema(implementation = Produkt.class))
            )})
    @GET
    @Path("{id}")
    public Response izpisProdukta(@Parameter(
            description = "Identifikator produkta za brisanje.",
            required = true) @PathParam("id") Integer id) {
        Produkt produkt = produktZrno.pridobiProdukt(id);
        if(produkt == null)  return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(produkt).build();
    }

    //ustvarimo produkt na viru trgovine
    @Operation(summary = "Dodaj nov produkt", description = "Dodaj nov produkt v bazo podatkov.")
    @APIResponses({
            @APIResponse(description = "Ustvarjen produkt",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Produkt.class))
            ),
            @APIResponse(description = "Napaka pri ustvarjanju produkta",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = NeveljavenProduktMapper.class)))
    })
    @POST
    @Path("{trgovina}")
    public Response ustvariProdukt(@RequestBody(
            description = "DTO objekt za dodajanje produktov.",
            required = true,
            content = @Content(schema = @Schema(implementation = Produkt.class))) ProduktDto produkt, @PathParam("trgovina") String imeTrgovine) {
        TrgovinaDto trgovina = new TrgovinaDto(imeTrgovine);
        Produkt novProdukt = upravljalecProduktovZrno.ustvariProdukt(produkt, trgovina);
        return Response.status(Response.Status.OK).entity(novProdukt).build();
    }

    @Operation(summary = "Spremeni produkt.", description = "Spremeni produkt v podatkovni bazi")
    @Parameters({
            @Parameter(name = "id", description = "ID produkta", required = true)
    })
    @APIResponses({
            @APIResponse(description = "Popravljen produkt",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Produkt.class))
            )
    })
    @PUT
    @Path("{id}")
    public Response posodobiProdukt(@RequestBody(
            description = "DTO objekt za spreminjanje produktov.",
            required = true,
            content = @Content(schema = @Schema(implementation = Produkt.class))) @PathParam("id") Integer id) {
        if(!produktZrno.posodobiProdukt(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    @Operation(summary = "Izbriše produkt.", description = "Izbriši produkt")
    @Parameters({
            @Parameter(name = "id", description = "ID produkta", required = true)
    })
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(description = "Napaka pri brisanju produkta.",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = NeveljavenProduktMapper.class))
            )
    })
    @DELETE
    @Path("{id}")
    public Response izbrisiProdukt(@PathParam("id") Integer id) {
        Produkt p = produktZrno.pridobiProdukt(id);
        if(p == null)  return Response.status(Response.Status.NOT_FOUND).build();
        ProduktDto produkt = new ProduktDto(p);
        upravljalecProduktovZrno.izbrisiProdukt(produkt);
        return Response.status(Response.Status.OK).entity(produkt).build();
    }
}
