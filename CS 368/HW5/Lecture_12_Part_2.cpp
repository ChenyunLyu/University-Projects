#include <iostream>
#include <iomanip>
#include <string>

using namespace std;

class PERSON {
protected:
	int ssn;
	string name;

public:
	PERSON(int s, const string &n = "") : ssn(s), name(n) {}
	const string & Get_Name() { return name; }
	int Get_SSN() {return ssn;}
	void Print (ostream & out = cout) const {cout<< ssn<<", " <<name;}
};
	ostream & operator<< (ostream &out, const PERSON &p) {p.Print(out); return out;}


int main() {
	PERSON p1(123, "Alice");
	p1.Print();cout<<endl;
	cout<<p1<<endl;
}
