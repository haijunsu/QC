// Inverted Index without the STL (maps or sets)
 
#include <iostream> 
#include <string> 
#include <climits>
#include <algorithm> 
#include <cassert>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std; 

// CS381/780 Project 8 Submission: Su, Haijun

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

// Object stores word positions.
class WordIndex {
	private:
		String word;
		vector<long> positions; // line, times
	public:
		// default constructor
		WordIndex() {
			word = '\0';
		}
		// 1 parameter constructor lets us write
		// WordIndex x("word");
		WordIndex(string word) {
			this->word = word;
		}
		
		// copy constructor for pass by value and
		// initialization
		WordIndex(const WordIndex & wi){
			word = wi.word;
			positions = wi.positions;
		}
		// destructor
		~WordIndex(){
			// nop
		}
		// overloaded assignment lets us assign
		// one WordIndex to another
		WordIndex & operator=(const WordIndex & wi){
			if (*this == wi) return *this;
			word = wi.word;
			positions = wi.positions;
			return *this;
		}
		
		bool operator==(const WordIndex & wi) {
			return word == wi.word;
		}
		
		bool operator<(const WordIndex & wi) {
			return word < wi.word;
		}
		// overloads << so we can directly print WordIndex
		friend ostream& operator<< (ostream& os, WordIndex wi);
};

// print pair to ostream
ostream& operator<<(ostream& os, WordIndex & wi){
	os << wi.word << ": ";
	for (vector<int>::iterator it = (wi.positions).begin(); it != (wi.positions).end(); ++it) {
		os<< *it;
		++it;
		if (*it > 1) {
			os << "(" << *it << "), "
		} else {
			os << *it << ", ";
		}
		os << endl; 	
	}
    return os;
};