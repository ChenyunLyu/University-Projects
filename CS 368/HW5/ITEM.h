#ifndef ITEM_TEMPLATE_H_
#define ITEM_TEMPLATE_H_

using namespace std;

class ITEM {
public:
	string name;
	string pname;
	int choice;
	int quantity;
	ITEM() : name(""), quantity(0){}
	~ITEM() {}
	ITEM(string name) : name(name), quantity(0) {}
	virtual bool Process_Purchase(istream &cin, ostream &cout) {
		return true;
	}
	virtual int Get_Cost() const {
		cout<<"don't call this function"<<endl;
		return 0;
	}
	bool operator<(const ITEM& rhs) {
		return this->name < rhs.name;
	}
	virtual void Print(ostream &out=cout) const {
		out<<"Print the derived class info instead of this"<<endl;
	}
};
ostream & operator<<(ostream &out, const ITEM &rhs) {
	rhs.Print(out);
	return out;
}
bool compare_pointers_to_items(ITEM *lhs, ITEM *rhs) {
	return *lhs < *rhs;
}


class BALLS : public ITEM {
public:	
	~BALLS();
	bool Process_Purchase(istream &cin, ostream &cout) {
		
		cout<<"Cannon Balls come in three sizes:"<<endl;
		cout<<"  1) Small     6 pieces of eight per Cannon Ball"<<endl;
		cout<<"  2) Medium   15 pieces of eight per Cannon Ball"<<endl;
		cout<<"  3) Large    52 pieces of eight per Cannon Ball"<<endl;
		cout<<"What size would you like? ";
		//get user's choice
		cin>>choice;
		if( choice == 1 )
		{
			pname = "Small Cannon Ball";
			name = "d";
		}
		else if( choice == 2 )
		{
			pname = "Medium Cannon Ball";
			name = "e";
		}
		else if( choice == 3 )
		{
			pname = "Large Cannon Ball";
			name = "f";
		}
		else
			return false;
		cout<<"How many would you like? ";
		//get quantity
		cin>>quantity;
		if( quantity > 0 )
		{
			cout<<"Happy hunting!"<<endl;
			return true;
		}
		else
			return false;
	}
	
	int Get_Cost() const
	{
		//calculate cost
		if( choice == 1 )
			return quantity * 6;
		else if( choice == 2 )
			return quantity * 15;
		else if( choice == 3 )
			return quantity * 52;
	}
	
	
	void Print(ostream &out=cout) const {
		string mod = "";
		//check if plural
		if( quantity != 1 )
			mod = "s";
		out<<to_string(quantity) + " " + pname + mod;
	}
};
class RUM : public ITEM {
public:	
	~RUM();
	bool Process_Purchase(istream &cin, ostream &cout) {
		
		cout<<"We have three different qualities of Rum in stock:"<<endl;
		cout<<"  1) Swill     1 pieces of eight per barrel"<<endl;
		cout<<"  2) Grog      5 pieces of eight per barrel"<<endl;
		cout<<"  3) Fine Rum 19 pieces of eight per barrel"<<endl;
		cout<<"What kind would you like? ";
		//get user's choice
		cin>>choice;
		if( choice == 1 )
		{
			pname = "Swill";
			name = "h";
			cout<<"Gotta keep the crew happy!"<<endl;
		}
		else if( choice == 2 )
		{
			pname = "Grog";
			name = "i";
			cout<<"The officers will love it!"<<endl;
		}
		else if( choice == 3 )
		{
			pname = "Fine Rum";
			name = "j";
			cout<<"Nothing but the best for the captain!"<<endl;
		}
		else
			return false;
		cout<<"How many barrels would you like? ";
		//get quantity
		cin>>quantity;
		if( quantity > 0 )
		{
			return true;
		}
		else
			return false;
	}
	
	int Get_Cost() const
	{
		//calculate cost
		if( choice == 1 )
			return quantity * 1;
		else if( choice == 2 )
			return quantity * 5;
		else if( choice == 3 )
			return quantity * 19;
	}
	
	
	void Print(ostream &out=cout) const {
		string mod = "Barrel of ";
		//check if pural
		if( quantity != 1 )
			mod = "Barrels of ";
		out<<to_string(quantity) + " " + mod + pname;
	}
};
class LIMES : public ITEM {
public:
	~LIMES();
	bool Process_Purchase(istream &cin, ostream &cout) {
		
		cout<<"Limes arrr delicious. They're on sale for 7 pieces of eight per dozen."<<endl;
		cout<<"How many dozen would you like? ";

		//get quantity
		cin>>quantity;
		name = "g";
		if( quantity > 0 )
			return true;
		else
			return false;
	}
	
	int Get_Cost() const
	{
		//calculate cost
		return quantity * 7;
	}
	
	
	void Print(ostream &out=cout) const {
		out<<to_string(quantity * 12) + " Limes";
	}
};
class APPAREL : public ITEM {
public:	
	~APPAREL();
	bool Process_Purchase(istream &cin, ostream &cout) {
		
		cout<<"That looks like a rough injury."<<endl;
		cout<<"  1) Eyepatch  4 pieces of eight"<<endl;
		cout<<"  2) Pegleg   13 pieces of eight"<<endl;
		cout<<"  3) Hook     43 pieces of eight"<<endl;
		cout<<"You're going to need a ... ";
		//get user's choice
		cin>>choice;
		quantity = 1;
		if( choice == 1 )
		{
			pname = "Eyepatch";
			name = "a";
			return true;
		}
		else if( choice == 2 )
		{
			pname = "Pegleg";
			name = "c";
			cout<<"We'll be getting more crutches in stock early next week."<<endl;
			return true;
		}
		else if( choice == 3 )
		{
			pname = "Hook";
			name = "b";
			return true;
		}
		else
			return false;
	}
	
	int Get_Cost() const
	{
		//calculate cost
		if( choice == 1 )
			return 4;
		else if( choice == 2 )
			return 13;
		else if( choice == 3 )
			return 43;
	}
	
	
	void Print(ostream &out=cout) const {
		out<<"1 " + pname;
	}
};
#endif
