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
extern volatile int keyPressed;
unsigned char buffer[10];

enum state
{
	idle,
	scanCard,
	enterPin,
	showMenu,
	showPrices,
	startCharging,
	charging,
	stopCharging,
	showInfo
};
state= idle;

void doStates(){
	switch (state)
	{
		case idle: 
					break;
		case scanCard:
					break;
		case enterPin:
					break;
		case showMenu:
					break;
		case showPrices:
					break;
		case startCharging:
					break;
		case charging:
					break;
		case stopCharging:
					break;
		showInfo;
					break;
		default: break;
		
		
		
	}
}

int main(void)
{   sei();
	initKeypad();
	USART_Init(64);
	timer1Init();
	int2Init();
	DDRB |= 0x01;
	PORTB |= (1<<PB0);
	ms=0;
	while(1){
		keyFound=0;
		sprintf(buffer, '%i',ms);
		Usart_sendString(buffer);
		if (scanKeyPad()==1) {
			
			USART_Transmit(key);}
			//if(key=='1')
			//PORTB^=(1<<PB0);
		
		
			
		
		
	}
}