/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import deploy.DeploymentConfiguration;
import static deploy.DeploymentConfiguration.PU_NAME;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author sebastiannielsen
 */
public class EntityManagerFactoryProvider {
    static EntityManagerFactory emf;
    
    public static EntityManagerFactory getEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        return emf;
    }
    
}
