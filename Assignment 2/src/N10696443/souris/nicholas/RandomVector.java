package N10696443.souris.nicholas;

import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

public class RandomVector implements RandomVectorGenerator {

	public double[] getVector() {
		double[] vector = new double[252];
	
		RandomGenerator rg = new JDKRandomGenerator();

		for (int i = 0; i < vector.length; i++) {
			vector[i] = rg.nextGaussian();
		}

		return vector;
	}
}
