#include <iostream>
#include <cmath>
using namespace std;

void printSquare() {
	int c = 1;
	while(c<=100) {
		cout << c << "  " << c * c <<endl;
		c++;
	}	
}

void printSqrt() {
	int c = 1;
	while(c<=100) {
		cout << c << "  " << sqrt(c) << endl;
		c++;
	}
}

void printPower() {
	int c = 1, power = 2;
	while(c <= 20) {
		cout << c << "  " << power <<endl;
		c++;
		power = power * 2;
	}
}

int main() {
	cout << "Call  printSquare()" << endl;
	printSquare();
	cout << "Call  printSqrt()" << endl;
	printSqrt();
	cout << "Call  printPower()" << endl;
	printPower();
	return 0;
}
