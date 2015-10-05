package edu.nyu.cims.compfin14.hw2;

import java.util.TreeMap;

public class Test {

	public static void main(String[] args) {
		new Test().go();

	}
	
	public void go(){
		TreeMap<Double, Double> yield = new TreeMap<Double, Double>();
		yield.put(1.0, 0.02);
		yield.put(2.0, 0.023);
		yield.put(3.0, 0.03);
		
		
		System.out.println("QUESTION 1: ");
		YieldCurve yc1 = new YieldCurve(yield);
		String s = yc1.toString();
		System.out.println(s);
		
		
		
		Bond[] bond = new Bond[2];
		bond[0] = new Bond(95,0,0.5, 100,0);
		bond[1] = new Bond(895, 0, 1, 1000, 0);
		YieldCurve yc = new YieldCurve(bond);
		System.out.println("QUESTION 2: ");
		s = yc.toString();
		System.out.println(s);
		System.out.println("Interest rate for 0.75 ");
		System.out.println(yc.getInterestRate(0.75)+"\n");
		
		Bond bond3 = new Bond(0, 0.05, 3,500,0.5);
		double Price = InfoGetter.getPrice(yc,bond3);		
		double ytm = InfoGetter.getYTM(bond3, Price);
		double Price2 = InfoGetter.getPrice(bond3, ytm);
		System.out.println("QUESTION 3: ");
		System.out.println("ytm is: "+ytm);
		System.out.println("price of question 3 is: "+Price);
		System.out.println("price with ytm is: "+Price2);
	}

}
