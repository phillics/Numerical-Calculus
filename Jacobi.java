import java.util.*;

public class Jacobi{

	public static void main(String[] args){
		double[][] A = {
			{10, -1, 2, 0},
			{-1, 11, -1, 3},
			{2, -1, 10, -1},
			{0, 3, -1, 8}
		};

		int n = 4;

		double[] XO = {0, 0, 0, 0};
		double[] x = new double[n];
		double[] B = {6, 25, -11, 15};
		final double TOL = 0.0001;
		final int MAX_ITER = 15;

		int k = 0;
		while(k <= MAX_ITER){
			for(int i = 0; i < n; i++){
				double sum = 0.0;

				for(int j = 0; j < n; j++){
					if(j == i)
						continue;
					sum += A[i][j] * XO[j];
				}

				x[i] = (1.0 / A[i][i]) * (-sum + B[i]);
			}


			//if x -
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
			System.out.println("Unsuccessful");

	}
}
