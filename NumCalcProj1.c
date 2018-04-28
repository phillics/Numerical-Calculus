/*
Christian Phillips
COT 4500 Project 1

Newton forward difference
Hermite
Lagrange

Notes:
I would like to re-do this in C++ so I can do the rounding properly.

*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void Difference(double x[], double f[], int size, double value) {

	int i, j;
	double n, d; //numerator, denominator
	double approx;
	//create 2d array for table

	double** Table = malloc(sizeof(double*) * size);

	for (i = 0; i < size; i++) {
		Table[i] = malloc(sizeof(double) * size);

		Table[i][0] = f[i];
	}

	printf("Newton Forward Difference:\nx\t\tf(x)\n");
	printf("%.5f\t\t%.5f\n", x[0], Table[0][0]);
	for (i = 1; i < size; i++) {
		printf("%.5f\t\t%.5f\t\t", x[i], Table[i][0]);

		for (j = 1; j <= i; j++) {
			n = Table[i][j - 1] - Table[i - 1][j - 1];
			d = x[i] - x[i - j];
			Table[i][j] = n / d;

			printf("%.5f\t\t", Table[i][j]);
		}
		printf("\n");
	}

	printf("\nP_%d(x) = %.5f", size - 1, Table[0][0]);
	for (i = 1; i < size; i++)
		printf(" %c %.5f (x - %.2f)", (Table[i][i] > 0) ? '+' : '-', fabs(Table[i][i]), x[0]);
	printf("(x - %.2f)", x[1]);

	//hard coded... should be put into a loop for generic... good luck with that
	approx = Table[0][0] + Table[1][1] * (value - x[0]) + Table[2][2] * (value - x[1]) * (value - x[1]);
	printf("\nforward-difference P_%d(%.5f) = %.5f\n", size - 1, value, approx);
	printf("Relative error: %.8f\n\n\n", fabs(sin(value) - approx));


	for (i = 0; i < size; i++)
		free(Table[i]);
	free(Table);
	return;
}

void Hermite(double x[], double f[], double fprime[], int n, double value) {

	//need 2d array Q[2n+1][2n+1]
	n = n - 1; // n should be Number_of_numbers - 1

	int i, j;
	double approx, tmp;
	double Q[20][20];// = malloc(sizeof(double*) * (2 * n + 20)); //larger than it needs to be
	double *Z = calloc(2 * n + 2, sizeof(double));   //larger than it needs to be
	//for (i = 0; i < 2 * n + 1; i++)
	//	Q[i] = calloc(2 * n + 2, sizeof(double));

	for (i = 0; i <= n; i++) {
		Z[2 * i] = x[i];
		Z[2 * i + 1] = x[i];
		Q[2 * i][0] = f[i];
		Q[2 * i + 1][0] = f[i];
		Q[2 * i + 1][1] = fprime[i];
		if (i != 0) {
			Q[2 * i][1] = (Q[2 * i][0] - Q[2 * i - 1][0]) / (Z[2 * i] - Z[2 * i - 1]);
		}
	}

	for (i = 2; i <= 2 * n + 1; i++) {
		for (j = 2; j <= i; j++) {
			Q[i][j] = (Q[i][j - 1] - Q[i - 1][j - 1]) / (Z[i] - Z[i - j]);
		}
	}

	printf("Hermite Polynomial:\nH_5(x) = %.5f", Q[0][0]);
	approx = Q[0][0];
	for (i = 1; i <= 2 * n + 1; i++) {
		printf(" %c %.5f", (Q[i][i] > 0) ? '+' : '-', fabs(Q[i][i]));
		tmp = Q[i][i];
		//print out the rest
		for (j = 1; j <= i; j++) {
			if (j % 2 != 0) {
				printf("(x-%.2f)", x[j / 2]);
				tmp *= (value - x[j / 2]);
			}
			else {
				printf("^2");
				tmp *= (value - x[j / 2]);
			}
		}
		approx += tmp;
	}

	printf("\nHermite H_5(%.2f) = %.5f\n", value, approx);

	printf("Relative error: %.8f\n\n\n", fabs(sin(value) - approx));

	free(Z);
	return;
}

void Lagrange(double x[], double f[], int n, double value) {
	double a = 1.0, b, c;
	double P[3];
	double denom, approx;

	printf("Lagrange polynomial:\n");
	//L0 calculations
	denom = (x[0] - x[1]) * (x[0] - x[2]);
	b = -(x[2] + x[1]);
	c = x[2] * x[1];
	printf("L0 = %f x^2 + %f x + %f\n", a / denom, b / denom, c / denom);
	P[0] = (a / denom)*f[0];
	P[1] = (b / denom)*f[0];
	P[2] = (c / denom)*f[0];


	//L1 calculations
	denom = (x[1] - x[0])*(x[1] - x[2]);
	b = -(x[2] + x[0]);
	c = x[2] * x[0];
	printf("L1 = %f x^2 + %f x + %f\n", a / denom, b / denom, c / denom);
	P[0] += (a / denom)*f[1];
	P[1] += (b / denom)*f[1];
	P[2] += (c / denom)*f[1];


	//L2 calculations
	denom = (x[2] - x[0])*(x[2] - x[1]);
	b = -(x[1] + x[0]);
	c = x[1] * x[0];
	printf("L2 = %f x^2 + %f x + %f\n", a / denom, b / denom, c / denom);
	P[0] += (a / denom)*f[2];
	P[1] += (b / denom)*f[2];
	P[2] += (c / denom)*f[2];

	printf("\nP_%d(x) = %f x^2 + %f x + %f\n", n - 1, P[0], P[1], P[2]);
	approx = P[0] * value*value + P[1] * value + P[2];
	printf("P_%d(%.2f) = %.5f\n", n - 1, value, approx);

	printf("Relative error: %.8f\n\n\n", fabs(sin(value) - approx));
	return;
}



int main() {
	double X[] = { 2, 5, 0.35 };
	double F[] = { 4, 1, 0.34290 };
	double Fprime[] = { 0.95534, 0.94924, 0.93937 };
	double value = 3;

	int i;
	//Forward-difference
	//Difference(X, F, 3, value);

	//Lagrange
	Lagrange(X, F, 1, value);

	//Hermite
	//Hermite(X, F, Fprime, 3, value);

	return 0;
}
