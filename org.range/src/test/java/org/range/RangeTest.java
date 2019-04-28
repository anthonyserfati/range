package org.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	public void rangebadLength() {
		try {
			String[] args = { "(", "5", "(" };
			new Range(args);
		}
		catch (Exception e) {
			assertEquals("Range expression length must be >=4", e.getMessage());
		}
	}

	@Test
	public void range() {
		String[] args = { "(", "5", "3", "]" };
		Range range = new Range(args);
		assertEquals(Range.BORDER_MIN_OUT, range.getBorderMin());
		assertEquals(3, range.getMin());
		assertEquals(5, range.getMax());
		assertEquals(Range.BORDER_MAX_IN, range.getBorderMax());

		String[] args2 = { "[", "0", "6", "]" };
		range = new Range(args2);
		assertEquals(Range.BORDER_MIN_IN, range.getBorderMin());
		assertEquals(0, range.getMin());
		assertEquals(6, range.getMax());
		assertEquals(Range.BORDER_MAX_IN, range.getBorderMax());

		String[] args3 = { "[", "4", "0", ")" };
		range = new Range(args3);
		assertEquals(Range.BORDER_MIN_IN, range.getBorderMin());
		assertEquals(0, range.getMin());
		assertEquals(4, range.getMax());
		assertEquals(Range.BORDER_MAX_OUT, range.getBorderMax());

		String[] args4 = { "[", "3", "1", "-5", "]" };
		range = new Range(args4);
		assertEquals(Range.BORDER_MIN_IN, range.getBorderMin());
		assertEquals(-5, range.getMin());
		assertEquals(3, range.getMax());
		assertEquals(Range.BORDER_MAX_IN, range.getBorderMax());
	}

	@Test
	public void rangeContainValues() {
		// [2,6) contains {2,4}
		String[] args = { "[", "2", "6", ")" };
		Range range = new Range(args);
		int[] values = { 2, 4 };
		boolean contains = range.contains(values);
		assertTrue(contains);
		// [2,6) doesn’t contain {-1,1,6,10}
		int[] values2 = { -1, 1, 6, 10 };
		contains = range.contains(values2);
		assertFalse(contains);
	}

}
