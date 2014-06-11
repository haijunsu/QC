#include <cstdlib> 
#include <iostream> 
#include <cassert>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std; 
string intToString (int a)
{
    ostringstream temp;
    temp<<a;
    return temp.str();
}

class Pair {
	public:
		int coe, exp;
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
		
		string toString() {
			return intToString(coe) + " " + intToString(exp);
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

// create node list from int tokens
node<Pair>* make_canonical_list(vector<int> tokens){ 
	if(tokens.size()<=1) return 0; // at least 2 elements

	node<Pair>* first,*p,*q,*tmp; 
	if (tokens.at(0) == 0) {
		tokens.at(1) = 0;
	} 
	Pair pair(tokens.at(0), tokens.at(1));
	first=p=new node<Pair>(pair); 
	for(int i=2; i<tokens.size(); ){
		// check whether the pair can be merged
		int merged = 0;
		do {
			if (p->data().exp == tokens.at(i + 1)) { // same exp
				p->data().coe = p->data().coe + tokens.at(i);
				merged = 1;
				break;
			}
		} while (p=p->link());
		// insert node in the list
		if (merged == 0) { // add new node
			if (tokens.at(i) == 0) {
				tokens.at(i + 1) = 0;
			} 
			Pair pair(tokens.at(i), tokens.at(i + 1));
			q=new node<Pair>(pair); 
			// insert new node
			p = tmp = first;
			if (p->data().exp < pair.exp) { // add q to first
				q->link() = first;
				first = q;
				tmp = 0;
			} else {
				int added = 0;
				while (p->link()) {
					p = p->link();
					if (p->data().exp < pair.exp) { // insert q before p
						tmp->link() = q;
						q->link() = p;
						tmp = 0;
						added = 1;
						break;
					}
					tmp = p;	
				}
				if (added == 0) { // add to end
					p->link() = q;
				}
			}
		}
		p = first;//move p to first
		i += 2;
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

template<class T> 
string getNodePairString(node<T> p) {
	node<T> q = p;
	node<T> *q1;
	string rtn = "";
	if (q.data().coe != 0 || q.link() == 0) {
		rtn += q.data().toString() + " "; 
	}
	while(q.link()) {
		q1 = q.link();
		if (q1->data().coe != 0) { 
			rtn += q1->data().toString() + " "; 
		}
		q = *q1;
	}
	q1 = NULL;
	return rtn;
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

class Polynomial {
	private:
		string original;
		node<Pair> * p;
	public:
		// default constructor
		Polynomial() {
			original = '\0';
			p = NULL;
		}
		// 1 parameter constructor lets us write
		// Polynomial x("10 20");
		Polynomial(string equation) {
			original = equation;
			vector<int> tokens = splitLine(equation);
			p = make_canonical_list(tokens);
		}
		
		// copy constructor for pass by value and
		// initialization
		Polynomial(const Polynomial & polynomial){
			original = polynomial.original;
			node<Pair> * p1, *q, *tmp;
			p1 = polynomial.p;
			Pair pair = p1->data(); // copy data from polynomial to pair
			p = tmp = new node<Pair>(pair);
			while (p1->link()) {
				p1 = p1->link();
				Pair pair = p1->data();
				q = new node<Pair>(pair);
				tmp->link() = q;
				tmp = q;
			}
			p1 = q = tmp = NULL;
		}
		// destructor
		~Polynomial(){
			delete p;
		}
		// overloaded assignment lets us assign
		// one Polynomial to another
		Polynomial & operator=(const Polynomial & polynomial){
			if(this==&polynomial)return *this;
			delete p;
			original = polynomial.original;
			node<Pair> * p1, *q, *tmp;
			p1 = polynomial.p;
			Pair pair = p1->data(); // copy data from polynomial to pair
			p = tmp = new node<Pair>(pair);
			while (p1->link()) {
				p1 = p1->link();
				Pair pair = p1->data();
				q = new node<Pair>(pair);
				tmp->link() = q;
				tmp = q;
			}
			p1 = q = tmp = 0;			
			return *this;
		}
		// overloaded add
		// one Polynomial to another
		Polynomial operator+(const Polynomial & polynomial){
			node<Pair> * p1, *p2;
			Polynomial plnm1(original);
			Polynomial plnm2 = polynomial;
			p1 = plnm1.p;
			p2 = plnm2.p;
			add_at_end(p1, p2);
			string values = getNodePairString(*p1);
			Polynomial plnm(values);
			p1 = p2 = NULL;
			return plnm;
		}
		// overloaded difference
		// one Polynomial to another
		Polynomial operator-(const Polynomial & polynomial){
			node<Pair> * p1, *p2, *p3;
			Polynomial plnm1(original);
			Polynomial plnm2 = polynomial;
			p1 = plnm1.p;
			p2 = p3 = plnm2.p;
			p3->data().coe = 0 - p3->data().coe;
			while (p3 = p3->link()) {
				p3->data().coe = 0 - p3->data().coe;
			}
			add_at_end(p1, p2);
			string values = getNodePairString(*p1);
			Polynomial plnm(values);
			p1 = p2 = p3 = NULL;
			return plnm;
		}
		// overloaded product
		// one Polynomial to another
		Polynomial operator*(const Polynomial & polynomial){
			node<Pair> * p1, *p2, *p3, *p4, *p5, * p6;
			Polynomial plnm1(original);
			Polynomial plnm2 = polynomial;
			p1 = plnm1.p;
			p2 = p3 = plnm2.p;
			int coe = p1->data().coe * p3->data().coe;
			int exp = p1->data().exp + p3->data().exp;
			Pair pair(coe, exp);
			p4 = p5 = new node<Pair>(pair);
			while (p3 = p3->link()) {
				coe = p1->data().coe * p3->data().coe;
				exp = p1->data().exp + p3->data().exp;
				Pair pair(coe, exp);
				p6 = new node<Pair>(pair);
				p5->link() = p6;
				p5 = p6;
			}
			p3 = p2;
			while(p1=p1->link()) {
				do {
					coe = p1->data().coe * p3->data().coe;
					exp = p1->data().exp + p3->data().exp;
					Pair pair(coe, exp);
					p6 = new node<Pair>(pair);
					p5->link() = p6;
					p5 = p6;
				} while (p3=p3->link());
				p3 = p2;
			}
			string values = getNodePairString(*p4);
			Polynomial plnm(values);
			p1 = p2 = p3 = p4 = p5 = p6 = NULL;
			return plnm;
		}
		
		string getCanonical() {
			return getNodePairString(*p);
		}
		
		string getOriginal() {
			return original;
		}
		
		// overloads << so we can directly print Pairs
		friend ostream& operator<< (ostream& os, Polynomial polynomial);
};


ostream& operator<< (ostream& os, Polynomial polynomial){
	os<< "original: " << polynomial.original << endl;
	os<< "canonical: ";
	node<Pair> * p = polynomial.p;
	if(p>0){
		do {
			os<<p->data()<<" "; 
		} while (p = p->link());
	}
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
		Polynomial plnm1(line);
		getline(input, line);
		Polynomial plnm2(line);
		output<< "original 1: " << plnm1.getOriginal() << endl;
		output<< "original 2: " << plnm2.getOriginal() << endl;
		output<< "canonical 1: " << plnm1.getCanonical() << endl;
		output<< "canonical 2: " << plnm2.getCanonical() << endl;
		Polynomial plnm3 = plnm1 + plnm2;
		output<< "original sum: " << plnm3.getOriginal() << endl;
		output<< "sum: " << plnm3.getCanonical() << endl;
		Polynomial plnm4 = plnm1 - plnm2;
		output<< "original difference: " << plnm4.getOriginal() << endl;
		output<< "difference: " << plnm4.getCanonical() << endl;
		Polynomial plnm5 = plnm1 * plnm2;
		output<< "original product: " << plnm5.getOriginal() << endl;
		output<< "product: " << plnm5.getCanonical() << endl << endl;
	}

	output << endl;
	input.close();
	output << flush;
	output.close();
    return 0;
}