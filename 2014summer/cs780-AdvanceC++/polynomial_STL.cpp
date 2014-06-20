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
		vector<int> values1 = splitLine(line);
		cout << "original 1: " << line <<endl;
		output << "original 1: " << line <<endl;
		getline(input, line);
		cout << "original 2: " << line <<endl;
		output << "original 2: " << line <<endl;
		vector<int> values2 = splitLine(line);

		map<int,int> poly1,poly2,sum, difference, product; 

		
		// this will read the first polynomial, and automatically keep in in "reverese cannonical form" 

		for(int i=0;i<values1.size();){ 
			int k = values1.at(i+1);
			int v = values1.at(i);
			poly1[k]+=v; 
			if(poly1[k]==0) {
				// erase k
				poly1.erase(k);
			}
			i+= 2;
		}
		
		// print it in cannonical form 
		cout<<"canonical 1: "; 
		output<<"canonical 1: "; 

		printMap(output, poly1);
		printMap(cout, poly1);
	
		for(int i=0;i<values2.size();){ 
			int k = values2.at(i+1);
			int v = values2.at(i);
			poly2[k]+=v; 
			if(poly2[k]==0) 
				poly2.erase(k);
			i+= 2;
		} 
		// this was the second polynomial 
		cout<<"canonical 2: "; 
		output<<"canonical 2: ";
		
		printMap(output, poly2);
		printMap(cout, poly2);
	
		map<int,int>:: reverse_iterator p, p2;
		sum=poly1; 

		// now "add poly2 to sum 
		for(p=poly2.rbegin();p!=poly2.rend();p++) {
			sum[p->first]+=p->second; 
			if(sum[p->first]==0) 
				sum.erase(p->first); 
		} 
		// erase zero
		//if (sum.size() > 1)
		//	sum.erase(sum.find(0));
		cout<<"sum: "; 
		output<<"sum: "; 
		
		printMap(output, sum);
		printMap(cout, sum);

		difference=poly1; 

		// now "subtract poly2 to difference
		for(p=poly2.rbegin();p!=poly2.rend();p++) {
			difference[p->first]-=p->second; 
			if(difference[p->first]==0) 
				difference.erase(p->first); 
		} 

		cout<<"difference: "; 
		output<<"difference: "; 

		printMap(output, difference);
		printMap(cout, difference);

		//product=poly1; 

		// now "multiple poly2 to product
		for(p=poly1.rbegin();p!=poly1.rend();p++) {
			for(p2=poly2.rbegin();p2!=poly2.rend();p2++) {
				int k = p->first + p2->first;
				int v = p->second * p2->second;
				product[k]+=v; 
				if(product[k]==0) 
					product.erase(k); 
			}
		} 
		cout<<"product: "; 
		output<<"product: "; 

		printMap(output, product);
		printMap(cout, product);
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