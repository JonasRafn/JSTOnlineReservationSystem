/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Airport;
import interfaces.IAirportFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class AirportFacade implements IAirportFacade {
    
    private EntityManagerFactory emf;

    public AirportFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Airport> getAirports() {
    EntityManager em = getEntityManager();
    List<Airport> airports = new ArrayList();
    try {
         TypedQuery<Airport> query = em.createNamedQuery("Airport.findAll", Airport.class);
         airports = query.getResultList();
        } finally {
            em.close();
        }
        return airports;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
     
    
}
