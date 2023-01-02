package si.fri.prpo.skupina3.api.v1.viri;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import si.fri.prpo.skupina3.api.v1.mappers.NeveljavenUporabnikMapper;
import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.dtos.UporabnikDto;
import si.fri.prpo.skupina3.storitve.zrna.UporabnikZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeUporabnikovZrno;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@ApplicationScoped
@Path("uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, DELETE")
public class UporabnikVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;

    @Operation(description = "Vrne seznam uporabnikov.", summary = "Seznam uporabnikov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam uporabnikov",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Stevilo vrnjnih uporabnikov")}
            )
    })
    @GET
    public Response pridobiUporabnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikZrno.pridobiUporabnikeCount(query);

        return Response
                .ok(uporabnikZrno.pridobiUporabnike(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();
    }
    public Response izpisUporabnikov() {
        List<Uporabnik> uporabniki = uporabnikZrno.pridobiUporabnike();
        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @Operation(description = "Vrne podrobnosti uporabnika.", summary = "Podrobnosti uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti uporabnika",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            )})
    @GET
    @Path("{id}")
    public Response izpisUporabnika(@Parameter(
            description = "Identifikator uporabnika za brisanje.",
            required = true) @PathParam("id") Integer id) {
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(id);
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @Operation(summary = "Izbriše uporabnika.", description = "Izbriši uporabnika")
    @Parameters({
            @Parameter(name = "id", description = "ID uporabnika", required = true)
    })
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(description = "Napaka pri brisanju uporabnika.",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = NeveljavenUporabnikMapper.class))
            )
    })
    @DELETE
    @Path("{id}")
    public Response izbrisiUporabnika(@PathParam("id") Integer id) {
        Uporabnik u = uporabnikZrno.pridobiUporabnika(id);
        if(u == null)  return Response.status(Response.Status.NOT_FOUND).build();
        UporabnikDto uporabnik = new UporabnikDto(u);
        upravljanjeUporabnikovZrno.izbrisiUporabnika(uporabnik);
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @Operation(summary = "Dodaj novega uporabnika", description = "Dodaj novega uporabnika v bazo podatkov.")
    @APIResponses({
            @APIResponse(description = "Ustvarjen uporabnik",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            ),
            @APIResponse(description = "Napaka pri ustvarjanju uporabnika",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = NeveljavenUporabnikMapper.class)))
    })

    @POST
    public Response ustvariUporabnika(@RequestBody(
            description = "DTO objekt za dodajanje uporabnikov.",
            required = true,
            content = @Content(schema = @Schema(implementation = Uporabnik.class))) UporabnikDto uporabnik) {
        Uporabnik novUporabnik = upravljanjeUporabnikovZrno.ustvariUporabnika(uporabnik);
        return Response.status(Response.Status.OK).entity(novUporabnik).build();
    }

    @Operation(summary = "Spremeni uporabnika.", description = "Spremeni uporabnika v podatkovni bazi")
    @Parameters({
            @Parameter(name = "id", description = "ID uporabnika", required = true)
    })
    @APIResponses({
            @APIResponse(description = "Popravljen uporabnik",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            )
    })
    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(@RequestBody(
            description = "DTO objekt za spreminjanje uporabnikov.",
            required = true,
            content = @Content(schema = @Schema(implementation = Uporabnik.class))) @PathParam("id") Integer id) {
        if(!uporabnikZrno.posodobiUporabnika(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }
}
