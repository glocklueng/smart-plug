/*
 * Keypad.c
 *
 * Created: 10-Nov-14 1:46:21 PM
 *  Author: Dan
 */ 
#include <avr/io.h>
#include "Keypad.h"
#include "uart.h"
char key_table[4][4]= {{'F','E','D','C'},
					   {'3','6','9','B'},
                       {'2','5','8','0'},
                       {'1','4','7','A'}};
unsigned char keyPressed= 0;
void initKeypad()
{
	DDRC|= (1<<DECODE1) | (1<<DECODE2);
}

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





unsigned char RawKeyPressed()
{	char temp1 = PIND&(1<<ENCODE1)>>6;
	char temp2 = PIND&(1<<ENCODE2)>>6;
	return temp1|temp2 |(PINB & (1<<PB2)); //0b000000q1q0 - the values of the output are in bit 0(q0),bit 1(q1)
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
					 PORTC&=~(1<<DECODE2); PORTC&=~(1<<DECODE1);
					if ((PINB& (1<<PB2)) !=0) {
						
						state1=keyPresses;
					}
					else
					{
						state1=idle;
						count++;
					}
					USART_Transmit('a');
					break;
					}
		case keyPresses: temp = RawKeyPressed(); state1= debounce; ms=0; USART_Transmit('b');break;
		case debounce: if((ms= 30) && (temp==RawKeyPressed())) state1= getKey; else state1= debounce; USART_Transmit('c'); break;
		case getKey: state1= waitRelease; key=findKey(count,temp); keyFound=1;USART_Transmit('d'); break;
		case waitRelease: PORTB ^= (1<<PB0); if (((PINB& (1<<PB2)) !=0)) {state1= releasedDebounce; ms=0;} else {state1=released;}USART_Transmit('e');break ;
		case releasedDebounce: if((ms>= 100) && ((PINB& (1<<PB2)) ==0)) {state1= released;USART_Transmit('f');} else { state1= releasedDebounce;USART_Transmit('k');} break;
		case released:USART_Transmit('g'); state1=idle; count=1; 
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


	