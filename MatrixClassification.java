import java.util.*;

public class MatrixClassification{
	public static void main(String[] args){
		double[][] A = {
			{ 7.0, 2.0, 0.0},
			{3.0, 5.0, -1.0},
			{0.0, 5.0, -6.0}
		};

		MatrixClassification mc = new MatrixClassification(A);
	}

	public MatrixClassification(double[][] A){
		printMatrix(A);

		System.out.println("\nMatrix is:");
		//check symmetric
		boolean symmetric = true;
		for(int r = 0; r < A.length; r++)
			for(int c = 0; c < A.length; c++)
				if(A[r][c] != A[c][r])
					symmetric = false;
		if(symmetric)
			System.out.println("symmetric");
		else
			System.out.println("NOT symmetric");

		//check diagonally dominant
		boolean strictlyDiagonallyDominant = true;
		boolean diagonallyDominant = true;

		for(int r = 0; r < A.length; r++)
			for(int c = 0; c < A[0].length; c++)
				if(A[r][r] <= A[r][c])
					strictlyDiagonallyDominant = false;
				else if(A[r][r] < A[r][c])
					diagonallyDominant = false;

		if(strictlyDiagonallyDominant)
			System.out.println("Strictly Diagonally Dominant");
		else if(diagonallyDominant)
			System.out.println("Diagonally Dominant");
		else
			System.out.println("NOT diagonally dominant");

		//Singular
		//A matrix is singular if and only if the Determinant is 0
		if(det(A, A.length, false) == 0.0){
			System.out.println("Singular");
		} else {
			System.out.println("NON-Singular");
		}



		//positive definite
		// very difficult to prove,
		// 		but we can easily prove certain matricies are not positive definite
		// if A is positive definite
		//      A has an inverse
		//      A is diagonally dominant
		//		diagonal is greater than zero
		//		Aij^2 < Aii * Ajj for each i != j

		//	A is positive definite if and only if det of each
		//		leading principal sub matricies has a positive Determinant
		boolean positiveDefinite = true;
		for(int n = 1; n < A.length; n++)
			if(det(A, n, false) <= 0.0)
				positiveDefinite = false;

		if(positiveDefinite)
			System.out.println("Positive Definite");
		else
			System.out.println("Not Positive Definite");

	}


	public double det(double[][] A, int n, boolean verbose){
		int  p, h, k, i, j;
		double d = 0.0;
		double[][] temp = new double[n][n];
		if(n == 1){
			d = A[0][0];
			if(verbose){
				printMatrix(A, n);
				System.out.println("Determinant = "+d+"\n");
			}
			return d;
		}
		else if (n == 2){
			d = A[0][0] * A[1][1] - A[0][1] * A[1][0];
			if(verbose){
				printMatrix(A, n);
				System.out.println("Determinant = "+d+"\n");
			}
			return d;
		}
		//n > 2
		for(p = 0; p < n ;p++){
			h = 0;
			k = 0;
			for(i = 1; i < n; i++){
				for(j = 0; j < n; j++){
					if(j == p) {continue;}
					temp[h][k] = A[i][j];
					k++;
					if(k == n-1){
						h++;
						k = 0;
					}
				}
			}
			d = d + A[0][p] * Math.pow(-1.0, (double) p)*det(temp, n-1, verbose);
		}
		if(verbose){
			printMatrix(A, n);
			System.out.println("Determinant = "+d+"\n");
		}
		return d;
	}

	public void printMatrix(double[][] A, int n){
		for(int r = 0; r < n; r++){
			for(int c = 0; c < n; c++){
				System.out.printf("%.2f\t", A[r][c]);
			}
			System.out.println();
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
