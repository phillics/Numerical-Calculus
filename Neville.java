import java.util.*;

public class Neville{

	final int size = 3;
	double[][] Qtable;
	double[] Xtable;
	double x;

	Neville(){

		Qtable = new double[size][];
		for(int i = 0; i < size; i++)
			Qtable[i] = new double[i+1];

		Xtable = new double[size];

		/*
		i,j

		0,0
		1,0		1,1
		2,0		2,1		2,2
		3,0		3,1		3,2		3,3
		...

		*/

		//assign initial values
		Xtable[0] = 2.0;
		Xtable[1] = 2.2;
		Xtable[2] = 2.3;
		//Xtable[3] = 8.7;
	//	Xtable[4] = 2.2;

		Qtable[0][0] = .6931;
		Qtable[1][0] = .7885;
		Qtable[2][0] = .8329;
		//Qtable[3][0] = 18.82091;
		//Qtable[4][0] = 0.1103623;

		x = 2.1;

		//calculate the rest of the table
		double n, d;

		System.out.printf("%f\n", Qtable[0][0]);

		for(int i = 1; i < size; i++){
			System.out.printf("%f\t", Qtable[i][0]);

			for(int j = 1; j <= i; j++){
				n = (x - Xtable[i-j]) * Qtable[i][j-1] - (x - Xtable[i]) * Qtable[i-1][j-1];
				d = Xtable[i] - Xtable[i-j];
				Qtable[i][j] = n / d;

				System.out.printf("%f\t", Qtable[i][j]);
			}

			System.out.println();
		}

	}



	public static void main(String[] args){
		Neville nel = new Neville();
	}
}
