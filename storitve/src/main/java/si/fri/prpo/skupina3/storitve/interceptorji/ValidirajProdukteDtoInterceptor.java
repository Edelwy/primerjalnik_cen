package si.fri.prpo.skupina3.storitve.interceptorji;

import si.fri.prpo.skupina3.storitve.anotacije.ValidirajProduktDto;
import si.fri.prpo.skupina3.storitve.dtos.ProduktDto;
import si.fri.prpo.skupina3.storitve.izjeme.NeveljavenProduktDtoIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@ValidirajProduktDto
public class ValidirajProdukteDtoInterceptor {
    Logger log = Logger.getLogger(ValidirajProdukteDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajUporabnikDtoInterceptor(InvocationContext context) throws Exception {
        if (context.getParameters().length == 1 && context.getParameters()[0] instanceof ProduktDto) {
            ProduktDto produkt = (ProduktDto) context.getParameters()[0];
            if (produkt.getIme() == null || produkt.getIme().isEmpty() || produkt.getProduktId() == null) {
                String msg = "Podan produkt ne vsebuje obveznih podatkov (ime, id).";
                log.severe(msg);
                throw new NeveljavenProduktDtoIzjema(msg);
            }
        }
        return context.proceed();
    }
}
