import java.util.*;

public class LUFactorization{
	public static void main(String[] args){
		LUFactorization lu = new LUFactorization();
	}

	public LUFactorization(){
		double[][] A =
		{{0, 0, -1, 1},
		{1, 1, -1, 2},
		{-1, -1, 2, 0},
		{1, 2, 0, 2}};
		/*{{1, 1, 0, 3},
		{ 2, 1, -1, 1},
		{ 3, -1, -1, 2},
		{-1, 2, 3, -1}};*/

		System.out.println("MATRIX TO FACTOR:");
		printMatrix(A);

		LUF(A, 4);
	}

	public double[][] LUF(double[][] A, int n){
		double[][] L = new double[n][n];
		double[][] U = new double[n][n];

		//set diagonal of L to 1
		for(int i = 0; i < n; i++)
			L[i][i] = 1.0;

		//copy values from A into U
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				U[i][j] = A[i][j];

		int[] NROW = new int[n+1]; 	  //row pointer, used to simulate exchange

		//initialize row pointer
		for(int i = 0; i < n; i++)
			NROW[i] = i;

		//perform GaussianElimination on U
		//update L with opperations performed for reduction
		boolean permNeeded = false;

		for(int i = 0; i < n-1; i++){
			int p;
			for(p = i; p < n; p++)
				if(U[p][i] != 0)
					break;
			if(p >= n){
				System.out.println("NO UNIQUE SOLUTION\n");
				return L;
			}

			//if p != i, exchange rows
			if(p != i){
				System.out.printf("ROW EXCHANGE REQUIRED %d %d\n", i, p);
				permNeeded = true;
				//exchange row pointers for Permutation Matrix
				int NCPY = NROW[i];
				NROW[i] = NROW[p];
				NROW[p] = NCPY;

				exchangeRows(U, p, i);
			}


			for(int j = i+1; j < n; j++){
				//set Mji = Aji / Aii
				double pivot = U[j][i] / U[i][i];

				L[j][i] = pivot;

				//perform (Ej - Mji Ei) -> Ej
				//for each value in row, reduce
				for(int k = i; k < n; k++){
					U[j][k] = U[j][k] - pivot * U[i][k];
				}

			}

		}

		//print out matricies

		if(permNeeded){
			//print out perm matrix
			double[][] P = new double[n][n];

			System.out.println("\nPermutation Matrix:");
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++)
					if(NROW[i] == j){
						System.out.printf("  1");
						P[i][j] = 1.0;
					}
					else{
						System.out.printf("  0");
						P[i][j] = 0.0;
					}

				System.out.printf("\n");
			}
			System.out.printf("\n");


			//P = MatrixTranspose(P);

			U = MatrixMult(P, A);

			System.out.println("Matrix PA is:");
			printMatrix(U);

			//perform LU factorization on U
			L = LUF(U, n);

			//L now needs to be multiplied by P transpose
			P = MatrixTranspose(P);
			L = MatrixMult(P, L);


			System.out.printf("Pt * ");
		}

		System.out.println("L * U");
		//print out LU matricies
		for(int i = 0; i < n; i++){
			System.out.printf("|  ");
			for(int j = 0; j < n; j++)
				System.out.printf("%.2f  ", L[i][j]);
			System.out.printf("\t|  ");
			for(int j = 0; j < n; j++)
				System.out.printf("%.2f  ", U[i][j]);
				System.out.printf("\t|\n");
		}
		System.out.println();

		return L;

	}

	public double[][] MatrixTranspose(double[][] A){
		double[][] T = new double[A[0].length][A.length];

		for(int r = 0; r < T.length; r++)
			for(int c = 0; c < T[0].length; c++)
				T[r][c] = A[c][r];

	 	return T;
	}

	public double[][] MatrixMult(double[][] A, double[][] B){
		//rows in A determines cols in R
		//cols in B determines rows in R

		double[][] R = new double[A.length][B[0].length];

		int n = 0;
		if(A[0].length != B.length)
			return R;

		n = A[0].length;

		for(int r = 0; r < R.length; r++){
			for(int c = 0; c < R[0].length; c++){
				double sum = 0.0;
				for(int k = 0; k < n;k++)
               		sum = sum + A[r][k] * B[k][c];
           		R[r][c] = sum;

			}
		}

		return R;
	}

	public void exchangeRows(double[][] A, int r1, int r2){
		for(int i = 0; i < A[r1].length; i++){
			double tmp = A[r1][i];
			A[r1][i] = A[r2][i];
			A[r2][i] = tmp;
		}
	}

	public void printMatrix(double[][] A){
		for(int r = 0; r < A.length; r++){
			for(int c = 0; c < A[r].length; c++){
				System.out.printf("%.2f\t", A[r][c]);
			}
			System.out.println();
		}
		System.out.println();
	}



}
