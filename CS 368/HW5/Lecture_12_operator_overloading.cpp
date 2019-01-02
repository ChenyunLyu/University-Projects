#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

class NAMED_INT {
public:
	int number;
	string name;

	int Get_Number() const { return number; } // accessor
	string Get_Name() const { return name; } // accessor

	NAMED_INT():number(0), name("") {} // default constructor
	NAMED_INT(int n, string s = ""): number(n), name(s) {} // constructor
	NAMED_INT(const NAMED_INT &copy): number(copy.number), name(copy.name) {} // copy

	// assignment operators - always member functions
	const NAMED_INT & operator=(const NAMED_INT &rhs) {
		if (this != &rhs) {
			number = rhs.number;
			name = rhs.name;
		}
		return *this; // return a const reference for operator chaining
	}
	const NAMED_INT & operator+=(const NAMED_INT &rhs) {
		number+=rhs.number;
		name+=" plus " + rhs.name;
		return *this;
	}
	const NAMED_INT & operator-=(const NAMED_INT &rhs) { number-=rhs.number; name+=" minus " + rhs.name; return *this; }
	const NAMED_INT & operator*=(const NAMED_INT &rhs) { number*=rhs.number; name+=" times " + rhs.name; return *this; }
	const NAMED_INT & operator/=(const NAMED_INT &rhs) { number/=rhs.number; name+=" over " + rhs.name; return *this; }

	// Relational operators as member functions - note this requires that the lhs be a class object
	bool operator<(const NAMED_INT &rhs) { return this->number < rhs.number; }
	bool operator<=(const NAMED_INT &rhs) { return this->number <= rhs.number; }
	bool operator==(const NAMED_INT &rhs) { return this->number == rhs.number; }
	bool operator>(const NAMED_INT &rhs) { return this->number > rhs.number; }
	bool operator>=(const NAMED_INT &rhs) { return this->number >= rhs.number; }
	bool operator!=(const NAMED_INT &rhs) { return this->number != rhs.number; }

	// Relational operators to make this work specifically with
	// int on rhs int without creating an anonymous NAMED_INT object
	// lhs int operators require they be nonmembers
	bool operator< (const int &rhs) { return this->number <  rhs; }
	bool operator<=(const int &rhs) { return this->number <= rhs; }
	bool operator==(const int &rhs) { return this->number == rhs; }
	bool operator> (const int &rhs) { return this->number >  rhs; }
	bool operator>=(const int &rhs) { return this->number >= rhs; }
	bool operator!=(const int &rhs) { return this->number != rhs; }

	// type casting
	operator int() const { return number; }
	operator float() const { return float(number); }
	operator double() const { return double(number); }
	operator string() const { return name; }

	// Unary Operators
	const NAMED_INT & operator++() { // prefix
		*this += NAMED_INT(1, "one");
		return *this;
	}
	const NAMED_INT operator++(int) {// postfix
		NAMED_INT copy_before_incrementing(*this);
		*this += NAMED_INT(1, "one");
		return copy_before_incrementing;
	}
	const NAMED_INT & operator--() { // prefix
		*this -= NAMED_INT(1, "one"); return *this;
	}
	const NAMED_INT operator--(int) {	// postfix
		NAMED_INT copy_before_decrementing(*this); *this -= NAMED_INT(1, "one"); return copy_before_decrementing;
	}
	bool operator!() { return !number; }
	NAMED_INT operator+() const { return *this; }
	NAMED_INT operator-() const { return NAMED_INT(-number, "minus " + name); }

	void Print(ostream &out = cout) {
		out<<"("<<number<<", "<<name<<")";
	}
};

// Relational operators these will work with both int and NAMED_INT on either lhs or rhs
// by creating anonymous NAMED_INT objects from the int values.
bool operator< (const NAMED_INT &lhs, const NAMED_INT &rhs)
{ return lhs.number <  rhs.number; }
bool operator<=(const NAMED_INT &lhs, const NAMED_INT &rhs)
		{ return lhs.number <= rhs.number; }
bool operator==(const NAMED_INT &lhs, const NAMED_INT &rhs)
		{ return lhs.number == rhs.number; }
bool operator> (const NAMED_INT &lhs, const NAMED_INT &rhs)
{ return lhs.number >  rhs.number; }
bool operator>=(const NAMED_INT &lhs, const NAMED_INT &rhs)
		{ return lhs.number >= rhs.number; }
bool operator!=(const NAMED_INT &lhs, const NAMED_INT &rhs)
		{ return lhs.number != rhs.number; }

// Relational operators specifically for lhs int without creating anonymous objects
bool operator< (const int &lhs, const NAMED_INT &rhs) { return lhs <  rhs.number; }
bool operator<=(const int &lhs, const NAMED_INT &rhs) { return lhs <= rhs.number; }
bool operator==(const int &lhs, const NAMED_INT &rhs) { return lhs == rhs.number; }
bool operator> (const int &lhs, const NAMED_INT &rhs) { return lhs >  rhs.number; }
bool operator>=(const int &lhs, const NAMED_INT &rhs) { return lhs >= rhs.number; }
bool operator!=(const int &lhs, const NAMED_INT &rhs) { return lhs != rhs.number; }

// Arithmetic operators as nonmember functions
// Boiler plate code if the assignment operators are available
// that guarantees that + and += have the same behavior
NAMED_INT operator+(const NAMED_INT &lhs, const NAMED_INT &rhs) {
	NAMED_INT sum(lhs); // initialize the result with a copy of the lhs
	sum += rhs; // use the += assignment operator
	return sum;
}
NAMED_INT operator-(const NAMED_INT &lhs, const NAMED_INT &rhs) { NAMED_INT result(lhs); result -= rhs; return result; }
NAMED_INT operator*(const NAMED_INT &lhs, const NAMED_INT &rhs) { NAMED_INT result(lhs); result *= rhs; return result; }
NAMED_INT operator/(const NAMED_INT &lhs, const NAMED_INT &rhs) { NAMED_INT result(lhs); result /= rhs; return result; }

// operator<< must be defined outside of the class because the lhs of the operator 'cout' is not an object of the class
// this is pretty much boiler plate code for any class that has a Print function
ostream & operator<<(ostream &out, NAMED_INT &rhs) {
	rhs.Print(out);
	return out;
}

bool sort_by_name(const NAMED_INT &lhs, const NAMED_INT &rhs) {
	if (lhs.name < rhs.name)
		return true;
	else return false;
}

int main() {
	cout<<endl<<"Creating some objects with the default constructor"<<endl;
	NAMED_INT n0(0, "zero");
	NAMED_INT n1(1, "one");
	NAMED_INT n2(2, "two");
	NAMED_INT n3(12, "twelve");
	NAMED_INT n4(12, "one dozen");
	n0.Print(); cout<<endl;
	n1.Print(); cout<<endl;
	n2.Print(); cout<<endl;
	n3.Print(); cout<<endl;
	n4.Print(); cout<<endl;

	cout<<endl<<"Copy constructor copying zero"<<endl;
	NAMED_INT n5(n0); n5.Print(); cout<<endl;

	cout<<endl<<"Assignment operator assigning the copy of zero "<<endl;
	NAMED_INT n6; // calls the default constructor
	n6 = n5; n6.Print(); cout<<endl;

	cout<<endl<<"Relational operators"<<endl;
	cout<<"Is "<< n0 << " < " << n1 <<"? "<<boolalpha<<(n0 < n1)<<endl;
	cout<<"Is "<< n0 << " > " << n1 <<"? "<<boolalpha<<(n0 > n1)<<endl;
	cout<<"Is "<< n0 << " <= " << n1 <<"? "<<boolalpha<<(n0 <= n1)<<endl;
	cout<<"Is "<< n3 << " >= " << n4 <<"? "<<boolalpha<<(n3 <= n4)<<endl;
	cout<<"Is "<< n0 << " != " << n1 <<"? "<<boolalpha<<(n0 != n1)<<endl;
	cout<<"Is "<< n3 << " == " << n4 <<"? "<<boolalpha<<(n3 == n4)<<endl;

	cout<<endl<<"Relational operators with int on LHS"<<endl;
	cout<<"Is "<< 0 << " < " << n1 <<"? "<<boolalpha<<(0 < n1)<<endl;
	cout<<"Is "<< 0 << " > " << n1 <<"? "<<boolalpha<<(0 > n1)<<endl;
	cout<<"Is "<< 0 << " <= " << n1 <<"? "<<boolalpha<<(0 <= n1)<<endl;

	cout<<endl<<"Relational operators with int on RHS"<<endl;
	cout<<"Is "<< n3 << " >= " << 12 <<"? "<<boolalpha<<(n3 <= 12)<<endl;
	cout<<"Is "<< n0 << " != " << 0 <<"? "<<boolalpha<<(n0 != 0)<<endl;
	cout<<"Is "<< n3 << " == " << 12 <<"? "<<boolalpha<<(n3 == 12)<<endl;

	cout<<endl<<"The other assignment operators +=  -=  *=  /="<<endl;
	n0 += n2;
	n1 -= n2;
	n3 *= n2;
	n4 /= n2;
	cout<<n0<<endl;
	cout<<n1<<endl;
	cout<<n3<<endl;
	cout<<n4<<endl;

	cout<<endl<<"Type casting"<<endl;
	cout<<"type casting to double "<< setprecision(3)<<fixed<<double(n4)<<endl;
	cout<<"type casting to float  "<< setprecision(3)<<fixed<<float(n4)<<endl;
	cout<<"type casting to int    "<< setprecision(3)<<fixed<<int(n4)<<endl;

	cout<<endl<<"Unary operators"<<endl;
	NAMED_INT n7(7, "seven");
	cout<<"create "<<n7<<endl;
	NAMED_INT n8 = ++n7;
	cout<<"prefix increment n8 = ++n7.  n8 = "<< n8<<" n7 = "<<n7<<endl;
	n8 = n7++;
	cout<<"postfix increment n8 = n7++. n8 = "<< n8<<" n7 = "<<n7<<endl;

	n8 = -n7; cout<<"n8 = -n7 "<< n8<<" n7 = "<<n7<<endl;
	cout<<"!n7 "<<boolalpha<<!n7<<endl;

	cout<<endl<<"Before sorting by name"<<endl;
	vector<NAMED_INT> vec{n0, n1, n2, n3, n4, n5, n6, n7, n8};
	for (auto e : vec)
		cout<<e<<endl;
	cout<<endl;

	cout<<endl<<"After sorting by name"<<endl;
	sort(vec.begin(), vec.end(), sort_by_name);
	for (auto e : vec)
		cout<<e<<endl;
	cout<<endl;
}



