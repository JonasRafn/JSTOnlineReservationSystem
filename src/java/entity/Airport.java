package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="airport")
@NamedQueries({
    @NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a")})
public class Airport implements Serializable {

    @Column(name = "IATA_code")
    @Id
    private String IATACode;

    @Column(name = "airport_name")
    private String name;

    @Column(name = "city")
    private String city;
    
    @Column(name = "country")
    private String country;

    @Column(name = "time_zone")
    private String timeZone;

    public Airport() {
    }

    public Airport(String IATACode, String name, String city, String country, String timeZone) {
        this.IATACode = IATACode;
        this.name = name;
        this.city = city;
        this.country = country;
        this.timeZone = timeZone;
    }

    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACODE) {
        this.IATACode = IATACODE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
