#include <iostream>
using namespace std;
int main() {
	int age;
	cout << "How old are you? ";
	cin >> age;
	cout << "Thank you. You are ";
	cout << age * 365 * 24 * 60 * 60;
	cout << " seconds old." << endl;
	return 0;
}
