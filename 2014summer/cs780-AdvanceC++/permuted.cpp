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

// Object stores word positions.
class WordIndex {
	private:
		string word;
		vector<long> positions; // line, times
		long previous_line;
	public:
		// default constructor
		WordIndex() {
			word = '\0';
			previous_line = -1;
		}
		// 1 parameter constructor lets us write
		// WordIndex x("word");
		WordIndex(string word) {
			this->word = word;
			previous_line = -1;
		}
		
		// copy constructor for pass by value and
		// initialization
		WordIndex(const WordIndex & wi){
			this->word = wi.word;
			this->positions = wi.positions;
			this->previous_line = wi.previous_line;
		}
		// destructor
		~WordIndex(){
			// nop
		}
		// overloaded assignment lets us assign
		// one WordIndex to another
		WordIndex & operator=(const WordIndex & wi){
			if (*this == wi) return *this;
			this->word = wi.word;
			this->positions = wi.positions;
			this->previous_line = wi.previous_line;
			return *this;
		}
		// overloaded equals
		bool operator==(const WordIndex & wi) {
			return this->word == wi.word;
		}

		// overloaded not equals
		bool operator!=(const WordIndex & wi) {
			return this->word != wi.word;
		}
				
		// add line number
		void addLineNumber(long num) {
			if (previous_line == num) {
				positions.back() ++; // count ++
				return;
			}
			// add new line
			positions.push_back(num); // add line
			positions.push_back(1); // count 1
			previous_line = num; // update previous_line
		}
		string getWord() {
			return this->word;
		}
		// overloads << so we can directly print WordIndex
		friend ostream& operator<< (ostream& os, WordIndex wi);
};

// print pair to ostream
ostream& operator<<(ostream& os, WordIndex wi){
	os << wi.word << ": ";
	bool isFirst = true;
	for (vector<long>::iterator it = (wi.positions).begin(); it != (wi.positions).end(); ++it) {
		if (!isFirst) os<<", ";
		else isFirst = false;
		os<< *it;
		++it;
		if (*it > 1) {
			os << "(" << *it << ")";
		} 
	}
	os << endl; 	
    return os;
};

// compare two WordIndex, used by sort function
bool comp(WordIndex wi1, WordIndex wi2) {
	return wi1.getWord() < wi2.getWord();
}

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
	long lineNum = 0;
	vector<WordIndex> wi;
	while (getline(input, line)){
		++lineNum;
		stringstream ss(line);
		vector<WordIndex>::iterator it;
		string buf;
		while (ss >> buf) {
			WordIndex wi2(buf);
			it = find(wi.begin(), wi.end(), wi2);
			if(it == wi.end()) {
				wi2.addLineNumber(lineNum);
				wi.push_back(wi2);
			} else {
				(*it).addLineNumber(lineNum);
			}
		}
	}
	sort(wi.begin(), wi.end(), comp);
	for (vector<WordIndex>::iterator it = wi.begin(); it != wi.end(); ++it) {
		output<<*it;
		cout << *it;
	}
	input.close();
	output << flush;
	output.close();
    return 0;
}