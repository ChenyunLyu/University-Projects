#include <iostream>
#include <string>
#include <iomanip>

// #include "BOX.h"
#include "POINT.h"

using namespace std;

int main() {

	// ##### ENUP #####
	// enum creates named constants
	// similar to ->  const int RED = 0;
	enum COLOR { RED, ORANGE, BLUE, BLACK=7, GREEN, YELLOW, VIOLET=2, PINK, WHITE };
	int w = 11;
	cout <<setw(w)<<right<< "RED = " << RED << endl;
	cout <<setw(w)<<right<< "ORANGE = " << ORANGE << endl;
	cout <<setw(w)<<right<< "BLUE = " << BLUE << endl;
	cout <<setw(w)<<right<< "BLACK = " << BLACK << endl;
	cout <<setw(w)<<right<< "GREEN = " << GREEN << endl;
	cout <<setw(w)<<right<< "YELLOW = " << YELLOW << endl;
	cout <<setw(w)<<right<< "VIOLET = " << VIOLET << endl;
	cout <<setw(w)<<right<< "PINK = " << PINK << endl;
	cout <<setw(w)<<right<< "WHITE = " << WHITE << endl;
	cout<<endl;

	// ##### SWITCH #####
	// try this with different colors
	COLOR color = ORANGE;
	switch (color) {
		case RED:
			cout<<"You selected RED"<<endl;
			break;
		case ORANGE:
			cout<<"You selected ORANGE"<<endl;
			// if no break then it falls through to the next case and continues
		case BLUE:
			cout<<"You selected either ORANGE which falls through to the BLUE case after printing the ORANGE line, or you selected BLUE"<<endl;
			break;
		default:
			cout<<"You didn't select RED, ORANGE, or BLUE"<<endl;
	}
	cout<<endl;

	// ##### CLASSES #####
	// default constructor
	POINT p1;
	int c=POINT::objectCount;
	cout<<"POINT objectCount : static access = "<<c<<endl;
	cout<<"POINT objectCount : object access = "<<p1.objectCount<<endl<<endl;

	cout<<"destructor will be called when the object goes out of scope"<<endl;
	{
		POINT p_to_be_destroyed(86, 86);
		cout<<"p_to_be_destroyed "; p_to_be_destroyed.Print_Point(); cout<<endl;

		cout<<"POINT objectCount inside scope = "<<POINT::objectCount<<endl<<endl;
	}
	cout<<"POINT objectCount after scope = "<<POINT::objectCount<<endl<<endl;


	// constructor
	POINT p2(8, 9);
	cout<<"p2 "; p2.Print_Point(); cout<<endl;
	cout<<"POINT objectCount "<<POINT::objectCount<<endl<<endl;


	// copy constructor
	POINT p3(p2);
	cout<<"p3 is a copy of p2. p3 = "; p3.Print_Point(); cout<<endl;

	cout<<"POINT objectCount "<<POINT::objectCount<<endl<<endl;

	cout<<"We can change p3 and it doesn't impact p2"<<endl;
	cout<<"changing p3 to (99,99)"<<endl;
	p3.setX(99);
	p3.setY(99);
	cout<<"p1 "; p1.Print_Point(); cout<<endl;
	cout<<"p2 "; p2.Print_Point(); cout<<endl;
	cout<<"p3 "; p3.Print_Point(); cout<<endl;

	cout<<"POINT objectCount "<<POINT::objectCount<<endl<<endl;

	cout<<"Pointers to objects. Use -> to access functions"<<endl;
	cout<<"p4 is a pointer to p1"<<endl;
	POINT *p4 = &p1;
	cout<<"p4 did not create a new object. p4 "; p4->Print_Point(); cout<<endl;
	cout<<"POINT objectCount "<<POINT::objectCount<<endl<<endl;

	cout<<"Changing p4 to (32,32)"<<endl;
	p4->setX(32); p4->setY(32);
	cout<<"p1 "; p1.Print_Point(); cout<<endl;
	cout<<"p4 "; p4->Print_Point(); cout<<endl<<endl;

	// error x is protected.
	// cout<<p3->x<<endl;
	// cout<<(*p3).x<<endl;

	return 0;
}
