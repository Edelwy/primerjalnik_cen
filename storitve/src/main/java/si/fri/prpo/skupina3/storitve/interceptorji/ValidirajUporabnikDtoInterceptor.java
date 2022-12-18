package si.fri.prpo.skupina3.storitve.interceptorji;
import si.fri.prpo.skupina3.storitve.anotacije.ValidirajUporabnikDto;
import si.fri.prpo.skupina3.storitve.dtos.UporabnikDto;
import si.fri.prpo.skupina3.storitve.izjeme.NeveljavenUporabnikDtoIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@ValidirajUporabnikDto
public class ValidirajUporabnikDtoInterceptor {
    Logger log = Logger.getLogger(ValidirajUporabnikDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajUporabnikDtoInterceptor(InvocationContext context) throws Exception {
        if (context.getParameters().length == 1 && context.getParameters()[0] instanceof UporabnikDto) {
            UporabnikDto uporabnik = (UporabnikDto) context.getParameters()[0];
            if (uporabnik.getIme() == null || uporabnik.getIme().isEmpty() || uporabnik.getPriimek() == null || uporabnik.getPriimek().isEmpty()) {
                String msg = "Podan uporabnik ne vsebuje obveznih podatkov (ime, priimek).";
                log.severe(msg);
                throw new NeveljavenUporabnikDtoIzjema(msg);
            }
        }
        return context.proceed();
    }
}
