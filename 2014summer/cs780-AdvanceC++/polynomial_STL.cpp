#include <cstdlib> 
#include <iostream> 
#include <fstream>
#include <sstream>
#include <vector>
#include <cassert>
#include <cassert>
#include<map> 
using namespace std; 

// CS381/780 Project 9 Submission: Su, Haijun

//Polynomial addition using maps 

// split words by space
vector<int> splitLine(string &line) {
	string buf; 
	stringstream ss(line);
	vector<int> tokens;
	while (ss >> buf) {
		tokens.push_back(atoi(buf.c_str()));
	}
	return tokens;
}
// print map
void printMap(ostream& os, map<int,int> &m) {
	map<int,int>:: reverse_iterator p;
	if (m.empty()) {
		os<<"0 0"; 
	} else {
		for(p=m.rbegin();p!=m.rend();p++) {
			os<<(*p).second<<" "<<(*p).first << " "; 
		}
	}
	os << endl;
}
// fill values into map
void fillMap(map<int, int> &m, vector<int> &values) {
	for(int i=0;i<values.size();){ 
		int k = values.at(i+1);
		int v = values.at(i);
		m[k]+=v; 
		if(m[k]==0) {
			// erase k
			m.erase(k);
		}
		i+= 2;
	}
}

// Polynomial class
class Polynomial {
	private:
		map<int, int> poly;
	public:
		// default constructor
		Polynomial() {
			//nop
		}
		// 1 parameter constructor lets us write
		// Polynomial x("10 20");
		Polynomial(string equation) {
			vector<int> tokens = splitLine(equation);
			fillMap(poly, tokens);
		}

		// copy constructor for pass by value and
		// initialization
		Polynomial(const Polynomial & polynomial){
			poly = polynomial.poly;
		}		
		// destructor
		~Polynomial(){
			// nop
		}
		// overloaded assignment lets us assign
		// one Polynomial to another
		Polynomial & operator=(const Polynomial & polynomial){
			if(this==&polynomial)return *this;
			poly = polynomial.poly;
			return *this;
		}		
		// overloaded add
		// one Polynomial to another
		Polynomial operator+(const Polynomial & polynomial){
			Polynomial plnm = *this;
			map<int,int> poly2 = polynomial.poly;
			// now "add poly2 to sum 
			map<int,int>:: reverse_iterator pp;
			for(pp=poly2.rbegin();pp!=poly2.rend();pp++) {
				plnm.poly[pp->first]+=pp->second; 
				if(plnm.poly[pp->first]==0) 
					plnm.poly.erase(pp->first); 
			}			
			return plnm;
		}

		// overloaded difference
		// one Polynomial to another
		Polynomial operator-(const Polynomial & polynomial){
			Polynomial plnm = *this;
			map<int,int> poly2 = polynomial.poly;
			// now "subtract poly2 to difference
			map<int,int>:: reverse_iterator pp;
			for(pp=poly2.rbegin();pp!=poly2.rend();pp++) {
				plnm.poly[pp->first]-=pp->second; 
				if(plnm.poly[pp->first]==0) 
					(plnm.poly).erase(pp->first); 
			}		
			return plnm;
		}

		// overloaded product
		// one Polynomial to another
		Polynomial operator*(const Polynomial & polynomial){
			map<int, int> product;
			map<int,int> poly2 = polynomial.poly;
			map<int,int>:: reverse_iterator pp1, pp2;
			// now "multiple poly2 to product
			for(pp1=poly.rbegin();pp1!=poly.rend();pp1++) {
				for(pp2=poly2.rbegin();pp2!=poly2.rend();pp2++) {
					int k = pp1->first + pp2->first;
					int v = pp1->second * pp2->second;
					product[k]+=v; 
					if(product[k]==0) 
						product.erase(k); 
				}
			}
			Polynomial plnm;
			plnm.poly = product;
			return plnm;
		
		}

		// overloads << so we can directly print Polynomial
		friend ostream& operator<< (ostream& os, Polynomial& polynomial);
};
// print polynomial
ostream& operator<< (ostream& os, Polynomial &polynomial){
	printMap(os, polynomial.poly);
    return os;
};


int main(int argc, char *argv[]) 
{ 
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
		cout << "original 1: " << line <<endl;
		output << "original 1: " << line <<endl;
		Polynomial poly1(line);
		getline(input, line);
		cout << "original 2: " << line <<endl;
		output << "original 2: " << line <<endl;
		Polynomial poly2(line);
		
		// print it in cannonical form 
		cout<<"canonical 1: " << poly1; 
		output<<"canonical 1: "<< poly1;
		cout<<"canonical 2: "<< poly2;
		output<<"canonical 2: "<< poly2;

		cout<<"sum: "; 
		output<<"sum: "; 
		Polynomial sum = poly1 + poly2;
		cout << sum;
		output << sum;


		cout<<"difference: "; 
		output<<"difference: "; 
		Polynomial difference = poly1 - poly2;
		cout << difference;
		output << difference;

		cout<<"product: "; 
		output<<"product: "; 
		Polynomial product = poly1 * poly2;
		cout << product;
		output << product;

		cout<<endl; 
		output<<endl; 

	}
	output << endl;
	input.close();
	output << flush;
	output.close();

	//system("PAUSE"); 
	return EXIT_SUCCESS; 
}