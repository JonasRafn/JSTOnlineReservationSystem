package service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dto.AirlineDTO;
import dto.FlightDTO;
import java.util.concurrent.Callable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class getFlights implements Callable<AirlineDTO> {

    private String url;
    private Gson gson;

    public getFlights(String url) {
        this.url = url;
    }

    @Override
    public AirlineDTO call() throws Exception {
        Client client;
        WebTarget target;
        String response = "";
        client = ClientBuilder.newClient();
        target = client.target(url);
        response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        JsonObject jo = gson.fromJson(response, JsonObject.class);
        AirlineDTO airline = new AirlineDTO(gson.fromJson(jo.get("airline").toString(), String.class)); //save airline name
        for (JsonElement element : jo.getAsJsonArray("flights")) { //save flights
            JsonObject asJsonObject = element.getAsJsonObject();
            FlightDTO dto = gson.fromJson(asJsonObject, FlightDTO.class);
            airline.addFlights(dto);
        }
        return airline;
    }
}
