package org.range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

/**
 * This is a simple Range class Range can be [a, b, c, .... ] : constructor will order params and determine min / max to set range borders Border can include
 * ('[') / exclude ('(')min / max border value Left border is '[' or '(' Right border is ']' or ')'
 * 
 * @author asi
 *
 */
public class Range {
	public static final String BORDER_MIN_IN = "[";
	public static final String BORDER_MIN_OUT = "(";
	public static final String BORDER_MAX_IN = "]";
	public static final String BORDER_MAX_OUT = ")";

	public static final List<String> BORDERS_MIN = Arrays.asList(BORDER_MIN_IN, BORDER_MIN_OUT);
	public static final List<String> BORDERS_MAX = Arrays.asList(BORDER_MAX_IN, BORDER_MAX_OUT);

	private int min, max;
	private String borderMin, borderMax;


	Range(String[] rangeExpr) {
		validate(rangeExpr);

		// Borders
		borderMin = rangeExpr[0];
		borderMax = rangeExpr[rangeExpr.length - 1];

		// Min / Max
		min = Integer.valueOf(rangeExpr[1]);
		max = Integer.valueOf(rangeExpr[1]);
		for (int i = 1; i <= rangeExpr.length - 2; i++) {
			int val = Integer.valueOf(rangeExpr[i]);
			if (val < min) {
				min = val;
			}
			if (val > max) {
				max = val;
			}
		}
	}

	private void validate(String[] rangeExpr) {
		if (rangeExpr == null || rangeExpr.length == 0) {
			String message = "Range expression is not defined";
			throw new RuntimeErrorException(new Error(message), message);
		}
		if (rangeExpr.length < 4) {
			String message = "Range expression length must be >=4";
			throw new RuntimeErrorException(new Error(message), message);
		}
		if (!BORDERS_MIN.contains(rangeExpr[0])) {
			String message = "Range expression 1st element must be " + BORDERS_MIN;
			throw new RuntimeErrorException(new Error(message), message);
		}
		if (!BORDERS_MAX.contains(rangeExpr[rangeExpr.length - 1])) {
			String message = "Range expression 1st element must be " + BORDERS_MAX;
			throw new RuntimeErrorException(new Error(message), message);
		}
	}

	@Override
	public String toString() {
		return borderMin + min + "," + max + borderMax;
	}

	/**
	 * Getter for min border value
	 * 
	 * @return
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Returns min border value with in/ou border exclusion
	 * 
	 * @return
	 */
	public int getMinBorderVal() {
		return BORDER_MIN_IN.equals(getBorderMin()) ? min : min + 1;
	}

	/**
	 * Getter for max border value
	 * 
	 * @return
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Returns max border value with in/ou border exclusion
	 * 
	 * @return
	 */
	public int getMaxBorderVal() {
		return BORDER_MAX_IN.equals(getBorderMax()) ? max : max - 1;
	}

	/**
	 * Getter for border min expression
	 * 
	 * @return
	 */
	public String getBorderMin() {
		return borderMin;
	}

	/**
	 * Getter for border max expression
	 * 
	 * @return
	 */
	public String getBorderMax() {
		return borderMax;
	}

	public boolean contains(int[] values) {
		for (int value : values) {
			if (!containsValue(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return if range contains given value
	 * 
	 * @param value
	 * @return
	 */
	private boolean containsValue(int value) {
		if(getMinBorderVal()>value) {
			return false;
		}
		if(getMaxBorderVal()<value) {
			return false;
		}
		return true;
	}

	/**
	 * All points of a range
	 * 
	 * @return
	 */
	public List<Integer> allPoints() {
		int minVal = getMinBorderVal();
		int maxMal = getMaxBorderVal();
		List<Integer> points = new ArrayList<>();
		for (int i = minVal; i <= maxMal; i++) {
			points.add(i);
		}
		return points;
	}

	/**
	 * Returns if range contains give one
	 * 
	 * @param range
	 * @return
	 */
	public boolean containsRange(Range range) {
		if (!containsValue(range.getMin())) {
			return false;
		}
		if (!containsValue(range.getMax())) {
			return false;
		}
		return true;
	}

	/**
	 * Return range border end points values
	 * 
	 * @return
	 */
	public List<Integer> endPoints() {
		List<Integer> endPoints = new ArrayList<>();
		endPoints.add(getMinBorderVal());
		endPoints.add(getMaxBorderVal());
		return endPoints;
	}

	/**
	 * Return if range overlaps given one (intersection)
	 * 
	 * @param range
	 * @return
	 */
	public boolean overlapsRange(Range range) {
		// Intersection with range
		if (containsValue(range.getMin())) {
			return true;
		}
		if (containsValue(range.getMax())) {
			return true;
		}

		// Range intersection with this
		if (range.containsValue(min)) {
			return true;
		}
		if (range.containsValue(max)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((borderMax == null) ? 0 : borderMax.hashCode());
		result = prime * result + ((borderMin == null) ? 0 : borderMin.hashCode());
		result = prime * result + max;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Range other = (Range) obj;
		if (borderMax == null) {
			if (other.borderMax != null)
				return false;
		}
		else if (!borderMax.equals(other.borderMax))
			return false;
		if (borderMin == null) {
			if (other.borderMin != null)
				return false;
		}
		else if (!borderMin.equals(other.borderMin))
			return false;
		if (max != other.max)
			return false;
		if (min != other.min)
			return false;
		return true;
	}

}
