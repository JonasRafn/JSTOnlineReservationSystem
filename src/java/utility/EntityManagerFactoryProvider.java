package utility;

import deploy.DeploymentConfiguration;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {
    static EntityManagerFactory emf;
    
    public static EntityManagerFactory getEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        return emf;
    }
}
