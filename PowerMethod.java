import java.util.*;

public class PowerMethod{

	public PowerMethod(double[][] A, double[] X, int n){
		double u = -1;				//eigenvalue
		final int MAX_ITER = 3;
		final double TOL = 0.00001;

		//find the smallest integer p with 1 <= p <= n and |Xp| = || x || infinity
		//find the index of the first, smallest value of X
		int p = 0;
		for(int i = 1; i < n; i++){
			if(Math.abs(X[i]) < Math.abs(X[p]))
				p = i;
		}

		//set x = x / x[p]
		for(int i = 0; i < n; i++){
			X[i] = X[i] / X[p];
		}
		int k = 0;

		while(k <= MAX_ITER){


			//y = Ax
			double[] Y = MatrixMult(A, X);
			u = Y[p];

			//find smallest integer p in Y
			p = 0;
			for(int i = 1; i < n; i++){
				if(Math.abs(Y[i]) < Math.abs(Y[p]))
					p = i;
			}

			if(Y[p] < TOL && Y[p] > -TOL){
				System.out.println("EigenVector: " + X + "\nA has eigenvalue 0, pick new vector X\n");
				break;
			}

			//determine error term
			double ERROR = 0.0;

			for(int i = 0; i < n; i++)
				if(Math.abs(X[i] - Y[i]/Y[p]) > ERROR)
					ERROR = Math.abs(X[i] - Y[i]/Y[p]);

			for(int i = 0; i < n; i++)
				X[i] = Y[i] / Y[p];

			System.out.println(k + "\t" + Arrays.toString(X) + "\t" + u + "\tERROR: " + ERROR);

			if( ERROR < TOL && ERROR > -TOL){
				System.out.println("procedure was successful");
				System.out.println("EigenValue: " + u + "\tEigenVector: " + Arrays.toString(X));
				break;
			}

			k = k+1;
		}

		if(k > MAX_ITER)
			System.out.println("Procedure was Unsuccessful");
	}

	public double[] MatrixMult(double[][] A, double[] B){
		//rows in A determines cols in R
		//cols in B determines rows in R

		double[] R = new double[A.length];

		int n = 0;
		if(A[0].length != B.length)
			return R;

		n = A[0].length;

		for(int r = 0; r < R.length; r++){
			double sum = 0.0;
			for(int k = 0; k < n; k++)
            	sum = sum + A[r][k] * B[k];
           	R[r] = sum;

		}

		return R;
	}


	public static void main(String[] args){
		double[][] A = {
			{1.1, 1, 1.1},
			{1, 1.1, 0},
			{1, 0, 1.1}
		};

		int n = 3;				//dimension
		double[] X = {-1, 0, 1};	//initial vector guess

		PowerMethod pm = new PowerMethod(A, X, n);
	}
}
