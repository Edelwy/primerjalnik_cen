package si.fri.prpo.skupina3.storitve.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ZrnoApplicationScoped {
    private UUID id;
    private Logger log = Logger.getLogger(ZrnoApplicationScoped.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + ZrnoApplicationScoped.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + ZrnoApplicationScoped.class.getSimpleName());
    }

    public void izpisId() {
        id = UUID.randomUUID();
        log.info("Id zrna z @ApplicationScoped je: " + id);
    }
}
