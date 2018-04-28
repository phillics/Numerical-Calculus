//det


import java.util.*;

public class Determinant{

	public static void main(String[] args){
		Determinant d = new Determinant();
	}

	public Determinant(){
		double[][] A =
		{{7, -2, 3},
		{1, 2, -3},
		{-3, 2, 1}};

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				A[i][j] = A[i][j] / 8.0;

		System.out.println("Determinant is: "+det(A, 3));
	}

	public double det(double[][] A, int n){
		int  p, h, k, i, j;
		double d = 0.0;
		double[][] temp = new double[n][n];
		if(n == 1) //
			return A[0][0];
		else if (n == 2)
			return A[0][0] * A[1][1] - A[0][1] * A[1][0];

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
			d = d + A[0][p] * Math.pow(-1.0, (double) p)*det(temp, n-1);
		}
		return d;
	}
}
