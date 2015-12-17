package rest;

import entity.Role;
import exception.ServerException;
import facades.UserFacade;
import interfaces.IUserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static utility.JsonConverter.fromJsonUser;

@Path("create")
public class CreateUserRest {

    private final IUserFacade uf;

    @Context
    private UriInfo context;

    public CreateUserRest() {
        uf = new UserFacade();
    }

    /**
     * Creates a new user, add to db and return created user as Json-object. If
     * user exists return a json-Object with error-message.
     *
     * @param user json-string
     * @return json-Object with created user
     * @throws exception.ServerException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String user) throws ServerException {
        entity.User u = fromJsonUser(user);
        if (u.getUserName() == null | u.getUserName().equals("")) {
            String error = "{\"message\":\"You must input a username!\"}";
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(error).build();
        } else if (u.getPassword() == null | u.getPassword().equals("")) {
            String error = "{\"message\":\"You must input a password!\"}";
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(error).build();
        } else {
            Role role = new Role("User");
            u.AddRole(role);
            uf.createUser(u);
            return Response.status(Response.Status.CREATED).entity(u).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
