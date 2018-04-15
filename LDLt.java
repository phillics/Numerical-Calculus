import java.util.*;

public class LDLt{
	public static void main(String[] args){
		double[][] A = {
			{4.0, -1.0, 1.0},
			{-1.0, 4.25, 2.75},
			{1.0, 2.75, 3.5}
		};
		LDLt ldlt = new LDLt(A, 3);
	}

	public LDLt(double[][] A, int n){
		double[] v = new double[n+1];
		double[] d = new double[n+1];
		double[][] l = new double[n+1][n+1];

		//initialize l matrix
		for(int i = 1; i <= n; i++)
			l[i][i] = 1.0;

		//perform factorization
		for(int i = 1; i <= n; i++){
			for(int j = 1; j <= i-1; j++)
				v[j] = l[i][j] * d[j];

			double sum = 0.0;
			for(int j = 1; j <= i-1; j++)
				sum += l[i][j] * v[j];

			d[i] = A[i-1][i-1] - sum;

			for(int j = i+1; j <= n; j++){
				sum = 0.0;
				for(int k = 1; k <= i-1; k++)
					sum += l[j][k] * v[k];

				l[j][i] = (A[j-1][i-1] - sum) / d[i];
			}

		}

		//print out results:
		//L D Lt
		System.out.println("matrix A:");
		printMatrix(A);

		System.out.println("\nL * D * Lt");
		for(int r = 1; r <= n; r++){
			System.out.printf("|");

			//print out row of L
			for(int c = 1; c <= n; c++)
				System.out.printf("  %.2f", l[r][c]);

			System.out.printf("\t|");

			//print out row of D
			for(int c = 1; c <= n; c++)
				if(c == r)
					System.out.printf("  %.2f", d[c]);
				else
					System.out.printf("  0.00");

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
