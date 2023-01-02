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
import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Primerjalnik;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.zrna.PrimerjalnikZrno;
import si.fri.prpo.skupina3.storitve.zrna.ProduktZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@ApplicationScoped
@Path("primerjalniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, DELETE")
public class PrimerjalnikVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private PrimerjalnikZrno primerjalnikZrno;

    @Operation(description = "Vrne seznam primerjalnikov.", summary = "Seznam primerjalnikov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam primerjalnikov",
                    content = @Content(schema = @Schema(implementation = Primerjalnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjnih primerjalnikov")}
            )
    })
    @GET
    public Response pridobiPrimerjalnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long primerjalnikiCount = primerjalnikZrno.pridobiPrimerjalnikeCount(query);

        return Response
                .ok(primerjalnikZrno.pridobiPrimerjalnike(query))
                .header("X-Total-Count", primerjalnikiCount)
                .build();
    }
    public Response izpisPrimerjalnikov() {
        List<Primerjalnik> primerjalniki = primerjalnikZrno.pridobiPrimerjalnike();
        return Response.status(Response.Status.OK).entity(primerjalniki).build();
    }

    @Operation(description = "Vrne podrobnosti primerjalnika.", summary = "Podrobnosti primerjalnika")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti primerjalnika",
                    content = @Content(schema = @Schema(implementation = Primerjalnik.class))
            )})
    @GET
    @Path("{id}")
    public Response izpisPrimerjalnika(@Parameter(
            description = "Identifikator primerjalnika za brisanje.",
            required = true) @PathParam("id") Integer id) {
        Primerjalnik primerjalnik = primerjalnikZrno.pridobiPrimerjalnik(id);
        return Response.status(Response.Status.OK).entity(primerjalnik).build();
    }

    @Operation(summary = "Spremeni primerjalnik.", description = "Spremeni primerjalnik v podatkovni bazi")
    @Parameters({
            @Parameter(name = "id", description = "ID primerjalnika", required = true)
    })
    @APIResponses({
            @APIResponse(description = "Popravljen primerjalnik",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Primerjalnik.class))
            )
    })
    @PUT
    @Path("{id}")
    public Response posodobiPrimerjalnik(@RequestBody(
            description = "DTO objekt za spreminjanje primerjalnikov.",
            required = true,
            content = @Content(schema = @Schema(implementation = Primerjalnik.class))) @PathParam("id") Integer id) {
        if(!primerjalnikZrno.posodobiPrimerjalnik(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    //primerjalnika ne moramo ustvarjati ali brisati: to se zgodi ko ustvarimo uporabnika!!!
}
