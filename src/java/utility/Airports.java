/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entity.Airport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author sebastiannielsen
 */
public class Airports {
    private static EntityManagerFactory emf = EntityManagerFactoryProvider.getEntityManagerFactory();
    private static Map<String, Airport> airports = getAirports();
    
    /**
     * get All Airports from DB
     *
     * @return return map containing Airport objects with IATA-code as key
     */
    public static Map<String, Airport> getAirports() {
        EntityManager em = getEntityManager();
        Map<String, Airport> airportMap = new HashMap();
        if(airportMap.isEmpty()){
            try {
                TypedQuery<Airport> query = em.createNamedQuery("Airport.findAll", Airport.class);
                List<Airport> airportList = query.getResultList();
                for (Airport a : airportList) {
                    airportMap.put(a.getIATACode(), a);
                }
            } finally {
                em.close();
            }
                return airportMap;
            } else {
                return airports;
            }
    }    
    
     private static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
}
