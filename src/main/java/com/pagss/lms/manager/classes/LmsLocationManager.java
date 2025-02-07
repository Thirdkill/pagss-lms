package com.pagss.lms.manager.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.commands.TableCommand;
import com.pagss.lms.dao.interfaces.LocationDao;
import com.pagss.lms.domains.Location;
import com.pagss.lms.manager.interfaces.LocationManager;

@Component
public class LmsLocationManager implements LocationManager {

	private LocationDao locationDao;
	/*******************************************************************************************************
	 * Start: Autowired Setters	
	 ******************************************************************************************************/
	@Autowired
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}
	/*******************************************************************************************************
	 * End: Autowired Setters	
	 ******************************************************************************************************/
	
	@Override
	public List<Location> fetchLocationPages(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.locationDao.fetchLocationPages(pageSize, calculatedPageNo);
	}
	
	@Override
	public int countTotalLocations() {
		return this.locationDao.countTotalLocations();
	}

	@Override
	public List<Location> searchLocations(TableCommand tableCommand) {
		int calculatedPageNo = tableCommand.getPageSize() * (tableCommand.getPageNumber() - 1);
		tableCommand.setPageNumber(calculatedPageNo);
		
		if(tableCommand.getKeyword() != "") {
			tableCommand.setWhereClause("WHERE l.locationCode like :keyword OR locationName like :keyword OR description like :keyword ");
		} else {
			tableCommand.setWhereClause("");
		}
		
		switch(tableCommand.getSortColumnName()) {
			case "locationCode":
				tableCommand.setOrderClause("ORDER BY l.locationCode " + tableCommand.getSortDirection() + " ");
				break;
			case "locationName":
				tableCommand.setOrderClause("ORDER BY l.locationName " + tableCommand.getSortDirection() + " ");
				break;
			case "description":
				tableCommand.setOrderClause("ORDER BY l.description " + tableCommand.getSortDirection() + " ");
				break;
			case "status":
				tableCommand.setOrderClause("ORDER BY l.status " + tableCommand.getSortDirection() + " ");
				break;
		}
		return this.locationDao.searchLocations(tableCommand);
	}

	@Override
	public int countTotalSearchedLocations(String keyword) {
		return this.locationDao.countTotalSearchedLocations(keyword);
	}
	
	@Override
	public Location fetchGeneratedLocationCode() {
		Location location = new Location();
		location.setLocationCode(generateLocationCode(this.locationDao.fetchLastInsertId()));
		return location;
	}
	
	@Override
	public int countLocationCode(String locationCode) {
		return this.locationDao.countLocationCode(locationCode);
	}
	
	@Override
	public List<Location> fetchLocations() {
		return this.locationDao.fetchLocations();
	}
	
	@Override
	public List<Location> fetchLocationsByStatus(int status) {
		return this.locationDao.fetchLocationsByStatus(status);
	}
	/**************************************************************************************************
	*								Start: Private Classes												*
	****************************************************************************************************/
	private String generateLocationCode(int locationId) {
		String locationNo = Integer.toString(10000 + locationId).substring(1);
		return "LC" + locationNo;
	}
	/**************************************************************************************************
	*								End: Private Classes												*
	****************************************************************************************************/
}
