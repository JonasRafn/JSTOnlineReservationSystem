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
public class PopMonth {
    
    private String month;
    private long amountOfSearches;

    public PopMonth(String Month, long amountOfSearches) {
        this.month = Month;
        this.amountOfSearches = amountOfSearches;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String Month) {
        this.month = Month;
    }

    public long getAmountOfSearches() {
        return amountOfSearches;
    }

    public void setAmountOfSearches(long amountOfSearches) {
        this.amountOfSearches = amountOfSearches;
    }
}
