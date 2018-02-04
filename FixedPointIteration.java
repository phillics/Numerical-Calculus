import java.util.*;

public class FixedPointIteration{

	double p0, p, delta;
	int n;
	int MAX_STEPS = 50;

	public FixedPointIteration(){
		//find solution to p = g(p);

		p0 = 0.00;	//some initial value
		delta = .0000000001;

		n = 1;
		while(n < MAX_STEPS){
			p = g(p0);

			if(p > p0-delta && p < p0+delta){
				System.out.printf("Success: iteration %d: p = %.10f", n, p);
				break;
			}

			System.out.printf("Step %d: p = %.10f\tg(p) = %.10f\n", n, p0, p);

			p0 = p;
			n++;
		}

		if(n >= MAX_STEPS)
			System.out.println("FAILURE after " + n + " steps.");

	}

	public double g(double x){
		//return Math.pow(Math.pow(x, -1.0)-5, .25);
		return 1/(x*x*x*x +5);
	}

	public static void main(String[] args){
		FixedPointIteration FPI = new FixedPointIteration();
	}
}
