/*
 * SmartPlug.c
 *
 * Created: 10-Nov-14 1:13:39 PM
 *  Author: Dan
 */ 


#include <avr/io.h>

#include "Keypad.h"
#include "uart.h"
#include "lcd.h"
#include "SPI.h"

extern volatile int ms;
extern unsigned char key;
extern volatile int keyPressed;
unsigned char buffer[10];
int idleFlag;
int scanCardFlag;
int enterPinFlag;
int showMenuFlag;
int showPricesFlag;
int startChargingFlag;
int chargingFlag;
int stopChargingFlag;
int showInfoFlag;



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

void initFlags(){
	idleFlag =1;
	scanCardFlag=1;
	enterPinFlag=1;
	showMenuFlag=1;
	showPricesFlag=1;
	startChargingFlag=1;
	chargingFlag=1;
	stopChargingFlag=1;
	showInfoFlag=1;
}

void doStates(){
	switch (state)
	{
		case idle: if (idleFlag==1) 
				{ 
			 LCDPutString("Swipe card to start charging");
			 idleFlag=0;}
			 
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
	lcd_init();
	initKeypad();
	USART_Init(64);
	timer1Init();
	int2Init();
	SPI_MasterInit();
	Init_SPI_interrupts();
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