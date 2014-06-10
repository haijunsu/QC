#include <cstdlib> 
#include <iostream> 
#include <cassert>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std; 

class Pair {
	private:
		int coe, exp;
	public:
		// default constructor
		// coe and exp are zeros
		Pair() {
			coe = exp = 0;
		}
		// 2 parameter constructor lets us write
		// pair x(10,20);
		Pair(int c, int e) {
			coe = c;
			exp = e;
		}
		
		// copy constructor for pass by value and
		// initialization
		Pair(const Pair & pair){
			coe = pair.coe;
			exp = pair.exp;
		}
		// destructor
		~Pair(){
			// nop
		}
		// overloaded assignment lets us assign
		// one Pair to another
		Pair & operator=(const Pair & pair){
			coe = pair.coe;
			exp = pair.exp;
			return *this;
		}
		// overloads << so we can directly print Pairs
		friend ostream& operator<< (ostream& os, Pair pair);
};

ostream& operator<<(ostream& os, Pair pair){
	os<< pair.coe << " " << pair.exp;
    return os;
};

template<class T> 
class node{ 
	private: 
		T Data; 
		node* Link; 
	public: 
		node(){Link=0;} 
		node(T i){Data=i;Link=0;} 
		T& data(){return Data;} 
		node* & link(){return Link;} 
}; 
 
template<class T> 
void print_list(node<T>* p){ 
	while(p){ 
		cout<<p->data()<<" "; 
		p=p->link(); 
	} 
	cout<<endl; 
} 
 
node<int>* make_int_list(int n){ 
	if(n<=0) return 0; 

	node<int>* first,*p,*q; 
	first=p=new node<int>(1); 
	for(int i=2; i<=n; i++){ 
		q=new node<int>(i); 
		p->link()=q; 
		p=q; 
	} 
	return first; 
} 
 
template<class T> 
void print_list_rec(node<T>* p){ 
	if(p==0) return; 
	cout<<p->data()<<" "; 
	print_list_rec( p->link()); 

} 
 
template<class T> 
void add_at_end(node<T>* &first, node<T>*r){ 
	node<T>* p; 
	p=first; 
	while(p->link()){ 
		p=p->link(); 
	} 
	p->link()=r; 

} 

// split words by space
vector<int> splitLine(string line) {
	string buf; 
	stringstream ss(line);
	vector<int> tokens;
	while (ss >> buf) {
		tokens.push_back(atoi(buf.c_str()));
	}
	return tokens;
} 

template<class T> 
class Polynomial {
	private:
		string original;
		node<T> * p;
	public:
		// default constructor
		Polynomial() {
			original = NULL;
			p = NULL;
		}
		// 1 parameter constructor lets us write
		// Polynomial x("10 20");
		Polynomial(string equation) {
			original = equation;
			//TODO
		}
		
		// copy constructor for pass by value and
		// initialization
		Polynomial(const Polynomial & polynomial){
			//TODO
		}
		// destructor
		~Polynomial(){
			delete [] p;
		}
		// overloaded assignment lets us assign
		// one Polynomial to another
		Polynomial & operator=(const Polynomial & polynomial){
			//TODO
			return *this;
		}
		// overloads << so we can directly print Pairs
		friend ostream& operator<< <T>(ostream& os, Polynomial<T> polynomial);
};

template<class T> 
ostream& operator<< (ostream& os, Polynomial<T> polynomial){
	os<< polynomial.original << " " << print_list_rec(polynomial.p);
    return os;
};
int main(int argc, char *argv[]) 
{ 
	node<Pair>* first,*p,*q;
	Pair pair(1,2);
	first=p=new node<Pair>(pair); 
	for(int i=2; i<=5; i++){ 
		Pair pair(i,(i+2) % 4);
		q=new node<Pair>(pair); 
		p->link()=q; 
		p=q; 
	} 
	print_list<Pair>(first);
	//create and print a list of n integer nodes 
	int n; 
	cout<<"How many nodes? "; 
	cin>>n; 
	cout<<endl; 
	print_list<int>(make_int_list(n)); 
	cout<<endl; 

	//now do it recursively 

	print_list_rec<int>(make_int_list(n)); 
	cout<<endl; 

	node<int>* a=make_int_list(9); 
	node<int>* b=new node<int>(56); 
	add_at_end(a,b); 
	cout<<endl; 
	print_list<int>(a); 


	//system("PAUSE"); 
	return EXIT_SUCCESS; 
}