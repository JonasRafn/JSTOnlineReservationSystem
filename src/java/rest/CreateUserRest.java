package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Role;
import exception.UserAlreadyExistException;
import facades.UserFacade;
import interfaces.IUserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("create")
public class CreateUserRest {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    IUserFacade uf = new UserFacade();

    @Context
    private UriInfo context;

    public CreateUserRest() {
    }

    /**
     * Creates a new user, add to db and return created user as Json-object. If
     * user exists return a json-Object with error-message.
     *
     * @param user json-string
     * @return json-Object with created user
     * @throws exception.UserAlreadyExistException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String user) throws UserAlreadyExistException {
        entity.User u = gson.fromJson(user, entity.User.class);
        Role role = new Role("User");
        u.AddRole(role);
        uf.createUser(u);
        return Response.status(Response.Status.CREATED).entity(u).build();
    }
}
