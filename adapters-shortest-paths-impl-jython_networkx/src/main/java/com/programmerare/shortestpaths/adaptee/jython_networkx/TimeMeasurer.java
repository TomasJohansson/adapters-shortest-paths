package com.programmerare.shortestpaths.adaptee.jython_networkx;

import java.util.Date;

// this file was copied from:
// package com.programmerare.shortestpaths.core.utils;
// since currently there is no dependency to the core project but if this library will be used
// and thus with a dependency to core then it should be used instead
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

	public void printMessageIncludingNumberOfSecondsSinceStart(String messageSuffix) {
		final long seconds = this.getSeconds();
		System.out.println("" + seconds + " seconds since start. " + messageSuffix);
	}
}
