package org.range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

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
		this.borderMin = rangeExpr[0];
		this.borderMax = rangeExpr[rangeExpr.length - 1];

		// Min / Max
		this.min = Integer.valueOf(rangeExpr[1]);
		this.max = Integer.valueOf(rangeExpr[1]);
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
		return this.borderMin + this.min + "," + this.max + this.borderMax;
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
		return BORDER_MIN_IN.equals(this.getBorderMin()) ? this.min : this.min + 1;
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
		return BORDER_MAX_IN.equals(this.getBorderMax()) ? this.max : this.max - 1;
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
		if (this.getBorderMin().equals(BORDER_MIN_IN) && this.min > value) {
			return false;
		}
		if (this.getBorderMin().equals(BORDER_MIN_OUT) && this.min >= value) {
			return false;
		}
		if (this.getBorderMax().equals(BORDER_MAX_IN) && this.max < value) {
			return false;
		}

		if (this.getBorderMax().equals(BORDER_MAX_OUT) && this.max <= value) {
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
		if (!this.containsValue(range.getMin())) {
			return false;
		}
		if (!this.containsValue(range.getMax())) {
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
		if (this.containsValue(range.getMin())) {
			return true;
		}
		if (this.containsValue(range.getMax())) {
			return true;
		}

		if (range.containsValue(this.min)) {
			return true;
		}
		if (range.containsValue(this.max)) {
			return true;
		}

		return false;
	}

}
