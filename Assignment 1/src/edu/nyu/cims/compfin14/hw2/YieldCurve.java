package edu.nyu.cims.compfin14.hw2;

import java.util.TreeMap;

public class YieldCurve {

	TreeMap<Double, Double> yield = new TreeMap<Double, Double>(); // using
																	// treemap
																	// to get
																	// sorted
																	// keys,
																	// which
																	// will make
																	// it easier
																	// to use
																	// during
																	// interpolation
	Bond[] bonds;

	public YieldCurve(TreeMap<Double, Double> yield) {
		this.yield = yield;
	}

	public YieldCurve(Bond[] bonds) {
		this.bonds = bonds;
		buildYieldCurve(bonds);
	}

	public void buildYieldCurve(Bond[] bonds) {
		for (int i = 0; i < bonds.length; i++) {
			if (bonds[i].payFrequency == 0) {
				yield.put(
						bonds[i].getMaturity(),
						Math.log1p((bonds[i].getFaceValue() / bonds[i]
								.getPrice()) - 1) / bonds[i].getMaturity());
			}
		}
	}

	public double getInterestRate(double time) { // if time is already an
													// existing key then the
													// value is returned.
													// Otherwise interpolation
													// is used after making sure
													// that the given time is
													// not lower than the
													// smallest key or higher
													// than the bigger key.
		double low, top = 0.0, mid, midValue;

		if (yield.get(time) != null)
			return yield.get(time);

		if (yield.floorKey(time) != null) {
			low = yield.floorKey(time);
		} else {
			return yield.firstEntry().getValue();
		}

		if (yield.ceilingKey(time) != null) {
			top = yield.ceilingKey(time);
		} else {
			return yield.lastEntry().getValue();
		}

		while (true) {
			mid = (low + top) / 2;
			midValue = (yield.get(low) + yield.get(top)) / 2;
			yield.put(mid, midValue);
			if (mid == time)
				return midValue;
			else if (time > mid)
				low = mid;
			else if (time < mid)
				top = mid;
		}

	}

	public double getForwardRate(double t0, double t1) {

		// returns forward rate between two dates Rt1*T1/Rt0*To(T1-T0)
		return (yield.get(t1) * t1) / (yield.get(t0) * t0 * (t1 - t0));
	}

	public double getDiscountFactor(double t) {
		// returns discount factor for a given duration e^T*T
		return Math.pow(Math.E, getInterestRate(t) * t);

	}

	public String toString() {
		String s = "";
		for (Double i : yield.keySet()) {
			s += "for time: " + i + " the rate is: " + yield.get(i) + "\n";
		}
		return s;
	}

}
