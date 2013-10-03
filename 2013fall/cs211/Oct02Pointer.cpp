#include <iostream>
using namespace std;
int main() {
	int a[3] = {10, 20, 30};
	for (int i=0; i<3; i++) {
		cout << a[i] <<endl;
		cout << "Don't use like i[a]: " << i[a] << endl; // a(i) = *(a+i) = *(i+a)
		cout << "Element a[" << i << "] address: " << a + i << endl;
		cout << "Use * to get element a[" << i << "] value: " <<*(a+i) <<endl;
	}
	int b[3][2] = {10, 20, 30, 40, 50, 60};
	for (int i=0; i<3; i++) {
		for (int j=0; j<2; j++) {
			cout << "b[" << i << "][" << j << "]=" << b[i][j] << endl;
			cout << "b[" << i << "][" << j << "]=" << *(b[i] + j) << endl;
			cout << "b[" << i << "][" << j << "]=" << *(*(b+i) +j) << endl;
		}
	}
	return 0;
}
