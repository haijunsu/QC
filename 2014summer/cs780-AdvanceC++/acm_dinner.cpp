// ACM dinner Using the STL next_permutation algorithm 
 
#include <iostream> 
#include <string> 
#include <climits>
#include <algorithm> 
#include <set>
#include <cassert>
#include <fstream>
#include <sstream>
#include <vector>
using namespace std; 

// CS381/780 Project 7 Submission: Su, Haijun

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

// get value base on the position of the member
// ex. {0, 1, 2, 3, 4, 5} => {+1, -1, +2, -2, +3, -3}
//     getValue(3) => -2
int getValue(int i) {
	if (i%2 != 0) return -((i/2) + 1); //female
	return i/2+1; //male
}

// print solution
void print(int* q, int n, int c){ 
	cout<<"Solution # "<<c<<": "; 
	for(int i=0; i<n; i++) {
		int value = getValue(q[i]);
		if (value > 0) cout << "+" << value << " ";
		else cout<<value<<" "; 
	}
	cout<<endl; 
	return; 
}


// whether the array is satisfied.
bool ok(int* q, int n){ 
	if (n < 5) return false; // only two teams
	if (getValue(q[0]) != 1 || getValue(q[1]) != -2 || getValue(q[2]) != 3)
		return false; // reserve 3 seats in front
	int max_grp = 0;
	set<int> grp_nums;
	grp_nums.insert(1);
	grp_nums.insert(2);
	grp_nums.insert(3);
	int prev = getValue(q[1]);
	int curr = getValue(q[2]);
	int next = getValue(q[3]);
	for(int i=3; i<n; i ++) { // begin at seat 4
		prev = curr;
		curr = next;
		if (i==(n-1)) {
			next = getValue(q[0]); // check the last one and the first one 
		} else {
			next = getValue(q[i+1]);
		}
		if(abs(curr) == abs(prev) || abs(curr) == abs(next)) // same team
			return false;
		if (((curr < 0 && prev < 0) && (curr > 0 && prev > 0)) 
			|| ((curr < 0 && next < 0) || (curr > 0 && next > 0))){ // same gender
			return false;
		}
		if (abs(curr) < max_grp && grp_nums.find(abs(curr)) == grp_nums.end()) {
			// large number group sit in front of small number group
			return false;
		}
		if (abs(curr) > max_grp) {
			max_grp = abs(curr);
		}
		grp_nums.insert(abs(curr));
	}
	return true; 
}; 

// ACM class
class ACM {
	private:
		int count;
		int n;
		vector<vector<int> > data;
	public:
		// default constructor
		ACM() {
			count = n = 0;
		}
		// 1 parameter constructor lets us write
		// ACM x(5);
		ACM(int n) {
			this->n = n;
			int q[2*n]; 
			//init team
			for (int i=0; i<n; i++) {
				q[2*i] = 2*i;
				q[2*i+1] = 2*i + 1;
			}
			count=0; 
			do { 
				if(ok(q, 2*n)) {
					++count;
					vector<int> el(q, q + sizeof(q) / sizeof(int));
					data.push_back(el);
				}
			} while ( next_permutation (q, q+2*n) ); 
		}

		// copy constructor for pass by value and
		// initialization
		ACM(const ACM & acm){
			n = acm.n;
			count = acm.count;
			data = acm.data;
		}
		// destructor
		~ACM(){
			n= count = 0;
		}
		// overloaded assignment lets us assign
		// one ACM to another
		ACM & operator=(const ACM & acm){
			if(this==&acm)return *this;
			n = acm.n;
			count = acm.count;
			data = acm.data;
			return *this;
		}				
		// overloads << so we can directly print ACM
		friend ostream& operator<< (ostream& os, ACM& acm);
};
// print ACM
ostream& operator<< (ostream& os, ACM &acm){
	os << "case " << acm.n << ": " << acm.count << endl;

	int k = 0;
	for (vector<vector<int> >::iterator it = (acm.data).begin(); it != (acm.data).end(); ++it) {
		cout<<"Solution # "<< ++k <<": "; 
		for(vector<int>::iterator it2 = (*it).begin(); it2 != (*it).end(); ++it2) {
			int value = getValue(*it2);
			if (value > 0) cout << "+" << value << " ";
			else cout<<value<<" "; 
		}
		cout<<endl; 	
	}
    return os;
};

int main () {
	int n = 9; // max team number
	for(int m=2; m<=n; m++) { // at least 2 teams
		ACM acm(m);
		cout << acm << endl;
	}
	//system("pause"); 
	return 0; 
}