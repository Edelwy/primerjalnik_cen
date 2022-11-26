package si.fri.prpo.skupina3.storitve.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class ZrnoRequestScoped {
    private UUID id;
    private Logger log = Logger.getLogger(ZrnoRequestScoped.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + ZrnoRequestScoped.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + ZrnoRequestScoped.class.getSimpleName());
    }

    public void izpisId() {
        id = UUID.randomUUID();
        log.info("Id zrna z @RecuestScoped je: " + id);
    }
}
