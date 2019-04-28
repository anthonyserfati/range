package org.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
	public void containValues() {
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

	@Test
	public void allPoints() {
		String[] args = { "[", "2", "6", ")" };
		Range range = new Range(args);
		List<Integer> points = range.allPoints();
		assertEquals(4, points.size());
		assertEquals(Integer.valueOf(2), points.get(0));
		assertEquals(Integer.valueOf(3), points.get(1));
		assertEquals(Integer.valueOf(4), points.get(2));
		assertEquals(Integer.valueOf(5), points.get(3));
	}

	@Test
	public void containsRange() {
		// [2,5) doesn’t contain [7,10)
		String[] args = { "[", "2", "5", ")" };
		Range range = new Range(args);
		String[] argsBis = { "[", "7", "10", ")" };
		Range rangeBis = new Range(argsBis);
		assertFalse(range.containsRange(rangeBis));
		// [2,5) doesn’t contain [3,10)
		String[] argsBis2 = { "[", "3", "10", ")" };
		Range rangeBis2 = new Range(argsBis2);
		assertFalse(range.containsRange(rangeBis2));

		// [2,10) contains [3,5]
		String[] args2 = { "[", "2", "10", ")" };
		Range range2 = new Range(args2);
		String[] argsBis3 = { "[", "3", "5", "]" };
		Range rangeBis3 = new Range(argsBis3);
		assertTrue(range2.containsRange(rangeBis3));

		// [3,5) doesn’t contain [2,10)
		String[] args3 = { "[", "3", "5", ")" };
		Range range3 = new Range(args3);
		String[] argsBis4 = { "[", "2", "10", ")" };
		Range rangeBis4 = new Range(argsBis4);
		assertFalse(range3.containsRange(rangeBis4));

		// [3,5] contains [3,5)
		String[] args4 = { "[", "3", "5", "]" };
		Range range4 = new Range(args4);
		String[] argsBis5 = { "[", "3", "5", ")" };
		Range rangeBis5 = new Range(argsBis5);
		assertTrue(range4.containsRange(rangeBis5));
	}

	@Test
	public void endPoints() {
		// [2,6) endPoints = {2,5}
		String[] args1 = { "[", "2", "6", ")" };
		Range range1 = new Range(args1);
		List<Integer> endPoints = range1.endPoints();
		assertEquals(2, endPoints.size());
		assertEquals(Integer.valueOf(2), endPoints.get(0));
		assertEquals(Integer.valueOf(5), endPoints.get(1));

		// [2,6] endPoints = {2,6}
		String[] args2 = { "[", "2", "6", "]" };
		Range range2 = new Range(args2);
		endPoints = range2.endPoints();
		assertEquals(2, endPoints.size());
		assertEquals(Integer.valueOf(2), endPoints.get(0));
		assertEquals(Integer.valueOf(6), endPoints.get(1));

		// (2,6) endPoints = {3,5}
		String[] args3 = { "(", "2", "6", ")" };
		Range range3 = new Range(args3);
		endPoints = range3.endPoints();
		assertEquals(2, endPoints.size());
		assertEquals(Integer.valueOf(3), endPoints.get(0));
		assertEquals(Integer.valueOf(5), endPoints.get(1));

		// (2,6] endPoints = {3,6}
		String[] args4 = { "(", "2", "6", "]" };
		Range range4 = new Range(args4);
		endPoints = range4.endPoints();
		assertEquals(2, endPoints.size());
		assertEquals(Integer.valueOf(3), endPoints.get(0));
		assertEquals(Integer.valueOf(6), endPoints.get(1));
	}

}
