//divided differences

import java.util.*;


public class DividedDifferences{

	double Ftable[][];
	double Xtable[];
	double x;
	final int size = 6;

	public DividedDifferences(){

		Xtable = new double[size];
		Ftable = new double[size][];

		for(int i = 0; i < size; i++)
			Ftable[i] = new double[i+1];

		Xtable[0] = 0.3;
		Xtable[2] = 0.32;
		Xtable[4] = 0.35;
		Xtable[1] = 0.3;
		Xtable[3] = 0.32;
		Xtable[5] = 0.35;
		//Xtable[3] = .75;
		//Xtable[4] = 2.2;

		Ftable[0][0] = 0.95534;
		Ftable[2][0] = 0.94924;
		Ftable[4][0] = 0.93937;
		Ftable[1][0] = 0.95534;
		Ftable[3][0] = 0.94924;
		Ftable[5][0] = 0.93937;
		//Ftable[3][0] = 1.91;
		//Ftable[4][0] = 0.1103623;

		double n, d;


		System.out.printf("%f\t%f\n", Xtable[0], Ftable[0][0]);
		for(int i = 1; i < size; i++){
			System.out.printf("%f\t%f\t", Xtable[i], Ftable[i][0]);

			for(int j = 1; j <= i; j++){
				n = Ftable[i][j-1] - Ftable[i-1][j-1];
				d = Xtable[i] - Xtable[i-j];
				Ftable[i][j] = n / d;

				System.out.printf("%f\t", Ftable[i][j]);
			}
			System.out.println();
		}


	}

	public static void main(String[] args){
		DividedDifferences diff = new DividedDifferences();
	}
}
