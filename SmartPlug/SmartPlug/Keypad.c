/*
 * Keypad.c
 *
 * Created: 10-Nov-14 1:46:21 PM
 *  Author: Dan
 */ 
#include <avr/io.h>
#include "Keypad.h"
char key_table[4][4]= {{'F','E','D','C'},
					   {'3','6','9','B'},
                       {'2','5','8','0'},
                       {'1','4','7','A'}};
unsigned char keyPressed= 0;
enum state1
{
	idle,
	keyPresses,
	debounce,
	getKey,
	waitRelease,
	releasedDebounce,
	released
	};
state1=idle; 
int count=1;
int ms=0;
unsigned char key;
unsigned char keyFound;
unsigned char keyPressed;
unsigned char RawKeyPressed()
{
	return (PINB&((1<<ENCODE1)|(1<<ENCODE2))>>6); //0b000000q1q0 - the values of the output are in bit 0(q0),bit 1(q1)
}

void setRow(char cnt)
{
	switch(cnt)
	{
		case 0: PORTC&=~(1<<DECODE2); PORTC&=~(1<<DECODE1); break; //A=0, B=0   Output:1000
		case 1: PORTC&=~(1<<DECODE2); PORTC|=(1<<DECODE1); break; // A=1, B=0   Output:0100
		case 2: PORTC|=(1<<DECODE2); PORTC&=~(1<<DECODE1); break; // A=0, B=1   Output:0010
		case 3: PORTC|=(1<<DECODE2); PORTC|=(1<<DECODE1); break; //  A=1, B=1   Output:0001
		default: break;
	}
}
char scanKeyPad()
{
	static char temp;
	switch(state1)
	{
		case idle: {if (count==5) 
						count=1;
					setRow(count);
					if (keyPressed) {
						state1=keyPresses;
					}
					else
					{
						state1=idle;
						count++;
					}
					break;
					}
		case keyPresses: temp = RawKeyPressed(); state1= debounce; ms=0; break;
		case debounce: if((ms= 30) && (temp==RawKeyPressed())) state1= getKey; else state1= debounce; break;
		case getKey: state1= waitRelease; key=findKey(count,temp); keyFound=1; break;
		case waitRelease: PORTB ^= (1<<PB0); if (keyPressed) {state1= releasedDebounce; ms=0;} else {state1=released;}break;
		case releasedDebounce: if((ms== 100) && !keyPressed) {state1= released;} else { state1= releasedDebounce;} break;
		case released: state1=idle; count=1; 
		default: state1=idle; count=1;
	}	
	return keyFound;

}
char findKey(char row, char temp)
{
	char key_d;
	switch (temp)
	{
		case 3<<0 : key_d= key_table[row-1][3]; break;
		case 2<<0 : key_d= key_table[row-1][2]; break;
		case 1<<0 : key_d= key_table[row-1][1]; break;
		case 0<<0 : key_d= key_table[row-1][0]; break;
		default: break;
	}
	return key_d;
}

