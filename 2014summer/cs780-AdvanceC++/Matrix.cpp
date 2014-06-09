#include <iostream>
#include <cstdlib>
#include <cassert>

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
			cout << " are not equal." << endl;
			exit(1);
		}

		SafeMatrix<T> product(row_low, row_high, s.col_low, s.col_high);
		for (int i = product.row_low; i <= product.row_high; i++) {
			for (int j = product.col_low; j <= product.col_high; j++) {
				for (int k=0; k<=col_high-col_low; k++) {
					product(i, j) += p[i][k + col_low] * s(k + s.row_low, j);
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
			cout<<s.p[i][j] << " ";
		}
		cout<<endl;
	}
    return os;
};

int main(){
 

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


    return 0;
}

