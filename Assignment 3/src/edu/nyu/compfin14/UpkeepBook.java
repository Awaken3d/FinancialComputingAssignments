package edu.nyu.compfin14;

import java.util.TreeMap;

public class UpkeepBook {
	double bid;
	double ask;
	
	TreeMap<Double,Integer> bidbook = new TreeMap<Double,Integer>();
	TreeMap<Double,Integer> askbook = new TreeMap<Double,Integer>();
	//Sorted by price, first key is ask and last key would be bid(ascending order)
	private void showBid(String ticker,boolean display){
		//displays bid price if it is changed
		if( (bidbook.isEmpty() == false) && bid != bidbook.lastKey()){
			
			bid = bidbook.lastKey();
			if(display == true)
				System.out.println("Company name: "+ticker + " Bidding Price is: " + bid);
		}
	}
	
	
	
	private void showAsk(String ticker,boolean display){
		//displays ask price if it is changed
		if( (askbook.isEmpty() == false) && ask != askbook.firstKey()){
			ask = askbook.firstKey();
			if(display == true)
				System.out.println("Company name: "+ticker + " Asking Price is: " + ask);
		}
	}
	
	
	//after being stored in a hashtable the new order updates the book
	
	public void addBuyOrder(double price,int amount,String ticker,boolean display){
		if(bidbook.containsKey(price))
		{
			
			int next_amount = bidbook.get(price) + amount;
			bidbook.put(price,next_amount);
			
			if(next_amount == 0)
				bidbook.remove(price);
		}
	
		else{
			bidbook.put(price,amount);
		}
		
		showBid(ticker,display);
	}
	//after being stored in a hashtable the new order updates the book
	public void addSellOrder(double price,int amount,String ticker,boolean display){
		if(askbook.containsKey(price))
		{
			
			int next_amount = askbook.get(price) + amount;
			askbook.put(price,next_amount);
			
			if(next_amount == 0)
				askbook.remove(price);
		}
		
		else{
			askbook.put(price,amount);
		}
		
		showAsk(ticker,display);
	}
	
}
