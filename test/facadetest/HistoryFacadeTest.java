/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facadetest;

import deploy.DeploymentConfiguration;
import dto.HistoryDTO;
import facades.HistoryFacade;
import interfaces.IHistoryFacade;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import utility.EntityManagerFactoryProvider;

/**
 *
 * @author sebastiannielsen
 */
public class HistoryFacadeTest {

    public HistoryFacadeTest() {
    }

    @Test
    public void testhistory() {
        EntityManagerFactory emf = EntityManagerFactoryProvider.getEntityManagerFactory();
        IHistoryFacade ctrl = new HistoryFacade(emf);
        HistoryDTO history = ctrl.getSearchHistory();

        
        System.out.println("Average tickets: " + history.getAverageNumberOfTickets());
    }
}
