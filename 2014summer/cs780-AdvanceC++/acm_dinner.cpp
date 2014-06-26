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

bool show_detail = 0;

bool myfn(int i, int j) { return abs(i)<abs(j); }

vector<int> getNextMembers(vector<int> &mm, int pos, int n) {
	vector<int> vm;
	if (pos > n) return vm;
	int max_grp = abs(*std::max_element(mm.begin(),mm.end(),myfn));
	if (max_grp < n/2) ++max_grp;
	if (pos % 2 == 0) { // negative number
		for (int i = 1; i<=max_grp; i++) {
			vm.push_back(-i);
		}
	} else {
		for (int i = 1; i<=max_grp; i++) {
			vm.push_back(i);
		}
	}
	for(vector<int>::iterator it = mm.begin(); it != mm.end(); ++it) {
		vm.erase(std::remove(vm.begin(), vm.end(), *it), vm.end());
	}
	return vm;
} 

int combineGrp(vector<vector<int> > total, int pos, int n, int count, vector<vector<int> > &data) {
	for (vector<vector<int> >::iterator it0 = total.begin(); it0 != total.end(); ++it0) {
		vector<int> nextMembers = getNextMembers(*it0, pos, n);
		for(vector<int>::iterator it = nextMembers.begin(); it != nextMembers.end(); ++it) {
			vector<vector<int> > subtotal;
			for (vector<vector<int> >::iterator it2 = total.begin(); it2 != total.end(); ++it2) {
				vector<int> tmp(*it2);
				if (tmp.back() == -(*it)) continue; 
				tmp.push_back(*it);
				if ((pos == n) && (tmp.back() != -1)) {
					++count;
					if (show_detail)
						data.push_back(tmp);
				}
				subtotal.push_back(tmp);
			}
			if (pos <= n)
				count = combineGrp(subtotal, pos+1, n, count, data);
		}
	}
	return count;
}
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
			count = 0;
			if (n < 3) return;
			int q[2*n]; 
			vector<int> mm;
			mm.push_back(1);
			mm.push_back(-2);
			mm.push_back(3);
			int pos = 4;
			vector<vector<int> > total;
			total.push_back(mm);
			count = combineGrp(total, pos, n*2, count, data);
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
	if (show_detail) {
		int k = 0;
		for (vector<vector<int> >::iterator it = (acm.data).begin(); it != (acm.data).end(); ++it) {
			os<<"Solution # "<< ++k <<": "; 
			for(vector<int>::iterator it2 = (*it).begin(); it2 != (*it).end(); ++it2) {
				if ((*it2) > 0) os << "+" << (*it2) << " ";
				else os<<(*it2)<<" "; 
			}
			os<<endl; 	
		}
	}
    return os;
};

int main (int argc, char* argv[]) {
	int n = 9; // max team number
	if (argc == 2) n = atoi(argv[1]);
	if (argc == 3) {
		n = atoi(argv[1]);
		show_detail = atoi(argv[2]);
	}
	for(int m=2; m<=n; m++) { // at least 2 teams
		ACM acm(m);
		cout << acm;
	}
	//system("pause"); 
	return 0; 
}