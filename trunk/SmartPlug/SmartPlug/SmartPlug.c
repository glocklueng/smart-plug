/*
 * SmartPlug.c
 *
 * Created: 10-Nov-14 1:13:39 PM
 *  Author: Dan
 */ 


#include <avr/io.h>

#include "Keypad.h"
#include "uart.h"
extern volatile int ms;
extern unsigned char key;

void timer1Init()
{
	TCNT1H= (-39)>>8;// the high byte
	TCNT1L= (-39)&0xFF; //overflow after 31250 clocks
	
	TCCR1A = 0x00; //normal mode
	TCCR1B= 0x04; // internal clock set to 1:256;
	
	TIMSK= (1<<TOIE1); //enable Timer 1 on the Timermask
	
}


ISR(TIMER1_OVF_vect	){
	ms++;
	TCNT1H= (-39)>>8;// the high byte
	TCNT1L= (-39)&0xFF;
	//PORTB ^= (1<<PB0);
}



int main(void)
{   sei();
	initKeypad();
	
	USART_Init(64);
	timer1Init();
	DDRB |= 0x01;
	PORTB |= (1<<PB0);
	ms=0;
	while(1){
		
		if(scanKeyPad())
		{
			
			PORTB^=(1<<PB0);
		}
		
			
		
		
	}
}