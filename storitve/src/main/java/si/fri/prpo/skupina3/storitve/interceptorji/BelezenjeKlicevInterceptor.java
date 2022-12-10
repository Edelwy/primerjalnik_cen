package si.fri.prpo.skupina3.storitve.interceptorji;

import si.fri.prpo.skupina3.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina3.storitve.zrna.BelezenjeKlicevZrno;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {
    BelezenjeKlicevZrno belezenjeKlicevZrno = new BelezenjeKlicevZrno();

    @AroundInvoke
    public Object klici(InvocationContext context) throws Exception {
        String imeMetode = context.getMethod().getName();
        int stKlicev = 1;
        if (belezenjeKlicevZrno.vsebuje(imeMetode))
            stKlicev = belezenjeKlicevZrno.pridobiStKlicev(imeMetode)+1;
        belezenjeKlicevZrno.nastaviStKlicev(imeMetode, stKlicev);
        return context.proceed();
    }
}
