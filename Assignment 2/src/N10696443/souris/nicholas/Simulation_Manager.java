package N10696443.souris.nicholas;

public class Simulation_Manager {
	public static void main(String[] arg) {

		double y = 2.064;
		int n = 0;

		double volatility = 0, square_sum = 0, mean = 0;
		Payout_calculator payout = new Payout_calculator();
		Stat_collector states = new Stat_collector();

		
		while (n < 1000 || (y * volatility) / Math.sqrt(n) > 0.01) {
			n++;
			RandomVector random_vector = new RandomVector();
			double[] vector = random_vector.getVector();

			double price = payout.getPayout(new Path_generator(vector));

			mean = states.getMean(price, n, mean);
			square_sum = states.getsquare_sum(price, n, square_sum);
			volatility = states.getVolatility(mean, square_sum);

			// using antithetic decorator to ensure desired average.
			n++;
			double[] nvector = AntiDecorator.getAntiVector(vector);

			double antiPrice = payout.getPayout(new Path_generator(nvector));

			mean = states.getMean(antiPrice, n, mean);
			square_sum = states.getsquare_sum(antiPrice, n, square_sum);
			volatility = states.getVolatility(mean, square_sum);
		}

		System.out.println("Problem's 1 option price: " + mean);

		n = 0;
		volatility = 0;
		square_sum = 0;
		mean = 0;

		Boolean asian_option = true;

		while (n < 1000 || (y * volatility) / Math.sqrt(n) > 0.01) {
			n++;
			RandomVector random_vector = new RandomVector();
			double[] vector = random_vector.getVector();

			double price = payout.getPayout(new Path_generator(vector),
					asian_option);

			mean = states.getMean(price, n, mean);
			square_sum = states.getsquare_sum(price, n, square_sum);
			volatility = states.getVolatility(mean, square_sum);

			n++;
			double[] nvector = AntiDecorator.getAntiVector(vector);

			double antiPrice = payout.getPayout(new Path_generator(nvector),
					asian_option);

			mean = states.getMean(antiPrice, n, mean);
			square_sum = states.getsquare_sum(antiPrice, n, square_sum);
			volatility = states.getVolatility(mean, square_sum);
		}

		System.out.println("Problem's 2 option price: " + mean);
	}
}
