package com.programmerare.shortestpaths.utils;

import java.util.Date;

public final class TimeMeasurer {

	public static TimeMeasurer start() {
		return new TimeMeasurer();
	}
	
	private final long startTime;
	
	private TimeMeasurer() {
		this.startTime = (new Date()).getTime();
	}
	
	public long getSeconds() {
		return ((new Date()).getTime()-startTime) / 1000;
	}
}
