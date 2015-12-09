package exceptionmapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exception.AirlineAlreadyExistException;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AirlineAlreadyExistExceptionMapper implements ExceptionMapper<AirlineAlreadyExistException> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    ServletContext context;

    @Override
    public Response toResponse(AirlineAlreadyExistException e) {
        JsonObject jo = new JsonObject();
        if (Boolean.valueOf(context.getInitParameter("debug"))) {
            jo.addProperty("StackTrace", Arrays.toString(e.getStackTrace()));
        }
        jo.addProperty("message", e.getMessage());
        return Response.status(Response.Status.CONFLICT).entity(jo.toString()).build();
    }

}
