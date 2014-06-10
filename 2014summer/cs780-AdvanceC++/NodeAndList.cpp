#include <cstdlib> 
#include <iostream> 
using namespace std; 
 
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
 
 
int main(int argc, char *argv[]) 
{ 
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