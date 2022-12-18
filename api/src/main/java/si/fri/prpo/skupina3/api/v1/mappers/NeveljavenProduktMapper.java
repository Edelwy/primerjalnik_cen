package si.fri.prpo.skupina3.api.v1.mappers;

import si.fri.prpo.skupina3.storitve.izjeme.NeveljavenProduktDtoIzjema;
import si.fri.prpo.skupina3.storitve.izjeme.NeveljavenUporabnikDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NeveljavenProduktMapper implements ExceptionMapper<NeveljavenProduktDtoIzjema> {
    @Override
    public Response toResponse(NeveljavenProduktDtoIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"")
                .build();
    }
}
