#include <iostream>
using namespace std;
void findCents() {
	int x, d, n, q, p;
	cout << "How many cents do you want in charge? ";
	cin >> x;
	q = x / 25;
	x = x % 25;
	d = x / 10;
	x = x % 10;
	n = x / 5;
	x = x % 5;
	p = x;
	cout << "You need " << q << " Quarter(s) " << d << " Dim(s) "
		<< n << " Nickel(s) and " << p << " Pennie(s)." << endl;
	return;;
}

void makeDecisions() {
	int age;
	cout << "What is your Age? ";
	cin >> age;
	if (age > 100) 
		cout << "Liar!" << endl;
	else
		cout << "You are " << age << ". Thank you. " << endl;
	return;
}

int main() {
	findCents();
	makeDecisions();
	return 0;
}

