#include <cstdlib> 
#include <climits>
#include <iostream> 
#include <cassert>
#include <fstream>
#include <sstream>
#include <vector>
#include "Matrix.cpp"

// CS381/780 Project 6 Submission: Su, Haijun

const int VNT_IS_FULL = 100;
const int VNT_IS_EMPTY = 200;

// main class
class VNT {
	private:
		int m, n;
		SafeMatrix<int> p;
	public:
		// 2 parameters constructor lets us write
		// VNT x(10, 20);
		VNT(int row, int col) {
			m = row;
			n = col;
			SafeMatrix<int> sm(row, col);
			for (int i=0; i<row; i++) {
				for (int j=0; j<col; j++) {
					sm(i, j) = INT_MAX;
				}
			}
			p = sm;
		}
		
		// destructor
		~VNT(){
			//delete p;
		}
		
		//check whether the VNT is empty
		bool isEmpty() {
			return p(0, 0) == INT_MAX;
		}
		
		// check whether the VNT is full;
		bool isFull() {
			return p(m-1, n-1) != INT_MAX;
		}
		
		// swap two values in VNT
		void swap(int row_1, int col_1, int row_2, int col_2) {
			int tmp = p(row_1, col_1);
			p(row_1, col_1) = p(row_2, col_2);
			p(row_2, col_2) = tmp;
		}
		
		// add an element to VNT
		void add(int value){
			// throw exception if VNT is full
			if (isFull()) throw (VNT_IS_FULL); //VNT_IS_FULL
			int cur_row = m - 1;
			int cur_col = n - 1;
			p(cur_row, cur_col) = value;
			while (!(cur_row == 0 && cur_col == 0)) {
				// compare left and up cells
				int left_value = INT_MAX;
				int up_value = INT_MAX;
				if (cur_row > 0) {
					up_value = p(cur_row-1, cur_col);
				}
				if (cur_col > 0) {
					left_value = p(cur_row, cur_col-1);
				}
				if (left_value > up_value) {
					if (left_value > value && cur_col > 0) {
						// swap to left
						p(cur_row, cur_col) = left_value;
						p(cur_row, cur_col-1) = value;
						cur_col--;
						continue;
					} else if (up_value > value && cur_row > 0) {
						// swap to up
						p(cur_row, cur_col) = up_value;
						p(cur_row-1, cur_col) = value;
						cur_row--;
						continue;
					}
				} else {
					if (up_value > value && cur_row > 0) {
						// swap to up
						p(cur_row, cur_col) = up_value;
						p(cur_row-1, cur_col) = value;
						cur_row--;
						continue;
					} else if (left_value > value && cur_col > 0) {
						// swap to left
						p(cur_row, cur_col) = left_value;
						p(cur_row, cur_col-1) = value;
						cur_col--;
						continue;
					}
				}
				// no swap, exit loop
				break;
			}
		}
		
		
		// get the minimum value, if VNT is empty, throw VNT_IS_EMPTY
		int getMin() {
			if (isEmpty()) throw (VNT_IS_EMPTY); // VNT is empty
			int rtn = p(0, 0);
			int cur_row = 0;
			int cur_col = 0;
			if (p(0,1) == INT_MAX && p(1,0) == INT_MAX) { // No more element in VNT
				p(0, 0) = INT_MAX;
				return rtn;
			}
			while (!(cur_row == (m-1) && cur_col == (n-1))) {
				// compare right and down cells
				int right_value = INT_MAX;
				int down_value = INT_MAX;
				if (cur_row < m-1) {
					down_value = p(cur_row+1, cur_col);
				}
				if (cur_col < n-1) {
					right_value = p(cur_row, cur_col+1);
				}
				if(right_value == INT_MAX && down_value == INT_MAX) {
					p(cur_row, cur_col) = INT_MAX;
					break; //no swap need
				}
				if (right_value > down_value) {
					if (cur_row < m-1) {
						// swap to down
						p(cur_row, cur_col) = down_value;
						p(cur_row+1, cur_col) = INT_MAX;
						cur_row++;
						continue;
					} else if (cur_col < n-1) {
						// swap to right
						p(cur_row, cur_col) = right_value;
						p(cur_row, cur_col + 1) = INT_MAX;
						cur_col++;
						continue;
					}
				} else {
					if (cur_col < n-1) {
						// swap to right
						p(cur_row, cur_col) = right_value;
						p(cur_row, cur_col + 1) = INT_MAX;
						cur_col++;
						continue;
					} else if (cur_row < m-1) {
						// swap to down
						p(cur_row, cur_col) = down_value;
						p(cur_row+1, cur_col) = INT_MAX;
						cur_row++;
						continue;
					}
				}
			}
			return rtn;
		}
		
		// sort integer array
		void sort(int k[], int size) {
			for (int i=0; i<size; i++) {
				add(k[i]);
			}
			for (int i=0; i<size; i++) {
				k[i] = getMin();
			}
		}
		
		// find an integer from VNT. Return true if value is in VNT
		bool find(int value) {
			// no element
			if (isEmpty()) return false;
			return subSearch(value, 0, m-1, 0, n-1);
		}
		
		// using binary search
		bool subSearch(int value, int rs, int re, int cs, int ce) {
			if (rs > re || cs > ce) return false; // out of range
			if (rs == re && cs == ce) return p(rs, cs) == value;
			int rm = (rs + re)/2;
			int cm = (cs + ce)/2;
			if (p(rm, cm) == value) return true;
			if (value > p(rm, cm)) {
				return subSearch(value, rm+1, re, cs, cm) || subSearch(value, rs, re, cm+1, ce);
			} else {
				return subSearch(value, rs, rm-1, cs, ce) || subSearch(value, rm, re, cs, cm-1);
			}
		
		}
		
		// overloads << so we can directly print VNT
		friend ostream& operator<< (ostream& os, VNT v);
};

// print VNT
ostream& operator<< (ostream& os, VNT v){
	os<< "m=" << v.m << ", n=" << v.n <<endl;
	os<< v.p;
    return os;
};


int main(int argc, char* argv[]){
 
	if (argc < 3) { //first argument(argv[0]) is the program path
		cerr << "usage: " << argv[0] << " <input file> <dest file>" << endl;
		return 0;
	}
       
	//note the arguments to main being used below
	ifstream input(argv[1]); //we'll assume the file exists
	ofstream output(argv[2]);
	char ch;
	input >> noskipws; //the noskipws flag allows for whitespace characters to be extracted
    string line;
	while (getline(input, line)){
		try {
			vector<int> values = splitLine(line);
			VNT v1(values.at(0), values.at(1));
			int size = values.at(2);
			getline(input, line);
			output << "Original array: " << line << endl;
			cout << "Original array: " << line << endl;
			values = splitLine(line);
			int* k = new int[size];
			for (int i=0; i<size; i++) {
				k[i] = values.at(i);
			}
			v1.sort(k, size);
			output << "Sorted array: ";
			cout << "Sorted array: ";
			for (int i=0; i<size; i++) {
				output << k[i] << " ";
				cout << k[i] << " ";
			}
			output << endl;
			cout << endl<<endl;
			for (int i=0; i<size; i++) {
				v1.add(values.at(i));
			}
			cout << v1 << endl;
			for (int i=0; i<size; i++) {
				cout << "find ("<< values.at(i) <<"):" << v1.find(values.at(i)) << endl;
			}
			cout << "find (-10):" << v1.find(-10) << endl;
			cout << "find (3):" << v1.find(3) << endl;
			cout << "find (200):" << v1.find(200) << endl;

		} catch (int e) {
			if (e == VNT_IS_FULL) {
				cout << "VNT is full" <<endl;
			} else if (e == VNT_IS_EMPTY) {
				cout << "VNT is empty." << endl;
			}
			output << "IMPOSSIBLE" << endl;
			cout << "IMPOSSIBLE" << endl;
		}
		cout << endl;
		output << endl;
	}

	output << endl;
	input.close();
	output << flush;
	output.close();
    return 0;
}
