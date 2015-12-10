/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dto.HistoryDTO;

/**
 *
 * @author sebastiannielsen
 */
public interface IDashboardFacade {
    public HistoryDTO getSearchHistory() throws Exception;

}
