package com.techjar.techtweaks.util;

public enum MetricUnit {
	NONE(1, 0, ""),
	// Decimal units
	ATTO(1000, -6, "a"),
	FEMTO(1000, -5, "f"),
	PICO(1000, -4, "p"),
	NANO(1000, -3, "n"),
	MICRO(1000, -2, "μ"),
	MILLI(1000, -1, "m"),
	KILO(1000, 1, "K"),
	MEGA(1000, 2, "M"),
	GIGA(1000, 3, "G"),
	TERA(1000, 4, "T"),
	PETA(1000, 5, "P"),
	EXA(1000, 6, "E"),
	// Binary units
	KIBI(1024, 1, "Ki"),
	MEBI(1024, 2, "Mi"),
	GIBI(1024, 3, "Gi"),
	TEBI(1024, 4, "Ti"),
	PEBI(1024, 5, "Pi"),
	EXBI(1024, 6, "Ei");
	
	public static final MetricUnit[] DECIMAL_UNITS = new MetricUnit[]{ATTO, FEMTO, PICO, NANO, MICRO, MILLI, NONE, KILO, MEGA, GIGA, TERA, PETA, EXA};
	public static final MetricUnit[] BINARY_UNITS = new MetricUnit[]{NONE, KIBI, MEBI, GIBI, TEBI, PEBI, EXBI};
	
	public final long magnitude;
	public final double magnitudeDouble;
	public final String prefix;
	
	MetricUnit(int mult, int power, String prefix) {
		this.magnitude = (long)Math.pow(mult, power);
		this.magnitudeDouble = Math.pow(mult, power);
		this.prefix = prefix;
	}
	
	public static long convertDecimal(long value) {
		for (int i = 0; i < DECIMAL_UNITS.length; i++) {
			MetricUnit unit = DECIMAL_UNITS[i];
			if (value <= unit.magnitude) return NONE.convertTo(value, unit);
		}
		return value;
	}
	
	public static long convertBinary(long value) {
		for (int i = 0; i < BINARY_UNITS.length; i++) {
			MetricUnit unit = BINARY_UNITS[i];
			if (value <= unit.magnitude) return NONE.convertTo(value, unit);
		}
		return value;
	}

	public static double convertDecimalDouble(long value) {
		for (int i = 0; i < DECIMAL_UNITS.length; i++) {
			MetricUnit unit = DECIMAL_UNITS[i];
			if (value <= unit.magnitudeDouble) return NONE.convertToDouble(value, unit);
		}
		return value;
	}

	public static double convertBinaryDouble(long value) {
		for (int i = 0; i < BINARY_UNITS.length; i++) {
			MetricUnit unit = BINARY_UNITS[i];
			if (value <= unit.magnitudeDouble) return NONE.convertToDouble(value, unit);
		}
		return value;
	}
	
	public static long convertFromTo(long value, MetricUnit from, MetricUnit to) {
		return (value * from.magnitude) / to.magnitude;
	}
	
	public long convertTo(long value, MetricUnit unit) {
		return convertFromTo(value, this, unit);
	}
	
	public long convertFrom(long value, MetricUnit unit) {
		return convertFromTo(value, unit, this);
	}
	
	/**
	 * Value may be inexact for large orders of magnitude.
	 */
	public static double convertFromToDouble(double value, MetricUnit from, MetricUnit to) {
		return (value * from.magnitudeDouble) / to.magnitudeDouble;
	}
	
	/**
	 * Value may be inexact for large orders of magnitude.
	 */
	public double convertToDouble(double value, MetricUnit unit) {
		return convertFromToDouble(value, this, unit);
	}
	
	/**
	 * Value may be inexact for large orders of magnitude.
	 */
	public double convertFromDouble(double value, MetricUnit unit) {
		return convertFromToDouble(value, unit, this);
	}
}
