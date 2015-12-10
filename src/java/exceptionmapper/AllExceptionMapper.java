package exceptionmapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AllExceptionMapper implements ExceptionMapper<Exception> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    ServletContext context;

    @Override
    public Response toResponse(Exception e) {
        JsonObject jo = new JsonObject();
        if (Boolean.valueOf(context.getInitParameter("debug"))) {
            jo.addProperty("message", "Error message: " + e.toString() + "\n\n Stacktrace: " + Arrays.toString(e.getStackTrace()));
        } else {
            jo.addProperty("message", "Oops... Internal Server Error: Something went wrong!");
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jo.toString()).build();
    }
}
