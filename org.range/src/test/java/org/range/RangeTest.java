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
			String[] args = {};
			new Range(args);
		}
		catch (Exception e) {
			assertEquals("Range expression is not defined", e.getMessage());
		}
	}

	@Test
	public void rangeBadBorderMin() {
		try {
			String[] args = { "g", "5", "3", "(" };
			new Range(args);
		}
		catch (Exception e) {
			assertEquals("Range expression 1st element must be " + Range.BORDERS_MIN, e.getMessage());
		}
	}

	@Test
	public void rangeBadBorderMax() {
		try {
			String[] args = { "(", "5", "3", "(" };
			new Range(args);
		}
		catch (Exception e) {
			assertEquals("Range expression 1st element must be " + Range.BORDERS_MAX, e.getMessage());
		}
	}

	@Test
	public void rangeExpressionSize() {
		try {
			String[] args = { "(", "5", "3" };
			new Range(args);
		}
		catch (Exception e) {
			assertEquals("Range expression must be 4 characters length", e.getMessage());
		}
	}

}
