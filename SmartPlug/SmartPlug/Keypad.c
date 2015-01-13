/*
 * Keypad.c
 *
 * Created: 10-Nov-14 1:46:21 PM
 *  Author: Dan
 */ 
#include <avr/io.h>
#include "Keypad.h"
#include "uart.h"
#include <util/delay.h>

char key_table[4][4]= {{'F','E','D','C'},
					   {'3','6','9','B'},
                       {'2','5','8','0'},
                       {'1','4','7','A'}};
extern volatile int keyPressed;
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
unsigned char buffer[10];

unsigned char RawKeyPressed()
{   char temp1 = (PIND&(1<<ENCODE1))>>6;
	 char temp2 = (PIND&(1<<ENCODE2))>>6;
	return temp1|temp2; //0b000000q1q0 - the values of the output are in bit 0(q0),bit 1(q1)
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
					 setRow(count-1);
					if ((PINB& (1<<PB2)) !=0 ) {
						
						state1=keyPresses;
						ms=0;
					}
					else
					{
						state1=idle;
						count++;
					}
					
					break;
					}
		case keyPresses: if(((PINB& (1<<PB2)) !=0)) { temp = RawKeyPressed(); state1= debounce;} else state1=idle;  break;
		case debounce: if((ms>=30) && (temp==RawKeyPressed()) && ((PINB& (1<<PB2)) !=0) ) {   state1= getKey;} else state1= keyPresses;  break;
		case getKey:  state1= waitRelease; key=findKey(count,temp);  break;
		case waitRelease:  if ((PINB& (1<<PB2)) !=0) {state1= releasedDebounce;} else {state1=released;}break ;
		case releasedDebounce: if((ms>= 70) && ((PINB& (1<<PB2)) ==0)) {state1= released;} else { state1= releasedDebounce;} break;
		case released: state1=idle; count=1; stopTimer(); keyFound=1; break;
		default: state1=idle; count=1; break;
	}	
	return keyFound;

}
char findKey(char row, char temp)
{
	char key_d;
	switch (temp)
	{
		case 0x03 : key_d= key_table[row-1][0]; break;
		case 0x02 : key_d= key_table[row-1][1]; break;
		case 0x01 : key_d= key_table[row-1][2]; break;
		case 0x00 : key_d= key_table[row-1][3]; break;
		default: break;
	}
	return key_d;
}


void timer1Init()
{
	TCNT1H= (-39)>>8;// the high byte
	TCNT1L= (-39)&0xFF; //overflow after 31250 clocks
	
	TCCR1A = 0x00; //normal mode
	TCCR1B= 0x04; // internal clock set to 1:256;
	
	//enable Timer 1 on the Timermask
	
}
void int2Init()
{
	GICR|=(1<<INT2);
	MCUCSR|= (1<< ISC2);
	GIFR|=(1<<ISC2);
}

ISR(TIMER1_OVF_vect	){
	ms++;
	TCNT1H= (-39)>>8;// the high byte
	TCNT1L= (-39)&0xFF;
	//PORTB ^= (1<<PB0);
}

ISR(INT2_vect){
	
	keyPressed=(keyPressed+1)%2;
	if (keyPressed==1)
	{
		MCUCSR&=~(1<<ISC2); // switch on falling edge the interrupt
	}
	else
	{
		MCUCSR|= (1<< ISC2);// switch on the rising edge the interrupt
	}
	PORTB^=(1<<PB0);
	startTimer();
}

void startTimer()
{
	TIMSK|= (1<<TOIE1);
	ms=0;
}
void stopTimer()
{
	TIMSK&= ~(1<<TOIE1);
}
	