package org.range;

import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Range {
	public static final List<String> BORDERS_MIN = Arrays.asList("[", "(");
	public static final List<String> BORDERS_MAX = Arrays.asList("]", ")");

	private String[] args;
	private int min, max;
	private boolean minExclude, maxExcluded;

	Range(String[] rangeExpr) {
		validate(rangeExpr);
		this.args = rangeExpr;
		this.min = Integer.valueOf(this.args[1]);
		this.max = Integer.valueOf(this.args[1]);
		for (int i = 1; i <= this.args.length - 2; i++) {
			int val = Integer.valueOf(this.args[i]);
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
		return args.toString();
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

}
