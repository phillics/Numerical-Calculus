import java.util.*;

public class Cholesky{
	public static void main(String[] args){
		double[][] A = {
			{4.0, -1, 1.0},
			{-1.0, 4.25, 2.75},
			{1.0, 2.75, 3.5}
		};
		Cholesky c = new Cholesky(A, 3);
	}

	public Cholesky(double[][] A, int n){
		double[][] l = new double[n+1][n+1];

		l[1][1] = Math.sqrt(A[0][0]);

		for(int j = 2; j <= n; j++)
			l[j][1] = A[j-1][0] / l[1][1];

		for(int i = 2; i <= n-1; i++){
			double sum = 0.0;
			for(int k = 1; k <= i -1; k++)
				sum += l[i][k] * l[i][k];

			l[i][i] = Math.sqrt(A[i-1][i-1] - sum);

			for(int j = i+1; j <= n; j++){
				sum = 0.0;
				for(int k = 1; k <= i-1; k++)
					sum += l[j][k] * l[i][k];

				l[j][i] = (A[j-1][i-1] - sum) / l[i][i];
			}
		}

		double sum = 0.0;
		for(int k = 1; k <= n-1; k++)
			sum += l[n][k] * l[n][k];

		l[n][n] = Math.sqrt(A[n-1][n-1] - sum);


		//print out result
		System.out.println("Matrix is:");
		printMatrix(A);
		System.out.println("\nL * Lt");

		for(int r = 1; r <= n; r++){
			System.out.printf("|");
			//print out row of L
			for(int c = 1; c <= n; c++)
				System.out.printf("  %.2f", l[r][c]);

			System.out.printf("\t|");
			//print out row of Lt
			for(int c = 1; c <= n; c++)
				System.out.printf("  %.2f", l[c][r]);

			System.out.printf("\t|\n");
		}

	}

	public void printMatrix(double[][] A){
		for(int r = 0; r < A.length; r++){
			for(int c = 0; c < A[r].length; c++)
				System.out.printf("%.2f\t", A[r][c]);
			System.out.println();
		}
		System.out.println();
	}
}
