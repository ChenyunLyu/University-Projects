Chenyun, Lyu
CS 537
cs login: chenyun

I used grep command to find out where the getpid system call is located, then added getppid after getpid mimicing the code.
There's a place I need to define the function of getppid, i used to find out the stuctrue of proc and found out that one of the elements of it is called parent.
Based on these information, I finished the getppid function.