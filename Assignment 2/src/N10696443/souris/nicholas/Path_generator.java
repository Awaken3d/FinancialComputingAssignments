package N10696443.souris.nicholas;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class Path_generator implements StockPath {

	double[] vector = new double[252];

	public Path_generator(double[] vector) {
		this.vector = vector;

	}

	public List<Pair<DateTime, Double>> getPrices() {

		DateTime dt = new DateTime();

		List<Pair<DateTime, Double>> list = new ArrayList<Pair<DateTime, Double>>();
		// calculating price
		double previous_price = 152.35;
		double price;

		for (int day = 0; day < 252; day++) {

			DateTime this_day = dt.plusDays(day + 1);

			price = previous_price
					* Math.exp((0.0001 - 0.01 * 0.01 / 2 + 0.01 * vector[day]));
			list.add(new Pair<DateTime, Double>(this_day, price));
			previous_price = price;
		}
		return list;
	}

}
