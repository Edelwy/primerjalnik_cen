package si.fri.prpo.skupina3.storitve.interceptorji;

import si.fri.prpo.skupina3.storitve.anotacije.ValidirajKosaricaDto;
import si.fri.prpo.skupina3.storitve.dtos.KosaricaDto;
import si.fri.prpo.skupina3.storitve.izjeme.NeveljavnaKosaricaDtoIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@ValidirajKosaricaDto
public class ValidirajKosaricaDtoInterceptor {
    Logger log = Logger.getLogger(ValidirajKosaricaDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajInvalidRequest(InvocationContext context) throws Exception {
        if (context.getParameters().length == 1 && context.getParameters()[0] instanceof KosaricaDto) {
            KosaricaDto kosarica = (KosaricaDto) context.getParameters()[0];
            if (kosarica.getKosaricaId() == null) {
                String msg = "Podana kosarica ne vsebuje obveznih podatkov (id).";
                log.severe(msg);
                throw new NeveljavnaKosaricaDtoIzjema(msg);
            }
        }
        return context.proceed();
    }
}
