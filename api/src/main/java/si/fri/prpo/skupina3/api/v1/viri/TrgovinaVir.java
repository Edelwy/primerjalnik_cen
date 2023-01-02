package si.fri.prpo.skupina3.api.v1.viri;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
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
import si.fri.prpo.skupina3.api.v1.mappers.NeveljavnaTrgovinaMapper;
import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Trgovina;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.zrna.TrgovinaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@ApplicationScoped
@Path("trgovine")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, DELETE")
public class TrgovinaVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private TrgovinaZrno trgovinaZrno;

    @Operation(description = "Vrne seznam trgovin.", summary = "Seznam trgovin")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam trgovin",
                    content = @Content(schema = @Schema(implementation = Trgovina.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjnih trgovin")}
            )
    })
    @GET
    public Response pridobiTrgovine() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long trgovineCount = trgovinaZrno.pridobiTrgovinoCount(query);

        return Response
                .ok(trgovinaZrno.pridobiTrgovine(query))
                .header("X-Total-Count", trgovineCount)
                .build();
    }
    public Response izpisTrgovin() {
        List<Trgovina> trgovine = trgovinaZrno.pridobiTrgovine();
        return Response.status(Response.Status.OK).entity(trgovine).build();
    }

    @Operation(description = "Vrne podrobnosti trgovine.", summary = "Podrobnosti trgovine")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti trgovine",
                    content = @Content(schema = @Schema(implementation = Trgovina.class))
            )})
    @GET
    @Path("{id}")
    public Response izpisTrgovine(@Parameter(
            description = "Identifikator trgovine za brisanje.",
            required = true) @PathParam("id") Integer id) {
        Trgovina trgovina = trgovinaZrno.pridobiTrgovino(id);
        if(trgovina == null)  return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }

    @Operation(summary = "Dodaj novo trgovino", description = "Dodaj novo trgovino v bazo podatkov.")
    @APIResponses({
            @APIResponse(description = "Ustvarjena trgovina",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Trgovina.class))
            ),
            @APIResponse(description = "Napaka pri ustvarjanju trgovine",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = NeveljavnaTrgovinaMapper.class)))
    })
    @POST
    public Response ustvariTrgovino(@RequestBody(
            description = "DTO objekt za dodajanje trgovin.",
            required = true,
            content = @Content(schema = @Schema(implementation = Trgovina.class))) TrgovinaDto trgovina) {
        Trgovina novaTrgovina = trgovinaZrno.dodajTrgovino(trgovina.getIme());
        return Response.status(Response.Status.OK).entity(novaTrgovina).build();
    }

    @Operation(summary = "Spremeni trgovino.", description = "Spremeni trgovino v podatkovni bazi")
    @Parameters({
            @Parameter(name = "id", description = "ID trgovine", required = true)
    })
    @APIResponses({
            @APIResponse(description = "Popravljena trgovina",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Trgovina.class))
            )
    })
    @PUT
    @Path("{id}")
    public Response posodobiTrgovino(@RequestBody(
            description = "DTO objekt za spreminjanje trgovin.",
            required = true,
            content = @Content(schema = @Schema(implementation = Trgovina.class))) @PathParam("id") Integer id) {
        if(!trgovinaZrno.posodobiTrgovino(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    @Operation(summary = "Izbriše trgovino.", description = "Izbriši trgovino")
    @Parameters({
            @Parameter(name = "id", description = "ID trgovine", required = true)
    })
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(description = "Napaka pri brisanju trgovine.",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = NeveljavnaTrgovinaMapper.class))
            )
    })
    @DELETE
    @Path("{id}")
    public Response izbrisiTrgovino(@PathParam("id") Integer id) {
        Trgovina trgovina = trgovinaZrno.pridobiTrgovino(id);
        if(trgovina == null)  return Response.status(Response.Status.NOT_FOUND).build();
        trgovinaZrno.odstraniTrgovino(id);
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }
}
