package unity.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Created by Son on 5/4/2017.
 */
@Path("/unity")
public class UnityController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("myip")
    public String get(@Context HttpServletRequest requestContext, @Context HttpHeaders headers) {
        return "{\"ip\":\"" + requestContext.getRemoteAddr() + "\"}";
    }
}
