#include "POINT.h"
using namespace std;

// declare static variables at global scope
int POINT::objectCount = 0;

POINT::POINT() {
	cout<<"default point constructor"<<endl;
	x = 1;
	y = 3;
	objectCount++;
	z = new int;
	*z = x;
}

POINT::POINT(int x_input, int y_input)
	: x(x_input), y(y_input)
{
	cout<<"constructor that accepts parameters"<<endl;
	objectCount++;
	z = new int;
	*z = x;
}

POINT::POINT(const POINT &p) {
	cout<<"copy constructor"<<endl;
	x = p.x;
	y = p.y;
	z = p.z;
	objectCount++;
	z = new int;
}

POINT::~POINT() {
	delete z; // anytime you use the new command - delete the object when you are done
	cout<<"destructor - deleting z"<<endl;
	objectCount--;
}

void POINT::Print_Point() {
	cout << "(" << x << "," << y << ")";
}

void UNIT_POINT::Set_Unit_Point(int x_input, int y_input) {
	cout<<"Let's keep the unit point at (1,1)"<<endl;
	x = 1;
	y = 1;
}