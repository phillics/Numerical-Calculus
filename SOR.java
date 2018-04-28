import java.util.*;

public class SOR{

	public static void main(String[] args){
		double[][] A = {
			{4, 3, 0},
			{3, 4, -1},
			{0, -1, 4}
		};
		int n = 3;

		double[] XO = {1, 1, 1};
		double[] x = new double[n];
		double[] B = {24, 30, -24};

		double w = 1.25;
		final int MAX_ITER = 25;
		final double TOL = 0.000001;


		int k = 0;
		while(k <= MAX_ITER){
			for(int i = 0; i < n; i++){
				double firstSUM = 0.0, secondSUM = 0.0;

				for(int j = 0; j < i; j++)
					firstSUM += A[i][j] * x[j];

				for(int j = i+1; j < n; j++)
					secondSUM += A[i][j] * XO[j];

				x[i] = (1.0 - w)*XO[i] + (1.0 / A[i][i]) * w *(-firstSUM - secondSUM + B[i]);
			}

			double ERROR = 0.0;
			for(int i = 0; i < n; i++){
				if(Math.abs(x[i] - XO[i]) > ERROR)
					ERROR = Math.abs(x[i] - XO[i]);
				XO[i] = x[i];
			}

			if(ERROR < TOL){
				System.out.println("Success\n" + Arrays.toString(x));
				break;
			}

			System.out.println(Arrays.toString(x));

			k++;

		}

		if(k > MAX_ITER)
			System.out.println("Fail");


	}
}
