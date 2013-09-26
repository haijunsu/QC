#include <iostream>
using namespace std;

/**
 * Read user's number e.g. 19683, print in reverse: 38691
 */
void reverseNumber() {
	int n;
	cout << "Give me your number: ";
	cin >> n;
	cout << "Your number is " << n << ". Reverse number is ";
	while (n>0) {
		cout << n%10;
		n = n / 10;
	}
	cout << endl;
}

/**
 * Find the first digit of user's number
 */
void findFirstDigit() {
	int n;
	cout << "Give me a positive number: ";
	cin >> n;
	cout << "Your number is " << n << ". The first digit is ";
	while (n>9)
		n = n/10;
	cout << n << endl;
}

/**
 * Print squares of 1 - 10
 */
void printSquares() {
	for (int c=1; c<=10; c++)
		cout << c <<"\t"<< c*c <<endl;
}

int main() {
	cout << "Call reverseNumber() "<<endl;
	reverseNumber();
	cout << "Call findFirstDigit()" << endl;
	findFirstDigit();
	cout << "Call printSquares()" << endl;
	printSquares();
	cout << "Done." <<endl;
	return 0;
}
