/*
Given:
E1:		a00 x1	+	a01 x2 	+	...	+	a0n-1	xn	=	a1,n+1
E2:		a10 x1	+	a11 x2	+	...	+	a0n-1 xn	= 	a2,n+1
...										...			...
En:		an-1,0	x1	+	an1	x2	+	...	+	ann	xn	=	an,n+1
*/


import java.util.*;

public class GaussianElimination{
	public static void main(String[] args){
		GaussianElimination ge = new GaussianElimination();
	}


	public GaussianElimination(){
		double[][] A =
		{{1, -1, 2, -1, -8},
		{2, -2, 3, -3, -20},
		{1, 1, 1, 0, -2},
		{1, -1, 4, 3, 4}};

		//GE(A, 4);
		GEPartialPivot(A, 4);
		//GEScaledPivot(A, 4);
	}

	public void GEScaledPivot(double[][] A, int n){
		double[] x = new double[n+1]; //solution
		int[] NROW = new int[n+1]; 	  //row pointer, used to simulate exchange
		double[] s = new double[n+1]; //scale

		//initialize scale array
		for(int i = 0; i < n; i++){
			double max_val = A[i][0];

			for(int j = 0; j < n; j++)
				if(A[i][j] > max_val)
					max_val = A[i][j];

			s[i] = max_val;
			if(s[i] == 0){
				System.out.println("NO SOLUTION");
				return;
			}
		}

		//initialize row pointer
		for(int i = 0; i < n; i++)
			NROW[i] = i;

		//elimination process
		for(int i = 0; i < n-1; i++){

			printMatrix(A);

			//find p, the smallest integer such that:
			//	i <= p <= n
			// 	A[NROW[p], i] == MAX(A[NROW[j], i]) when i <= j <= n
			int p = i;
			double MAX_VAL = A[NROW[i]][i] / s[NROW[i]];
			for(int j = i; j < n; j++){
				double newVal = A[NROW[j]][i] / s[NROW[j]];
				if(newVal > MAX_VAL){
					MAX_VAL = newVal;
					p = j;
				}
			}

			if(A[NROW[p]][i] == 0){
				System.out.println("NO SOLUTION\n");
				return;
			}

			if(NROW[i] != NROW[p]){
				//exchange row pointers
				int NCPY = NROW[i];
				NROW[i] = NROW[p];
				NROW[p] = NCPY;
			}

			//perform operations on the rest of the rows.
			for(int j = i+1; j < n; j++){
				double pivot = A[NROW[j]][i] / A[NROW[i]][i];
				for(int k = i; k < n+1; k++)
					A[NROW[j]][k] = A[NROW[j]][k] - pivot * A[NROW[i]][k];
			}

		}//end elimination

		printMatrix(A);

		if(A[NROW[n-1]][n-1] == 0.0){
			System.out.println("NO UNIQUE SOLUTION");
			return;
		}

		//start backward substitution
		x[n-1] = A[NROW[n-1]][n] / A[NROW[n-1]][n-1];

		for(int i = n-2; i >= 0; i--){
			double sum = 0.0;
			for(int j = i+1; j < n; j++)
				sum += A[NROW[i]][j]*x[j];
			x[i] = (A[NROW[i]][n] - sum) / A[NROW[i]][i];
		}


		//print out solutions
		for(int i = 0; i < n; i++)
			System.out.printf("x%d = %.2f\n", i, x[i]);


	}

	public void GEPartialPivot(double[][] A, int n){
		double[] x = new double[n+1]; //solution
		int[] NROW = new int[n+1]; 	  //row pointer, used to simulate exchange

		//initialize row pointer
		for(int i = 0; i < n; i++)
			NROW[i] = i;

		//elimination process
		for(int i = 0; i < n-1; i++){

			printMatrix(A);

			//find p, the smallest integer such that:
			//	i <= p <= n
			// 	A[NROW[p], i] == MAX(A[NROW[j], i]) when i <= j <= n
			int p = i;
			double MAX_VAL = A[NROW[i]][i];
			for(int j = i; j < n; j++){
				if(A[NROW[j]][i] > MAX_VAL){
					MAX_VAL = A[NROW[j]][i];
					p = j;
				}
			}

			if(A[NROW[p]][i] == 0){
				System.out.println("NO SOLUTION\n");
				return;
			}

			if(NROW[i] != NROW[p]){
				//exchange row pointers
				int NCPY = NROW[i];
				NROW[i] = NROW[p];
				NROW[p] = NCPY;
			}

			//perform operations on the rest of the rows.
			for(int j = i+1; j < n; j++){
				double pivot = A[NROW[j]][i] / A[NROW[i]][i];
				for(int k = i; k < n+1; k++)
					A[NROW[j]][k] = A[NROW[j]][k] - pivot * A[NROW[i]][k];
			}

		}//end elimination

		printMatrix(A);

		if(A[NROW[n-1]][n-1] == 0.0){
			System.out.println("NO UNIQUE SOLUTION");
			return;
		}

		//start backward substitution
		x[n-1] = A[NROW[n-1]][n] / A[NROW[n-1]][n-1];

		for(int i = n-2; i >= 0; i--){
			double sum = 0.0;
			for(int j = i+1; j < n; j++)
				sum += A[NROW[i]][j]*x[j];
			x[i] = (A[NROW[i]][n] - sum) / A[NROW[i]][i];
		}


		//print out solutions
		for(int i = 0; i < n; i++)
			System.out.printf("x%d = %.2f\n", i, x[i]);


	}

	public void GE(double[][] A, int n){
		double[] x = new double[n+1]; //solution

		//elimination process
		for(int i = 0; i < n-1; i++){
			printMatrix(A);

			//let p be the smallest integer with i <= p <= n and Api != 0
			int p;
			for(p = i; p < n; p++)
				if(A[p][i] != 0)
					break;
			if(p >= n){
				System.out.println("NO UNIQUE SOLUTION");
				return;
			}

			//if p != i, exchange rows
			if(p != i)
				exchangeRows(A, p, i);

			for(int j = i+1; j < n; j++){
				//set Mji = Aji / Aii
				double pivot = A[j][i] / A[i][i];

				//perform (Ej - Mji Ei) -> Ej
				//for each value in row, reduce
				for(int k = i; k < n + 1; k++){
					A[j][k] = A[j][k] - pivot * A[i][k];
				}
			}
		}

		//We have now finished reduction
		printMatrix(A);

		if(A[n-1][n-1] == 0){
			System.out.println("NO UNIQUE SOLUTION");
			return;
		}

		//start backward substitution
		x[n-1] = A[n-1][n] / A[n-1][n-1];
		for(int i = n-2; i >= 0; i--){
			double sum = 0.0;
			for(int j = i+1; j < n; j++)
				sum += A[i][j]*x[j];
			x[i] = (A[i][n] - sum) / A[i][i];
		}
		//print out solutions
		for(int i = 0; i < n; i++)
			System.out.printf("x%d = %.2f\n", i, x[i]);

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
