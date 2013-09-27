#include <iostream>
using namespace std;

void ifDecision() {
	int num;
	cout << "Give me an even number: ";
	cin >> num;
	if ((num % 2) != 0) {
		cout << "Idiot!" << endl;
		return;
	}
	cout << "Thank you." << endl;
}

void whileDecision() {
	int num, strikes = 0;
	cout << "Even #: ";
	cin >> num;
	while ((num % 2) != 0) {
		strikes ++;
		if (strikes >=3) {
			cout << "you are out idiot!" << endl;
			return;
		}
		cout << "Wrong! Try again: ";
		cin >> num;
	}
	cout << "Thank you!" << endl;
	return;
}

int main() {
	ifDecision();
	whileDecision();
	return 0;
}
