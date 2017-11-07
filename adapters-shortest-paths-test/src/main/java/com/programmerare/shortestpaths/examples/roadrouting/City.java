package com.programmerare.shortestpaths.examples.roadrouting;

import java.util.UUID;

//import javax.persistence.Entity;
//import javax.persistence.Id;

import com.programmerare.shortestpaths.adapter.Vertex;

/**
 * @author Tomas Johansson
 */
//@Entity
public final class City implements Vertex {

//	@Id
	private UUID cityKey;

	private String cityName;
	
	/**
	 * @param cityKey might be a primary key in your own database
	 */
	public City(final UUID cityKey) {
		this.cityKey = cityKey;
	}
	
	public City() {
	}	

	public City(final UUID cityKey, final String cityName) {
		this.cityKey = cityKey;
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}

	// the interface method
	public String getVertexId() {
		return cityKey.toString();
	}

	@Override
	public String toString() {
		return "City [cityName=" + cityName + ", cityKey=" + cityKey + "]";
	}

}