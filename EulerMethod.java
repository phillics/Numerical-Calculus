import java.util.*;

public class EulerMethod{

	double a, b, w, t, alpha, h;
	int N;

	public EulerMethod(){
		//end points
		a = 0.0;
		b = 1.0;

		N = 4;

		//initial value
		alpha = 1.0;

		//Step 1
		h = (b - a) / N;
		t = a;
		w = alpha;
		System.out.printf("y\'(%f) ~= %f\n", t, w);

		//Step 2
		for(int i = 1; i <= N; i++){
			w = w + h * f(t, w);
			t = a + i * h;
			System.out.printf("y\'(%f) ~= %f\n", t, w);
		}

	}
	public double f(double t, double y){
		//return y - t*t + 1.0;
		//return t*Math.pow(Math.exp(1.0), 3.0*t)-2*y;
		//return 1.0 + (t-y)*(t-y);
		//return 1.0 + y / t;
		return Math.cos(2*t) + Math.sin(3*t);
	}

	public static void main(String[] args){
		EulerMethod e = new EulerMethod();
	}
}
