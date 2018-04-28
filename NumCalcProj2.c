/*
Christian Phillips
COT 4500
Project 2
*/

#include <stdio.h>
#define N 4 //dimension of matrix

void LU(double[N][N], double[N][N], int);
void LDLt(double[N][N]);
void MatrixMult(double[N][N], double[N][N], double[N][N]);
void MatrixTranspose(double[N][N], double[N][N]);
void cloneMatrix(double[N][N], double[N][N]);
void printMatrix(double[N][N]);
void exchangeRows(double[N][N], int, int);

int main(void){

	//create matrix
	double matrix[N][N] =
	//demonstrates LU factorization with permutation matrix
	//doesn't work with LDLt factorization...
	/*{{0, 0, -1, 1},
	{1, 1, -1, 2},
	{-1, -1, 2, 0},
	{1, 2, 0, 2}};*/

	//example from book for LU factorization
	{{1, 1, 0, 3},
	{ 2, 1, -1, 1},
	{ 3, -1, -1, 2},
	{-1, 2, 3, -1}};

	//example from book for LDLt factorization
	//need to change N to 3
	/*{{4.0, -1.0, 1.0},
	{-1.0, 4.25, 2.75},
	{1.0, 2.75, 3.5}};*/

	double Lreturn[N][N];

	LU(matrix, Lreturn, 1); //pass in matrix and Lreturn because C is stupid
	LDLt(matrix);

	return 0;
}

void LU(double A[N][N], double Lreturn[N][N], int verbose){
	//copy matrix so we aren't making changes to original
	double U[N][N] = {0.0};
	double P[N][N] = {0.0};
	double L[N][N] = {0.0};
	double MultRes[N][N] = {0.0};
	int NROW[N] = {0}; //row pointer to simulate exchange
	int i, j, k, p;
	double pivot, tmp;
	int permNeeded = 0;
	//copy values of A into U
	cloneMatrix(U, A);

	if(verbose){
		printf("\n---------------------------\n");
		printf("PERFORMING LU FACTORIZATION\n");
		printf("---------------------------\n\n");
		printf("Original Matrix A:\n");
		printMatrix(A);
	}

	//set diagonal of L to 1, set row pointer to row
	for(i = 0; i < N; i++){
		L[i][i] = 1.0;
		NROW[i] = i;
	}

	//perform GaussianElimination on U,
	//update L with opperations performed for reduction
	for(i = 0; i < N-1; i++){
		//find next value to use for elimination
		for(p = i; p < N; p++)
			if(U[p][i] != 0)
				break;

		if(p >= N){ //if no value found
			printf("NO UNIQUE SOLUTION\n");
			return;
		}

		if(p != i){
			printf("Exchange Rows: %d, %d\n", i, p);
			permNeeded = 1;

			//exchange row pointers for Permutation Matrix
			tmp = NROW[i];
			NROW[i] = NROW[p];
			NROW[p] = tmp;

			//exchange rows in matrix
			exchangeRows(U, p, i);
		}

		//use pivot to perform row reduction
		for(j = i+1; j < N; j++){
			pivot = U[j][i] / U[i][i];
			L[j][i] = pivot;

			//reduce
			for(k = i; k < N; k++)
				U[j][k] = U[j][k]-pivot * U[i][k];
		}
	}//end GaussianElimination

	//print out results
	if(permNeeded){
		//print out perm matrix
		printf("\nPermutation Matrix:\n");
		for(i = 0; i < N; i++){
			for(j = 0; j < N; j++){
				if(NROW[i] == j){
					printf("1  ");
					P[i][j] = 1.0;
				}
				else{
					printf("0  ");
					P[i][j] = 0.0;
				}
			}
			printf("\n");
		}
		printf("\n");

		MatrixMult(P, A, MultRes);
		printf("Matrix P * A is:\n");
		printMatrix(MultRes);

		//perform LU factorization on U, store result in L
		LU(MultRes, L, 0);

		//L now needs to be multiplied by P transpose
		MatrixTranspose(P, P); //calculate P transpose, store result in P
		MatrixMult(P, L, L); //multiply P x L, store result in L

		printf("Pt * ");
	}

	if(verbose){
		printf("L * U:\n");

		//print out LU matricies
		for(i = 0; i < N; i++){
			for(j = 0; j < N; j++)
				printf("%3.2lf\t", L[i][j]);
			printf("|\t");
			for(j  = 0; j < N; j++)
				printf("%3.2lf\t", U[i][j]);
			printf("\n");
		}
		printf("\n");
	}

	cloneMatrix(Lreturn, L);
}

void LDLt(double matrix[N][N]){
	double V[N] = {0.0};
	double D[N] = {0.0};
	double L[N][N] = {0.0};
	double sum = 0.0;
	int i, p, j, k, r, c;
	int NROW[N];

	//set diagonal of L to 1
	for(i = 0; i < N; i++)
		L[i][i] = 1.0;

	//Perform Factorization
	for(i = 0; i < N; i++){
		for(j = 0; j <= i-1; j++)
			V[j] = L[i][j] * D[j];

		sum = 0.0;
		for(int j = 0; j <= i-1; j++)
			sum += L[i][j] * V[j];

		D[i] = matrix[i][j] - sum;

		for(j = i+1; j < N; j++){
			sum = 0.0;
			for(k = 0; k <= i-1; k++)
				sum += L[j][k] * V[k];

			L[j][i] = (matrix[j][i] - sum) / D[i];
		}
	} //END FACTORIZATION

	//print out results:
	//L D Lt
	printf("\n-----------------------------\n");
	printf("PERFORMING LDLt FACTORIZATION\n");
	printf("-----------------------------\n");
	printf("Original Matrix A:\n");
	printMatrix(matrix);

	printf("L * D * Lt\n");
	for(r = 0; r < N; r++){
		//print out row of l
		for(c = 0; c < N; c++)
			printf("%3.2lf\t", L[r][c]);
		printf("|\t");

		//print out row of d
		for(c = 0; c < N; c++)
			printf("%3.2lf\t", (r == c)? D[c] : 0.00);
		printf("|\t");

		//print out row of Lt
		for(c = 0; c < N; c++)
			printf("%3.2lf\t", L[c][r]);
		printf("\n");
	}

}

void MatrixMult(double A[N][N], double B[N][N], double ret[N][N]){
	int r, c, k;
	double R[N][N] = {0.0};
	double sum;
	for(r = 0; r < N; r++){
		for(c = 0; c < N; c++){
			sum = 0.0;
			for(k = 0; k < N; k++)
				sum = sum + A[r][k] * B[k][c];
			R[r][c] = sum;
		}
	}
	cloneMatrix(ret, R);
}

void exchangeRows(double A[N][N], int r1, int r2){
	int i;
	double tmp;
	for(i = 0; i < N; i++){
		tmp = A[r1][i];
		A[r1][i] = A[r2][i];
		A[r2][i] = tmp;
	}
}

void MatrixTranspose(double A[N][N], double ret[N][N]){
	double T[N][N] = {0.0};
	int r, c;
	for(r = 0; r < N; r++)
		for(c = 0; c < N; c++)
			T[r][c] = A[c][r];

	cloneMatrix(ret, T);
}

void cloneMatrix(double copy[N][N], double original[N][N]){
	int i, j;
	for(i = 0; i < N; i++)
		for(j = 0; j < N; j++)
			copy[i][j] = original[i][j];
}

void printMatrix(double matrix[N][N]){
	int i, j;
	for(i = 0; i < N; i++){
		for(j = 0; j < N; j++)
			printf("%3.2lf\t", matrix[i][j]);
		printf("\n");
	}
	printf("\n");
}
