/*
 * Keypad.h
 *
 * Created: 10-Nov-14 1:40:25 PM
 *  Author: Dan
 */ 
#define F_CPU 10000000L
#define DECODE1= PC6;
#define DECODE2= PC7;
#define ENCODE1= PD6;
#define ENCODE2= PD7;
void setRow(char cnt);
char scanKeyPad();
char findKey(char row, char temp);
