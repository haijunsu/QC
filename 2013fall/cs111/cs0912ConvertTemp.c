#include <iostream>
using namespace std;
/**
 * Convert Celsius to Fahrenheit
 */
void convertToFahrenheit() {
	int c;
	double f;
	cout << "What is the Celsius temp? ";
	cin >> c;
	f = 32 + 9 * c / 5.0;
	cout << "Celsius " << c << " is " << f << " Fahrenheit." << endl;
}

/**
 * Convert Fahrenheit to Celsius
 */
void convertToCelsius() {
	int f;
	double c;
	cout << "What is the Fahrenheit temp? ";
	cin >> f;
	c = 5.0 * (f - 32) / 9;
	cout << "Fahrenheit " << f << " is " << c << " Celsius." << endl;
}

// main function
int main() {
	convertToFahrenheit();
	convertToCelsius();
	return 0;
}

