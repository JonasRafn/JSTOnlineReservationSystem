package interfaces;

import dto.HistoryDTO;

public interface IDashboardFacade {
    public HistoryDTO getSearchHistory() throws Exception;

}
