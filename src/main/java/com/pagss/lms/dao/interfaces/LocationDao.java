package com.pagss.lms.dao.interfaces;

import java.util.List;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.domains.Location;

public interface LocationDao {

	public List<Location> fetchLocationPages(int pageSize, int pageNo);
	
	public int countTotalLocations();
	
	public List<Location> searchLocations(TableCommand tableCommand);
	
	public int countTotalSearchedLocations(String keyword);
	
	public int fetchLastInsertId();
	
	public int countLocationCode(String locationCode);
	
	public List<Location> fetchLocations();
	
	public List<Location> fetchLocationsByStatus(int status);
}
