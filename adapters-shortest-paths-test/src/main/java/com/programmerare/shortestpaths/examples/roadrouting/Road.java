package com.programmerare.shortestpaths.examples.roadrouting;

import java.util.UUID;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Transient;

import com.programmerare.shortestpaths.adapter.Edge;
import com.programmerare.shortestpaths.adapter.Vertex;
import com.programmerare.shortestpaths.adapter.Weight;

/**
 * @author Tomas Johansson
 */
//@Entity
public final class Road implements Edge {

	//	@Id
	private UUID roadKey;
	
//	@Transient
	private City cityFrom;
	
//	@Transient
	private City cityTo;
	
	private UUID cityFromId;
	private UUID cityToId;
	
	private int roadLength;
	private RoadQuality roadQuality;
	
//	@Transient
	private Weight edgeWeight;

	private String roadName;

	public Road(UUID roadKey)  {
		this.roadKey = roadKey;
	}
	
	public Road()  {
	}	

	public Road(City cityFrom, City cityTo, int roadLength, RoadQuality roadQuality, String roadName) {
		this.cityFrom = cityFrom;
		this.cityTo = cityTo;
		this.roadLength = roadLength;
		this.roadName = roadName;

		setRoadQuality(roadQuality);
		setEdgeWeight(roadLength, roadQuality);
	}

	private void setEdgeWeight(int roadLength, RoadQuality roadQuality) {
		edgeWeight = new WeightDeterminedByRoadLengthAndQuality(roadLength, roadQuality);
	}

	// interface methods below
	public String getEdgeId() { // important as documented in the interface for Edge
		return cityFrom.getVertexId() + "_" + cityTo.getVertexId();
	}

	public Vertex getStartVertex() {
		return cityFrom;
	}

	public Vertex getEndVertex() {
		return cityTo;
	}	

	public Weight getEdgeWeight() {
		return edgeWeight;
	}
	// interface methods above

	@Override
	public String toString() {
		return "Road [cityFrom=" + cityFrom + ", cityTo=" + cityTo + ", edgeWeight=" + edgeWeight + ", roadQuality="
				+ getRoadQuality() + ", roadLength=" + roadLength + ", roadKey=" + roadKey + ", cityFromId=" + cityFromId
				+ ", cityToId=" + cityToId + "]";
	}

	public RoadQuality getRoadQuality() {
		return roadQuality;
	}

	public void setRoadQuality(RoadQuality roadQuality) {
		this.roadQuality = roadQuality;
	}

	public String getRoadName() {
		return roadName;
	}
	
}
