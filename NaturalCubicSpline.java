//natural cubic spline

import java.util.*;

public class NaturalCubicSpline{

	private double x[];
	private double f[];
	private double h[], l[], u[], z[], a[];

	private double A[], B[], C[], D[];

	private int n;


	public NaturalCubicSpline(){
		n = 2;

		x = new double[n+1];
		f = new double[n+1];
		h = new double[n+1];
		l = new double[n+1];
		u = new double[n+1];
		z = new double[n+1];
		a = new double[n+1];

		A = new double[n+1];
		B = new double[n+1];
		C = new double[n+1];
		D = new double[n+1];

		//set values;
		x[0] = 0.0;
		x[1] = 0.25;
		x[2] = 0.5;

		f[0] = .101;
		f[1] = .117;
		f[2] = .227;

		//do calculations
		A[0] = f[0];
		A[1] = f[1];
		A[2] = f[2];

		for(int i = 0; i < n; i++){
			h[i] = x[i+1] - x[i];
		}

		for(int i = 1; i < n; i++){
			a[i] = (3 / h[i]) * (A[i+1] - A[i]) - (3 / h[i-1]) * (A[i] - A[i-1]);
		}

		l[0] = 1.0;
		u[0] = z[0] = 0.0;

		//step 4
		for(int i = 1; i < n; i++){
			l[i] = 2*(x[i+1] - x[i-1]) - h[i-1] * u[i-1];
			u[i] = h[i] / l[i];
			z[i] = (a[i] - h[i-1] * z[i-1]) / l[i];
		}

		//setp 5;
		l[n] = 1.0;
		z[n] = C[n] = 0.0;

		//step 6;
		for(int j = n-1; j >= 0; j--){
			C[j] = z[j] - u[j] * C[j+1];
			B[j] = (A[j+1] - A[j]) / h[j] - h[j] * (C[j+1] + 2 * C[j]) / 3;
			D[j] = (C[j+1] - C[j]) / (3* h[j]);
		}

		for(int i = 0; i < n; i++){
			System.out.printf("System %d\na = %f\nb = %f\nc = %f\nd = %f\n\n", i, A[i], B[i], C[i], D[i]);
		}

	}

	public static void main(String[] args){
		NaturalCubicSpline ncs = new NaturalCubicSpline();
	}
}
