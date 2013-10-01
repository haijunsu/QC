#include <iostream>
using namespace std;

/**
 * Print sum of odd number from 1 to 99
 */
void printSumOdds();

/**
 * Print a trangle with "*"
 */
void printTrangle();

int main() {
	printSumOdds();
	printTrangle();
	return 0;
}

void printSumOdds() {
	int answer = 0;
	for (int r=0; r<=99; r+=2)
		answer += r;
	cout << "The sum is " << answer << endl;
}

void printTrangle() {
	for (int r=1; r<=10; r++) {
		for (int c=1; c<=r; c++)
			cout << "*";
		cout << endl;
	}
}
