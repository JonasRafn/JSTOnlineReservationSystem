package service;

import java.util.concurrent.Callable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class getFlights implements Callable<String> {

    private String url;

    public getFlights(String url) {
        this.url = url;
    }

    @Override
    public String call() {
        Client client;
        WebTarget target;
        String response = "";
        client = ClientBuilder.newClient();
        target = client.target(url);
        response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        return response;
    }
}
