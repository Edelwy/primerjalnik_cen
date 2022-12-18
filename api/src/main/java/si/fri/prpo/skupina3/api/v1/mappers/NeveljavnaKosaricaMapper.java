package si.fri.prpo.skupina3.api.v1.mappers;

import si.fri.prpo.skupina3.storitve.izjeme.NeveljavnaKosaricaDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NeveljavnaKosaricaMapper implements ExceptionMapper<NeveljavnaKosaricaDtoIzjema> {
    @Override
    public Response toResponse(NeveljavnaKosaricaDtoIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"")
                .build();
    }
}
