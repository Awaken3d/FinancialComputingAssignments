package edu.nyu.cims.compfin14.hw2;

import java.util.Map;

public class InfoGetter {
	public static double getPrice(YieldCurve ycm, Bond bond) {

		double price = 0;
		Map<Double, Double> cashFlow = bond.getCashFlow();

		for (Double time : cashFlow.keySet()) {
			price += cashFlow.get(time) / ycm.getDiscountFactor(time);
		}
		return price;
	}

	public static double getYTM(Bond bond, double price) {
		double ytm;
		ytm = ((bond.getCoupon()) + ((bond.getFaceValue() - price) / bond
				.getMaturity())) / ((bond.getFaceValue() + price) / 2);
		return ytm;

	}

	public static double getPrice(Bond bond, double ytm) {
		double Price = 0.0;

		for (Double time : bond.cashFlow.keySet()) {
			Price += bond.cashFlow.get(time) * Math.pow(Math.E,-time * ytm);
		}
		return Price;
	}
}
