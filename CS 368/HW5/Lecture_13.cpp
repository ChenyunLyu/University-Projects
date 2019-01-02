#include <iostream>
#include <string>
#include <vector>

using namespace std;

class ANIMAL{
public:
	virtual ~ANIMAL(){cout<<"ANIMAL DESTRUCTOR"<<endl;}
	string color;
	virtual void speak() = 0;
};

class BIRD : public ANIMAL {
public:
	~BIRD(){cout<<"BIRD DESTRUCTOR"<<endl;}
	void speak() {cout<<"Chirp. Chirp"<<endl;}
};

class MAMMAL : public ANIMAL{
public:
	~MAMMAL(){cout<<"MAMMAL DESTRUCTOR"<<endl;}
	void speak() {cout<<"Mammals don't speak"<<endl;}
	void bark() {cout<<"MAMMAL bark"<<endl;}
};

class CAT : public MAMMAL{
public:
	~CAT(){cout<<"CAT DESTRUCTOR"<<endl;}
	void speak() {cout<<"Meow."<<endl;}
	void purr() {cout<<"purrrrr."<<endl;}
};

class DOG : public MAMMAL{
public:
	string name;
	~DOG(){cout<<"DOG DESTRUCTOR"<<endl;}
	void speak() {cout<<"Woof. Woof."<<endl;}
	void bark(int x) {cout<<"dog bark"<<endl;}
	using MAMMAL::bark;
};


int main(){
	// declare a bird and note that it can access color in the base class
	// note use of pointers
	BIRD *b = new BIRD;
	b->color = "red";
	cout<<b->color<<endl;
	cout<<"test"<<endl<<endl;

	// mammal can point to a dog and still call the speak method
	// if speak is a virtual function - tells C++ to use dynamic
	// dispatch
	DOG *d = new DOG;
	d->speak();
	MAMMAL *m = new MAMMAL();
	m->speak();
	m=d;
	m->speak();

	// speak works but purr does not because purr is not a function in mammal
	MAMMAL *m2 = new CAT();
	m2->speak();
	// m2->purr();

	// fix that by casting the mammal m2 back to a cat
	CAT *c = (CAT *)m2; // cast to a pointer to a cat
	c->speak();
	c->purr();

	// that's potentially dangerous because C++ doesn't check that
	// the type is appropriate
	DOG *d2 = (DOG *)m2;
	d2->speak();
	// d2->purr();

	// use dynamic_cast to tell C++ to check if the types are appropriate
	// it will either reassign the pointer to the requested object
	// or it will return null if the types do not belong to the same
	// inheritance hierarchy
	MAMMAL *m3 = new DOG();
	CAT *c2 = dynamic_cast<CAT *>(m3);
	//c2->purr();
	if (c2) c2->purr();
	else cout<<"not a cat"<<endl;

	// ANIMAL is abstract - note the use of =0 in the class definition
	// we can still assign pointers of type animal to derived classes
	// but we can not create new objects of type animal
	// ANIMAL a;
	ANIMAL *a = new DOG();
	a->speak();

	// use virtual destructors
	// they chain up from the correct type
	// prevent memory leaks
	DOG *d3 = new DOG();
	// delete d3;
	MAMMAL *m4;
	m4= d3;
	delete m4;

	// Name Resolution
	// C++ looks for a function with the correct name inside the derived class
	// but finds the prototype doesn't match - bark in the dog class requires an
	// int.  add the using directive to tell C++ to also consider choosing
	// the function from the base class.
	DOG *d4 = new DOG();
	d4->bark();


}

