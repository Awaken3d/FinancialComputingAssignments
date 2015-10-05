package N10696443.souris.nicholas;

public class AntiDecorator {

	public static double[] getAntiVector(double[] vector) {

		double[] vector1 = new double[vector.length];

		for (int i = 0; i < vector.length; i++) {
			vector1[i] = -vector[i];
		}
		return vector1;
	}
}
