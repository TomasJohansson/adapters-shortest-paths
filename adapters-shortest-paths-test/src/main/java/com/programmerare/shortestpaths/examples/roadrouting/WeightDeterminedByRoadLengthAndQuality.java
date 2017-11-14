package com.programmerare.shortestpaths.examples.roadrouting;

import com.programmerare.shortestpaths.adapter.Weight;

/**
 * @author Tomas Johansson
 */
public final class WeightDeterminedByRoadLengthAndQuality implements Weight {

	private final double weightValue;
	
	public WeightDeterminedByRoadLengthAndQuality(final int roadLength, final RoadQuality roadQuality) {
		final double multiplierForRoadQuality = roadQuality == RoadQuality.BAD ? 1.5 : 1;
		weightValue = roadLength * multiplierForRoadQuality;
	}

	public double getWeightValue() {
		return weightValue;
	}

	public String renderToString() {
		return toString();
	}

	@Override
	public String toString() {
		return "WeightDeterminedByRoadLengthAndQuality [weightValue=" + weightValue + "]";
	}

	
}