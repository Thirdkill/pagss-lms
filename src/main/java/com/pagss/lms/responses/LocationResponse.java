package com.pagss.lms.responses;

import java.util.List;

import com.pagss.lms.domains.Location;

import lombok.Getter;
import lombok.Setter;

public class LocationResponse {

	@Getter @Setter private int status;
	@Getter @Setter private int totalRecords;
	@Getter @Setter private Location location;
	@Getter @Setter private List<Location> locations;
	
	public LocationResponse(int status) {
		setStatus(status);
	}
	
	public LocationResponse(int status,Location location) {
		setStatus(status);
		setLocation(location);
	}

	public LocationResponse(int status,List<Location> locations) {
		setStatus(status);
		setLocations(locations);
	}
	
	public LocationResponse(int status,List<Location> locations, int totalRecords) {
		setStatus(status);
		setLocations(locations);
		setTotalRecords(totalRecords);
	}
}
