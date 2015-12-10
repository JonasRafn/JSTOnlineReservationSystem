/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author sebastiannielsen
 */
public class Destination {
    
        String destination;
        String iataCode;
        Long numberOfSearches;
        
        public Destination(String iataCode, Long numberOfSearches){
            this.iataCode = iataCode;
            this.numberOfSearches = numberOfSearches;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public Long getNumberOfSearches() {
            return numberOfSearches;
        }

        public void setNumberOfSearches(Long numberOfSearches) {
            this.numberOfSearches = numberOfSearches;
        }

        public String getIataCode() {
            return iataCode;
        }

        public void setIataCode(String iataCode) {
            this.iataCode = iataCode;
        }
        
        
    
}
