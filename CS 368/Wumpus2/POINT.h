#ifndef POINT_H_
#define POINT_H_

#include <iostream>

using namespace std;

class POINT {

public:
	static int objectCount;
	int *z;

protected: // so that UNIT_POINT can use these
	int x;
	int y;

public:
	POINT();
	POINT(int x_input, int y_input);
	POINT(const POINT &p);
	~POINT();
	void Print_Point();

	// getters and setters
	// note functions areautomatically inline if they are defined in the header
	int getX() const { return x; }
	void setX(int x) { this->x = x; }
	int getY() const { return y; }
	void setY(int y) { this->y = y; }
};

// derived class for the point (1,1)
class UNIT_POINT: public POINT {
public:
	UNIT_POINT() { x=1; y=1; }
	UNIT_POINT(int x, int y) { this->x = 1; this->y=1; }
	UNIT_POINT(const POINT &p) { x=1; y=1; }
	void Set_Unit_Point(int x_input, int y_input);
};

#endif /* POINT_H_ */
