/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facadetest;

import deploy.DeploymentConfiguration;
import entity.Airport;
import facades.AirportFacade;
import interfaces.IAirportFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebastiannielsen
 */
public class AirportFacadeTest {
    
    public AirportFacadeTest() {
    }
    
    @Test
    public void getFlightFromSuccess() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IAirportFacade ctrl = new AirportFacade(emf);
        List<Airport> airports = ctrl.getAirports();
        assertEquals(25, airports.size());
        assertEquals("CPH", airports.get(5).getIATACode());
    }
    
}
