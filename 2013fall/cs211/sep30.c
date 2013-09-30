#include <iostream>
using namespace std;

void swap(int &i, int &j) {
	int t = i;
	i = j;
	j = t;
}

// need figure out why it doesn't work
void swap2(int* i, int* j) {
	int *t;
	t = (int *)i;
	i = (int *)j;
	j = (int *)t;
	cout << "ij(): " << *i << *j <<endl;
	cout << "ij(): " << i << j <<endl;
}

int main() {
	int i = 100;
	int *ip;
	ip = &i;
	cout << "i = "<< i << ", IP = " << ip << ", *IP = " << *ip <<endl;
	int j = 23;
	cout << "ij(10023):  " << i << j <<endl;
	swap(i, j);
	cout << "ij(23100): " << i << j <<endl;
	i = 300;
	j = 45;
	swap2(&i, &j);
	cout << "ij(45300): " << i << j <<endl;
	return 0;
}
