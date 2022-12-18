package si.fri.prpo.skupina3.api.v1;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.annotation.security.DeclareRoles;
import javax.ws.rs.core.Context;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@DeclareRoles({"user", "admin"})
@OpenAPIDefinition(info = @Info(title = "Produkti API", version = "v1",
        contact = @Contact(email = "prpo@fri.uni-lj.si"),
        license = @License(name = "dev"), description = "API za storitev Produkti."),
        servers = @Server(url = "http://localhost:8080/"))

@ApplicationPath("v1")
public class ProduktiApplication extends Application {
}
