//bisection
import java.util.*;

public class Bisection{

	double a, b, p, delta, goal;
	int n;
	int MAX_STEPS = 50;

	public Bisection(){
		//starting values
		a = -2;
		b = 2;


		delta = .001;
		goal = 0.0;

		double expectedIterations = (b-a)/delta;
		System.out.printf("Expected Iterations: %.0f\n\n\n", expectedIterations);

		n = 1;
		while(n < MAX_STEPS){
			p = a+(b-a)/2;

			//check if close enough
			if(f(p) <= goal + delta && f(p) >= goal - delta){
				System.out.printf("SUCCESS: P-%d = %f\tf(P) = %f\n", n, p, f(p));
				break;
			}

			//print out current status
			System.out.printf("a = %f\tb = %f\tp-%d = %f\n", a, b, n, p);
			System.out.printf("%f\t%f\t%f\n\n", f(a), f(b), f(p));

			//make sure that goal is findable
			if((f(a) < goal && f(b) < goal ) || (f(a) > goal && f(b) > goal)){
				System.out.printf("FAILURE: CANNOT DETERMINE SOLUTION IN RANGE\n");
				break;
			}

			//take another step in right direction
			//if between a and p
			if(f(a) < goal){
				if(f(p) < goal)
					a = p;
				else
					b = p;
			}else{
				if(f(p) > goal)
					a = p;
				else
					b = p;
			}


			n++;
		}
	}

	public static void main(String[] args){
		Bisection bi = new Bisection();

	}

	public double f(double x){
		return x*x*x + 0.2*x*x - 0.2*x - 1.2;
	}

}
