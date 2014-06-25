#include <iostream> 
using namespace std; 
 
class z{ 
	public: 
		z(){cout<<"enter"<<endl;} 
		~z(){cout<<"exit"<<endl;} 
}; 
 
class X 
{ 
		int i; 
		int j; 
	public: 
		void destroy(); 
		virtual ~X() {z a; cout << "X's dtor\n"; } // POINT 1 
		// ~X() {z a; cout << "X's dtor\n"; } // POINT 1 

}; 
 
void X::destroy() {z a; delete this; } 
 
class Y : public X 
{ 
		int *j; 
	public: 
		Y(){ j= new int;} 
		~Y() {z a; cout << "Y's dtor\n"; delete j; } 
}; 
 
int main() 
{ 
	X* x = new Y; 
	cout<<"going to destroy"<<endl; 
	x->destroy(); 
	cout<< "from main: Do we call destructors now?" <<endl; 
}

/*
a) The program will print 10 lines. 
1.  going to destroy
2.  enter
3.  enter
4.  Y's dtor
5.  exit
6.  enter
7.  X's dtor
8.  exit
9.  exit
10. from main: Do we call destructors now?

b) If we remove the "virtual" on POINT 1, 7 lines will be printed.
1. going to destroy
2. enter
3. enter
4. X's dtor
5. exit
6. exit
7. from main: Do we call destructors now?
*/

