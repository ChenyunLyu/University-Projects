#ifndef PLAYER_H_
#define PLAYER_H_

#include <iostream>
#include <map>
#include <vector>
#include <set>
#include <string>
#include <time.h>

using namespace std;

class CAVE;
class PLAYER {
	vector<int> position;

	enum PSTATUS {
		TRUE, FALSE
	};
	enum FACE {
		LEFT, RIGHT, UP, DOWN
	};
	enum PLAYERSTATUS {
		ALIVE, EATEN, FELL, ESCAPED
	};
	PSTATUS hasGold;
	PLAYERSTATUS playerStatus;
	PSTATUS hasArrow;
	FACE direction;
	

public:
	PLAYER();
	~PLAYER();
	vector<int> Get_Position();
	string Get_Facing();
	vector<int> Get_Next_Square();
	void Move_Forward();
	void Turn_Left();
	void Turn_Right();
	bool Pickup_Gold( CAVE &cave );
	bool Shoot_Arrow( CAVE &cave );
	void Climb_Ladder();
	bool Has_Arrow();
	bool Has_Gold();
	bool Update_Player_Status( CAVE &cave );
	bool Escaped();
	bool Eaten();
	bool Fell();
};
#endif
