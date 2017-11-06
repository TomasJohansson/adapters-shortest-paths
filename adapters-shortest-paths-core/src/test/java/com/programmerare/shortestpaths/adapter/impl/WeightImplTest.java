package com.programmerare.shortestpaths.adapter.impl;

import static com.programmerare.shortestpaths.adapter.impl.WeightImpl.createWeight;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.programmerare.shortestpaths.adapter.Weight;

/**
 * @author Tomas Johansson
 */
public class WeightImplTest {

	private Weight weightA;
	private Weight weightB;
	private double weightValueA;
	private double weightValueB;
	
	
	@Before
	public void setUp() throws Exception {
		weightValueA = 12345.6789;
		weightValueB = 12345.6789;
		weightA = createWeight(weightValueA);
		weightB = createWeight(weightValueB);
	}
	
	@Test
	public void testGetWeightValue() {
		assertEquals(
			weightValueA, 
			weightA.getWeightValue(), 
			0.0000000001 // small delta value for comparison of double
		);
	}
	
	@Test
	public void testEquals() {
		assertEquals(weightA, weightB);

		assertTrue(weightA.equals(weightB));
		assertTrue(weightB.equals(weightA));
	}
	
	@Test
	public void testHashCode() {
		assertEquals(weightA.hashCode(), weightB.hashCode());
	}	

}