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
import si.fri.prpo.skupina3.api.v1.mappers.NeveljavnaKosaricaMapper;
import si.fri.prpo.skupina3.entitete.Kosarica;
import si.fri.prpo.skupina3.entitete.Produkt;
import si.fri.prpo.skupina3.entitete.Uporabnik;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.dtos.UporabnikDto;
import si.fri.prpo.skupina3.storitve.zrna.KosaricaZrno;
import si.fri.prpo.skupina3.storitve.zrna.UporabnikZrno;
import si.fri.prpo.skupina3.storitve.zrna.UpravljanjeProduktovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@ApplicationScoped
@Path("kosarice")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, DELETE")
public class KosaricaVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private KosaricaZrno kosaricaZrno;

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private UpravljanjeProduktovZrno upravljalecProduktovZrno;

    @Operation(description = "Vrne seznam košaric.", summary = "Seznam košaric")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam košaric",
                    content = @Content(schema = @Schema(implementation = Kosarica.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjnih košaric")}
            )
    })
    @GET
    public Response pridobiKosarice() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long kosariceCount = kosaricaZrno.pridobiKosariceCount(query);

        return Response
                .ok(kosaricaZrno.pridobiKosarice(query))
                .header("X-Total-Count", kosariceCount)
                .build();
    }
    public Response izpisKosaric() {
        List<Kosarica> kosarice = kosaricaZrno.pridobiKosarice();
        return Response.status(Response.Status.OK).entity(kosarice).build();
    }

    @Operation(description = "Vrne podrobnosti košarice.", summary = "Podrobnosti košarice")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti košarice",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))
            )})
    @GET
    @Path("{id}")
    public Response izpisKosarice(@Parameter(
            description = "Identifikator košarice za brisanje.",
            required = true) @PathParam("id") Integer id) {
        Kosarica kosarica = kosaricaZrno.pridobiKosarico(id);
        if(kosarica == null)  return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }

    @Operation(summary = "Dodaj novo košarico", description = "Dodaj novo košarico v bazo podatkov.")
    @APIResponses({
            @APIResponse(description = "Ustvarjena košarica",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))
            ),
            @APIResponse(description = "Napaka pri ustvarjanju košarice",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = NeveljavnaKosaricaMapper.class)))
    })
    @POST
    @Path("{imeTrgovine}/{idUporabnika}")
    public Response ustvariKosarico(@RequestBody(
            description = "DTO objekt za dodajanje kosaric.",
            required = true,
            content = @Content(schema = @Schema(implementation = Kosarica.class))) KosaricaDto kosarica,
                                   @PathParam("imeTrgovine") String imeTrgovine,
                                   @PathParam("idUporabnika") Integer id) {

        TrgovinaDto trgovina = new TrgovinaDto(imeTrgovine);
        Uporabnik u = uporabnikZrno.pridobiUporabnika(id);
        if(u == null)  return Response.status(Response.Status.NOT_FOUND).build();
        UporabnikDto uporabnik = new UporabnikDto(u);
        Kosarica novaKosarica = upravljalecProduktovZrno.ustvariKosarico(kosarica, uporabnik, trgovina);
        return Response.status(Response.Status.OK).entity(novaKosarica).build();
    }

    @Operation(summary = "Spremeni košarico.", description = "Spremeni košarico v podatkovni bazi")
    @Parameters({
            @Parameter(name = "id", description = "ID košarice", required = true)
    })
    @APIResponses({
            @APIResponse(description = "Popravljena košarica",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))
            )
    })
    @PUT
    @Path("{id}")
    public Response posodobiKosarico(@RequestBody(
            description = "DTO objekt za spreminjanje košaric.",
            required = true,
            content = @Content(schema = @Schema(implementation = Kosarica.class))) @PathParam("id") Integer id) {
        if(!kosaricaZrno.posodobiKosarico(id)) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).build();
    }

    @Operation(summary = "Izbriše košarico.", description = "Izbriši košarico")
    @Parameters({
            @Parameter(name = "id", description = "ID košarice", required = true)
    })
    @APIResponses({
            @APIResponse(responseCode = "200"),
            @APIResponse(description = "Napaka pri brisanju košarice.",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = NeveljavnaKosaricaMapper.class))
            )
    })
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
