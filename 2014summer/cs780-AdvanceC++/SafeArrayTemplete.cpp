#include <iostream>
#include <cstdlib>
#include <cassert>
using namespace std;


template <class T> // forward declaration
class SA;

template <class T> //forward declaration
ostream& operator<<(ostream& os, SA<T> s);

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


int main(){
    SA<float> a(10);
    int i;
    for( i=0;i<10;i++)
   	 a[i]= i/2.0f;
    cout<<"printing using []"<<endl;
    for( i=0;i<10;i++)
   	 cout<<a[i]<<endl;
    cout<<"and now with overloaded <<"<<endl;
    cout<<a;
    return 0;
}

