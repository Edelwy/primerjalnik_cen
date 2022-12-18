package si.fri.prpo.skupina3.api.v1.mappers;

import si.fri.prpo.skupina3.storitve.izjeme.NeveljavnaTrgovinaDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NeveljavnaTrgovinaMapper implements ExceptionMapper<NeveljavnaTrgovinaDtoIzjema> {
    @Override
    public Response toResponse(NeveljavnaTrgovinaDtoIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"")
                .build();
    }
}
