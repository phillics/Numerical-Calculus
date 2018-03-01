//Romberg integration
import java.util.*;

public class RombergIntegration{

	//input a, b, integer n
	double a, b;
	double h;
	int n = 3;
	double R[][];

	public RombergIntegration(){

		a = 1.0;
		b = 1.5;
		R = new double[n+1][n+1];

		h = b - a;
		R[1][1] = (h / 2.0)*(f(a)+f(b));
		System.out.printf("%.8f\n", R[1][1]);

		for(int i = 2; i <= n; i++){
			R[2][1] = (.5) * TrapezoidalMethod(i);

			for(int j = 2; j <= i; j++){
				R[2][j] = R[2][j-1] + (R[2][j-1] - R[1][j-1]) / (Math.pow(4.0, (double) j-1.0)-1);
			}

			for(int j = 1; j <= i; j++)
				System.out.printf("%.8f\t", R[2][j]);
			System.out.println();

			h = h / 2.0;
			 for(int j = 1; j <= i; j++)
			 	R[1][j] = R[2][j];

		}



	}

	private double TrapezoidalMethod(int i){

		double sum = 0.0;
		//calculate end point 2^(i-2)
		switch(i){
			case 2: i = 1;
				break;
			case 3: i = 2;
				break;
			case 4: i = 4;
				break;
			case 5: i = 8;
				break;
			case 6: i = 16;
				break;
			case 7: i = 32;
				break;
			default: i = 0;
				break;
		}

		for(int k = 1; k <= i; k++)
			sum += f(a + ((double)k - 0.5)*h);

		return R[1][1] + h * sum;
	}

	private double f(double x){
		return x * x * Math.log(x);
		//return Math.sin(x);
	}

	public static void main(String[] args){
		RombergIntegration ri = new RombergIntegration();
	}
}
