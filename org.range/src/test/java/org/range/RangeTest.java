package org.range;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RangeTest {

	@Test
	public void rangeNull() {
		try {
			new Range(null);
		}
		catch (Exception e) {
			assertEquals("Range expression is not defined", e.getMessage());
		}

	}

	@Test
	public void rangeEmpty() {
		try {
			new Range("");
		}
		catch (Exception e) {
			assertEquals("Range expression is not defined", e.getMessage());
		}
	}

	@Test
	public void rangeBadBorderMin() {
		try {
			new Range("g5,3(");
		}
		catch (Exception e) {
			assertEquals("Range expression 1st element must be " + Range.BORDERS_MIN, e.getMessage());
		}
	}

	@Test
	public void rangeBadBorderMax() {
		try {
			new Range("(5,3(");
		}
		catch (Exception e) {
			assertEquals("Range expression 1st element must be " + Range.BORDERS_MAX, e.getMessage());
		}
	}

}
