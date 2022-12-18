package si.fri.prpo.skupina3.storitve.interceptorji;

import si.fri.prpo.skupina3.storitve.anotacije.ValidirajKosaricaDto;
import si.fri.prpo.skupina3.storitve.anotacije.ValidirajTrgovineDto;
import si.fri.prpo.skupina3.storitve.anotacije.ValidirajUporabnikDto;
import si.fri.prpo.skupina3.storitve.dtos.TrgovinaDto;
import si.fri.prpo.skupina3.storitve.izjeme.NeveljavnaTrgovinaDtoIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@ValidirajTrgovineDto
public class ValidirajTrgovineDtoInterceptor {
    Logger log = Logger.getLogger(ValidirajTrgovineDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajTrgovineDtoInterceptor(InvocationContext context) throws Exception {
        if (context.getParameters().length == 1 && context.getParameters()[0] instanceof TrgovinaDto) {
            TrgovinaDto trgovina = (TrgovinaDto) context.getParameters()[0];
            if (trgovina.getIme() == null || trgovina.getIme().isEmpty()) {
                String msg = "Podana trgovina ne vsebuje obveznih podatkov (ime).";
                log.severe(msg);
                throw new NeveljavnaTrgovinaDtoIzjema(msg);
            }
        }
        return context.proceed();
    }
}
