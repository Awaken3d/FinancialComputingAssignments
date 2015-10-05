package N10696443.souris.nicholas;

import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class Payout_calculator implements PayOut {

	// european option calculation, last day price
	public double getPayout(StockPath path) {
		List<Pair<DateTime, Double>> list = path.getPrices();
		int size = list.size();
		double price = (list.get(size - 1)).getValue();

		if (price > 165) {
			price = price - 165;

		} else {
			price = 0;
		}
		return price;

	}

	public double getPayout(StockPath path, boolean asian) {
		// asian option price calculation using average price
		List<Pair<DateTime, Double>> list = path.getPrices();
		double sum = 0;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			double price = (list.get(i)).getValue();
			sum += price;
		}

		double price = sum / size;
		if (price > 165) {
			price = price - 165;

		} else {
			price = 0;
		}
		return price;

	}
}
