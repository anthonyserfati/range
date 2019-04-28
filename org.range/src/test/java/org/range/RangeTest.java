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
		assertEquals(3, range.getMin());
		assertEquals(5, range.getMax());

		String[] args2 = { "[", "0", "6", "]" };
		range = new Range(args2);
		assertEquals(0, range.getMin());
		assertEquals(6, range.getMax());

		String[] args3 = { "[", "4", "0", "]" };
		range = new Range(args3);
		assertEquals(0, range.getMin());
		assertEquals(4, range.getMax());

		String[] args4 = { "[", "3", "1", "-5", "]" };
		range = new Range(args4);
		assertEquals(-5, range.getMin());
		assertEquals(3, range.getMax());
	}

}
