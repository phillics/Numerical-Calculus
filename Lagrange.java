//lagrange

import java.util.*;

public class Lagrange{

	double x, x0, x1, x2, x3, x4, x5;
	double f0, f1, f2, f3, f4, f5;
	double P[];
	double a, b, c, d, e;
	double denom;


	public Lagrange(){

		//initial values.. change these
		int degree = 2;
		P = new double[degree + 1];
		x = 0.43;

		x0 = 0.3;
		x1 = 0.32;
		x2 = 0.35;
		//x3 = 0;
		//x4 = 0;

		f0 = 0.95534;
		f1 = 0.94924;
		f2 = 0.03037;
		//f3 = ;
		//f4 = ;

		a = 1.0; //don't change this...

		if(degree == 1){
			//L0 calculations
			b = -x1;
			denom = x0 - x1;
			System.out.printf("L0 = %f x + %f\n", a / denom, b / denom);
			P[0] = (a / denom) * f0;
			P[1] = (b / denom) * f0;

			//L1 calculations
			b = -x0;
			denom = x1 - x0;
			System.out.printf("L1 = %f x + %f\n", a / denom, b / denom);
			P[0] += (a/denom) * f1;
			P[1] += (b/denom) * f1;

			System.out.printf("\nP(x) = %f x + %f\n", P[0], P[1]);

			System.out.printf("\nP(%f) = %f\n", x, P[0] * x + P[1]);


		}else if(degree == 2){
			//L0 calculations
			denom= (x0 - x1) * (x0 - x2);
			b = -(x2 + x1);
			c = x2 * x1;
			System.out.printf("L0 = %f x^2 + %f x + %f\n", a / denom, b / denom, c / denom);
			P[0] = (a / denom)*f0;
			P[1] = (b / denom)*f0;
			P[2] = (c / denom)*f0;


			//L1 calculations
			denom= (x1 - x0)*(x1 - x2);
			b = -(x2 + x0);
			c = x2 * x0;
			System.out.printf("L1 = %f x^2 + %f x + %f\n", a / denom, b / denom, c / denom);
			P[0] += (a / denom)*f1;
			P[1] += (b / denom)*f1;
			P[2] += (c / denom)*f1;


			//L2 calculations
			denom= (x2 - x0)*(x2 - x1);
			b = -(x1 + x0);
			c = x1 * x0;
			System.out.printf("L2 = %f x^2 + %f x + %f\n", a / denom, b / denom, c / denom);
			P[0] += (a / denom)*f2;
			P[1] += (b / denom)*f2;
			P[2] += (c / denom)*f2;

			System.out.printf("\nP(x) = %f x^2 + %f x + %f\n", P[0], P[1], P[2]);
			System.out.printf("P(%f) = %f", x, P[0]*x*x + P[1]*x + P[2]);

		} else if(degree == 3){
			//L0 calculations
			//L1 calculations
			//L2 calculations
			//L3 calculations
		}
	}

	public static void main(String[] args){
		Lagrange l = new Lagrange();
	}
}
