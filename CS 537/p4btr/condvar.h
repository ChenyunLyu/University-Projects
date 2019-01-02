#ifndef _CV_H_
#define _CV_H_

// cond_t

typedef struct{
  volatile int queue[8]; //thread queue PID
  uint locked;            // lock protection
  int head;              // head
  int tail;              // tail
  int size;              // thread count
}cond_t;

// lock_t struct definition.

typedef struct{
  uint locked;
}lock_t;

#endif //_CV_H_

