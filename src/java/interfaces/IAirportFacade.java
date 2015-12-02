/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.Airport;
import exception.BadRequestException;
import java.util.List;

/**
 *
 * @author sebastiannielsen
 */
public interface IAirportFacade {
    
    public List<Airport> getAirports();
    
}
