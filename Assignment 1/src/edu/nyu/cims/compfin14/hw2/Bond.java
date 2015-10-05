package edu.nyu.cims.compfin14.hw2;

import java.util.Map;
import java.util.TreeMap;

public class Bond {
	private double Price, Coupon, Maturity, faceValue;
	double payFrequency;
	double[] yieldCurve = { 0.02, 0.023, 0.03 };
	Map<Double, Double> cashFlow = new TreeMap<Double, Double>();

	public Bond(double Price, double couponRate, double Maturity, double faceValue,
			double payFrequency) {
		this.Price = Price;
		this.Coupon = couponRate*faceValue;
		this.Maturity = Maturity;
		this.faceValue = faceValue;
		this.payFrequency = payFrequency;
	}

	public double getPrice() {		
		return Price;
	}

	public double getCoupon() {
		return Coupon;
	}

	public double getMaturity() {
		return Maturity;
	}

	public double getFaceValue() {
		return faceValue;
	}

	public Map<Double, Double> getCashFlow() {
		// key:time value:cashflow at that period
		//payFrequency of 0.0 means that the bond is zero coupon
		if (payFrequency == 0.0) {
			cashFlow.put(Maturity, faceValue);
			return cashFlow;
		} else {
			for (double i = payFrequency; i <= Maturity; i += payFrequency) {
				if(i==Maturity){
					cashFlow.put(i, (Coupon*payFrequency)+faceValue);
				}else{
					cashFlow.put(i, Coupon*payFrequency);					
				}
			}
		}
		return cashFlow;
	}
}
