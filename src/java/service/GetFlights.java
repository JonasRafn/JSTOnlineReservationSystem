package service;

import java.util.concurrent.Callable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GetFlights implements Callable<Response> {

    private String url;

    public GetFlights(String url) {
        this.url = url;
    }

    @Override
    public Response call() {
        Client client;
        WebTarget target;

        String response = "";
        client = ClientBuilder.newClient();
        target = client.target(url);
        Response r = target.request(MediaType.APPLICATION_JSON).get(Response.class);
        return r;
    }
}
