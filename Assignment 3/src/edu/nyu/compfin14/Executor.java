package edu.nyu.compfin14;

import java.util.Hashtable;
import java.util.Iterator;

public class Executor {

	static Hashtable<String, Queue> orderInfo = new Hashtable<String, Queue>();
	static Hashtable<String, UpkeepBook> tickerInfo = new Hashtable<String, UpkeepBook>();
//this method just adds a new order and updates the correspond orderInfo and ask/bid book. Previous is the previous order element in the queue.
	private static void addNewOrder(Object obj, Queue previous, boolean display) {
		NewOrder newOrder = (NewOrder) obj;
		Queue element = new Queue();

		element.size = newOrder.getSize();
		element.idOrder = newOrder.getOrderId();
		element.ticker = newOrder.getSymbol();
		//if the company ticker exists then point to it else create a new book for that company
		if (!tickerInfo.containsKey(element.ticker)) {
			tickerInfo.put(element.ticker, new UpkeepBook());
		}
		UpkeepBook tick = tickerInfo.get(element.ticker);
		//check for price depending on whether the price is >0 or <0
		if (Double.isNaN(newOrder.getLimitPrice())) {
			element.limitPrice = (element.size > 0) ? tick.bidbook.lastKey()
					: tick.askbook.firstKey();
		}

		else {
			element.limitPrice = newOrder.getLimitPrice();
		}

		previous.next = element;
		element.previous = previous;
		previous = element;
		// placing id of order and object of order in order to achieve constant time search time
		orderInfo.put(element.idOrder, element);
		//using size to determine to which book the order should be placed
		if (element.size > 0) {
			tick.addBuyOrder(element.limitPrice, element.size, element.ticker,
					display);
		} else {
			tick.addSellOrder(element.limitPrice, element.size, element.ticker,
					display);
		}
	}
	//If the prices are equal the size is just changed, otherwise a new price is added
	private static void changeOrder(Queue element, int new_amount,
			double new_price, UpkeepBook tick, boolean display) {
		//if the price is the same then just make changes to the book
		//otherwise change the price to zero and update it.
		if (element.limitPrice == new_price) {

			if (element.size > 0) {
				tick.addBuyOrder(element.limitPrice, new_amount - element.size,
						element.ticker, display);
				element.size = new_amount;
			} else {
				tick.addSellOrder(element.limitPrice, new_amount - element.size,
						element.ticker, display);
				element.size = new_amount;
			}
		}

		else if (element.limitPrice != new_price) {
			//buy
			if (element.size > 0) {
				tick.addBuyOrder(element.limitPrice, (0 - element.size), element.ticker,
						display);
				element.size = new_amount;
				element.limitPrice = new_price;
				tick.addBuyOrder(new_price, new_amount, element.ticker, display);
			}
			//sell
			else {
				tick.addSellOrder(element.limitPrice, (0 - element.size),
						element.ticker, display);
				element.size = new_amount;
				element.limitPrice = new_price;
				tick.addSellOrder(new_price, new_amount, element.ticker, display);
			}
		}
	}

	private static void cancel_order(Queue elem, UpkeepBook tick,
			boolean display) {
		//updating bidbook
		// 0 - elem.size is to reduce the amount = elem.size in the bidbook 
		if (elem.size > 0) {

			tick.addBuyOrder(elem.limitPrice, (0 - elem.size), elem.ticker,
					display);
		} else {

			tick.addSellOrder(elem.limitPrice, (0 - elem.size), elem.ticker,
					display);
		}
		//deletion of element
		elem.previous.next = elem.next;

		if (elem.next != null) {
			elem.next.previous = elem.previous;
		}

		orderInfo.remove(elem.idOrder);
	}

	public static void Runable(boolean display, OrdersIterator a) {

		Iterator<Message> m;

		m = a.getIterator();

		Queue head = new Queue();

		Queue previous = head;
		// m.next() point to the order object
		// String name get the class name
		while (m.hasNext()) {

			Object obj = m.next();
			String name = obj.getClass().getSimpleName();

			if (name.compareTo("NewOrderImpl") == 0) {
				addNewOrder(obj, previous, display);
			}

			else if (name.compareTo("OrderCxRImpl") == 0) {

				OrderCxR cxrOrder = (OrderCxR) obj;

				String getId = cxrOrder.getOrderId();

				Queue node = orderInfo.get(getId);
				UpkeepBook tick = tickerInfo.get(node.ticker);
				// size = 0 means cancel order
				if (cxrOrder.getSize() == 0) {
					cancel_order(node, tick, display);
				}
				// size != 0 means replace order
				else {
					changeOrder(orderInfo.get(getId), cxrOrder.getSize(),
							cxrOrder.getLimitPrice(), tick, display);
				}
			}
		}
	}

	public static void main(String[] args) {
		boolean display = true;
		OrdersIterator a = new OrdersIterator();
		Runable(display, a);

	}

}
