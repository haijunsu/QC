// On some compilers this works as expected, but ...
// on some  .... try it and see what happens.

#include <iostream>
using namespace std;


class queue
{
private:
    char* s;
    int F, R, size;
public:
    queue(int i=4)
    {
   	 s= new char[i];
   	 size=i;
   	 F=R=-1;
    }

    ~queue()
    {
   	 delete []s;
    }

    bool empty()
    {
   	 return F==-1;
    }
/*
    bool full()//this is for a non circular queue
    {
   	 if (F==-1 && R==size-1)
   		 return true;
     if (F > 0) {
   	   for (int i=0; i<=F; i++)
   		 s[i]=s[F+i];
   	   R=R-F-1;
   	   F=0;
     }
   	 return false;
    }*/

    bool full()
    {
     if ((R+1)%size==F)
   		 return true;
   	 return false;
    }


/*    void add (char c)//non circular add
    {
   	 if (full())
   	 {
   		 cout << "queue is full";
   		 exit(1);
   	 }
   	 R++;
   	 s[R]=c;
    }*/

    void add (char c)
    {
   	 if (full())
   	 {
   		 cout << "queue is full";
   		 exit(1);
   	 }
   	 R=(R+1)%size;
     cout << "add: " << R << "=" << c << ", " << endl;
   	 s[R]=c;
     if (F==-1) {
         F = 0; // move to first element
     }
    }

/*    char del()// non circular
    {
   	 if (empty())
   	 {
   		 cout << "queue empty";
   		 exit(1);
   	 }
   	 F++;
   	 return s[F];
    }*/

    char del()
    {
   	 if (empty())
   	 {
   		 cout << "queue empty";
   		 exit(1);
   	 }
     char rtn = s[F];
     cout << "del: " << F << "=" << rtn << ", " << endl;
     if (F == R) {
         F = R = -1; // empty queue
     } else {
         F = (F+1) % size; //move front to next element
     }
   	 return rtn;
    }


};

int main()
{
    queue q;
    q.add('c');
    q.add('b');
    q.add('c');
	q.add('a');
	q.del();
	q.del();
	q.add('b');
    q.add('c');
    q.del();
    q.add('a');
	char a = q.del();
	char b = q.del();
	char c = q.del();
	cout << a << b << c << endl;
//    cout << q.del() << q.del() << q.del() << endl;

    return 0;
}

