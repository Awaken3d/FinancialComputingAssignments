package N10696443.souris.nicholas;

public class Stat_collector {
	public double getVolatility(double mean, double square_sum){
		return Math.sqrt( square_sum - Math.pow(mean,2));
	}
	public double getMean(double price, int n, double mean){
		return (mean * ( n - 1) + price ) / (double)n;
	}
	public double getsquare_sum(double price, int n,double square_sum){
		return (square_sum * ( n - 1) + price * price ) / (double)n;
	}
}
