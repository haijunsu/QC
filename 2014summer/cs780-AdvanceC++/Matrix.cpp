#include <iostream>
#include <cstdlib>
#include <cassert>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std;

template <class T> // forward declaration
class SA;

template <class T> //forward declaration
ostream& operator<<(ostream& os, SA<T> s);

template <class T> // forward declaration
class SafeMatrix;

template <class T> //forward declaration
ostream& operator<<(ostream& os, SafeMatrix<T> s);

template <class T>
class SA {
    private:
   	 int low, high;
   	 T* p;
    public:
    // default constructor
    // allows for writing things like SA a;
    SA() {
   	 low=0;
   	 high=-1;
   	 p=NULL;
    }
    // 2 parameter constructor lets us write
    // SA x(10,20);
    SA(int l, int h){
   	 if((h-l+1)<=0) {
   		 cout << "constructor error in bounds definition" << endl;
   		 exit(1);
   	 }
   	 low=l;
   	 high=h;
   	 p=new T[h-l+1];
    }
    // single parameter constructor lets us
    // create a SA almost like a "standard" one by writing
    // SA x(10); and getting an array x indexed from 0 to 9
    SA(int i) {
   	 low=0;
   	 high=i-1;
   	 p=new T[i];
    }
    // copy constructor for pass by value and
    // initialization
    SA(const SA & s){
   	 int size=s.high-s.low+1;
   	 p=new T[size];
   	 for(int i=0; i<size; i++)
   		 p[i]=s.p[i];
   	 low=s.low;
   	 high=s.high;
    }
    // destructor
    ~SA(){
   	 delete [] p;
    }
    //overloaded [] lets us write
    //SA x(10,20); x[15]= 100;
    T& operator[](int i){
   	 if(i<low || i>high){
   		 cout<< "index "<<i<<" out of range"<<endl;
   		 exit(1);
   	 }
   	 return p[i-low];
    }
    // overloaded assignment lets us assign
    // one SA to another
    SA & operator=(const SA & s){
   	 if(this==&s)return *this;
   	 delete [] p;
   	 int size=s.high-s.low+1;
   	 p=new T[size];
   	 for(int i=0; i<size; i++)
   	 p[i]=s.p[i];
   	 low=s.low;
   	 high=s.high;
   	 return *this;
    }
    // overloads << so we can directly print SAs
    friend ostream& operator<< <T>(ostream& os, SA<T> s);
};

template <class T>
ostream& operator<<(ostream& os, SA<T> s){
    int size=s.high-s.low+1;
    for(int i=0; i<size; i++)
   	 cout<<s.p[i]<<endl;
    return os;
};

template <class T>
class SafeMatrix {
    private:
		int row_low, row_high, col_low, col_high;
		SA<SA<T> > p;
    public:
    
    // 1 parameter constructor lets us write
    // SafeMatrix x(10);
    SafeMatrix(int dim){
		row_low = 0;
		row_high = dim-1;
		col_low = 0;
		col_high = dim-1;
		SA<SA<T> > row(dim);
		for (int i=0; i<dim; i++) {
			row[i] = SA<T>(dim);
		}
		p = row;
    }
    // 2 parameter constructor lets us write
    // SafeMatrix x(10,20);

    SafeMatrix(int r, int c){
		row_low = 0;
		row_high = r-1;
		col_low = 0;
		col_high = c-1;
		SA<SA<T> > row(r);
		for (int i=0; i<r; i++) {
			row[i] = SA<T>(c);
		}
		p = row;
	}

	// 4 parameter constructor lets us write
    // SafeMatrix x(10,20,10,20);
    SafeMatrix(int r_l, int r_h, int c_l, int c_h){
		if (r_h + 1 <= r_l || c_h + 1 <= c_l) {
			cout << "constructor error in bounds definition" << endl;
			throw 200;
		}
		row_low = r_l;
		row_high = r_h;
		col_low = c_l;
		col_high = c_h;
		SA<SA<T> > row(r_l, r_h);
		for (int i=r_l; i<=r_h; i++) {
			row[i] = SA<T>(c_l, c_h);
		}
		p = row;
    }
    // destructor
    ~SafeMatrix(){
		for (int i=row_low; i<=row_high; i++) {
			p[i] = NULL;
		}
		p = NULL;
    }
    //overloaded () lets us write
    //SafeMatrix x(10,20)
    T& operator()(int i, int j){
		if(i< row_low || i>row_high){
			cout<< "Row index "<<i<<" out of range"<<endl;
			exit(1);
		}
		if(j< col_low || j>col_high){
			cout<< "Col index "<<j<<" out of range"<<endl;
			exit(1);
		}
		return p[i][j];
    }

    // overloaded assignment lets us assign
    // one SafeMatrix to another
    SafeMatrix & operator=(SafeMatrix & s){
		if(this==&s)return *this;
		row_low = s.row_low;
		row_high = s.row_high;
		col_low = s.col_low;
		col_high = s.col_high;
		SA<SA<T> > row(row_low, row_high);
		for (int i=row_low; i<=row_high; i++) {
			row[i] = SA<T>(col_low, col_high);
			for(int j=col_low; j<=col_high; j++) {
				row[i][j] = s(i,j);
			}
		}
		p = row;
		return *this;
    }

    // overloaded multiplication
    // one SafeMatrix to another
    SafeMatrix operator*(SafeMatrix & s){
		int col_size = col_high - col_low + 1;
		int s_row_size = s.row_high - s.row_low + 1;
		if (col_size != s_row_size) {
			cout << "Column size (" << col_size;
			cout << ") and S's row size (" << s_row_size;
			cout << ") are not equal." << endl;
			throw 200;
		}

		SafeMatrix<T> product(row_low, row_high, s.col_low, s.col_high);
		for (int i = product.row_low; i <= product.row_high; i++) {
			for (int j = product.col_low; j <= product.col_high; j++) {
				for (int k=0; k<=col_high-col_low; k++) {
					if (k == 0) {
						product(i, j) = p[i][k + col_low] * s.p[k + s.row_low][j];
					} else {
						product(i, j) += p[i][k + col_low] * s.p[k + s.row_low][j];
					}
				}
			}
		}
		return product;
	}
	
    // overloads << so we can directly print SAs
    friend ostream& operator<< <T>(ostream& os, SafeMatrix<T> s);
};

template <class T>
ostream& operator<<(ostream& os, SafeMatrix<T> s){
    for(int i=s.row_low; i<=s.row_high; i++) {
		for (int j=s.col_low; j<=s.col_high; j++) {
			os<<s.p[i][j] << " ";
		}
		os<<endl;
	}
    return os;
};

SafeMatrix<int> getSM(vector<int> tokens) {
	if(tokens.size() == 1){
		SafeMatrix<int> sm1(tokens.at(0));
		return sm1;
	} else if (tokens.size() == 2){
		SafeMatrix<int> sm2(tokens.at(0), tokens.at(1));
		return sm2;
	} else if (tokens.size() == 4){
		SafeMatrix<int> sm4(tokens.at(0), tokens.at(1), tokens.at(2), tokens.at(3));
		return sm4;
	}
	return NULL;
}

SafeMatrix<int> buildSM(vector<int> values1, vector<int> values2) {
	SafeMatrix<int> sm1 = getSM(values1);
	
	int col_low, col_high, row_low, row_high;
	if (values1.size() == 1) {
		row_low = 0;
		row_high = values1.at(0) - 1;
		col_low = 0;
		col_high = values1.at(0) - 1;
	} else if (values1.size() == 2) {
		row_low = 0;
		row_high = values1.at(0) - 1;
		col_low = 0;
		col_high = values1.at(1) - 1;
	} else if (values1.size() == 4) {
		row_low = values1.at(0);
		row_high = values1.at(1);
		col_low = values1.at(2);
		col_high = values1.at(3);
	}

	int col_size = col_high - col_low + 1;
	int row = 0;
	for (int i=0; i<values2.size(); i++) {
		if (i > 0 && i % col_size == 0) 
			row++;
		sm1(row + row_low, i%col_size + col_low) = values2.at(i);
	}
	return sm1;
}

vector<int> splitLine(string line) {
	string buf; 
	stringstream ss(line);
	vector<int> tokens;
	while (ss >> buf) {
		tokens.push_back(atoi(buf.c_str()));
	}
	return tokens;
}

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
		//cout << line << endl;
		vector<int> values1 = splitLine(line);
		getline(input, line);
		vector<int> values2 = splitLine(line);
		SafeMatrix<int> sm1 = buildSM(values1, values2);
		getline(input, line);
		vector<int> values3 = splitLine(line);
		getline(input, line);
		vector<int> values4 = splitLine(line);
		SafeMatrix<int> sm2 = buildSM(values3, values4);
		output << "Matrix 1 :" << endl;
		output << sm1 << endl;
		output << "Matrix 2 :" << endl;
		output << sm2 << endl;
		output << "Matrix 1 * Matrix 2 :" <<endl;
		try {
			SafeMatrix<int> sm3 = sm1 * sm2;
			output << sm3 << endl;
		} catch (int e) {
			output << "IMPOSSIBLE" << endl;
		}
/*		if (!sm3) {
			output << "IMPOSSIBLE" << endl;
		} else {
			output << sm3 << endl;
		} */
		output << endl;
	}

	output << endl;
	/*
	SafeMatrix<int> sm(2, 2);
	sm(0,0) = 1;
	sm(0,1) = 2;
	sm(1,0) = 3;
	sm(1,1) = 4;
	output << sm;
	input.close();
	output << flush;
	output.close();

	//string str("Split me by whitespaces");
    string buf; // Have a buffer string
    stringstream ss(line); // Insert the string into a stream

    vector<string> tokens; // Create vector to hold our words

    while (ss >> buf) {
        tokens.push_back(buf);
		cout<<buf << " ";
	}

	

	cout << "sm:" << endl;
	SafeMatrix<int> sm(2, 2);
	sm(0,0) = 1;
	sm(0,1) = 2;
	sm(1,0) = 3;
	sm(1,1) = 4;
	cout << sm << endl;
	SafeMatrix<int> sm2 = sm;
	sm(0,0) = 5;
	cout << "sm (modified):" << endl;
	cout << sm << endl;
	sm2(1,0)=6;
	cout << "sm2:" << endl;
	cout << sm2 << endl;

    SafeMatrix<int> sm3(1, 2, 1, 3);
	cout << "sm3:" << endl;
	sm3(2,3) = 5;
	sm3(2,2) = 1;
	sm3(2,1) = 2;
	sm3(1,3) = 6;
	sm3(1,2) = 3;
	sm3(1,1) = 4;
	cout << sm3 << endl;
	
	cout << sm2 * sm3 << endl;
*/
    return 0;
}

