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
#define DEBUG 1
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
unsigned char bufferPin[10];
int pinIndex;



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
					#if DEBUG
					Usart_sendString("Idle ");
					#endif
					lcdClear();
					
			 LCDPutString("Swipe");
			 idleFlag=0;}
			 if (cardPresent) {
			 state=scanCard;
			 }
					break; 
		case scanCard:{ 
					if (scanCardFlag==1){
					scanCardFlag=0;
					lcdClear();
					
					//
					//GoTo(0,1);
					LCDPutString("Press any key ->");
					//LCDPutString(bufferCard);
					#if DEBUG
					Usart_sendString("Scan Card ");
					#endif
					}
					keyFound=0;
					if (scanKeyPad()==1)
					{	if (key=='A')
						state=idle;
						if (key=='B')
						state=enterPin;
						#if DEBUG
						Usart_sendString("KeyPressed ");
						#endif
						key='n';
					}
					break;
					}
		case enterPin:
					{
					lcdClear();
						
					#if DEBUG
					Usart_sendString("Enterpin ");
					#endif
					pinIndex=0;
					while (key!='B')
					{
						keyFound=0;
						if (scanKeyPad()==1)
						{   if (key>='0' & key<='9')
							{
							bufferPin[pinIndex]=key;
							GoTo(pinIndex,0);
							LCDPutString("*");
							pinIndex++;
							Usart_sendString(" Pin:");
							Usart_sendString(bufferPin);
							}
							else
							if (key=='C' & pinIndex!=0)
							{	pinIndex-=1;
								GoTo(pinIndex,0);
								
								
								LCDPutString(" ");
								Usart_sendString(" Pin:");
								Usart_sendString(bufferPin);
								
								
							}
							
							
							
						}
					}
					#if DEBUG
					Usart_sendString("B Key pressed after PIN Inserted ");
					#endif
					state=charging;
					key='n';
					break;
					}
		case showMenu:
					break;
		case showPrices:
					break;
		case startCharging:
					break;
		case charging:
						{
							startCharge();
							sprintf(buffer, '%d', calculateEnergy());
							lcdClear();
							LCDPutString("Charge      ");
							 GoTo(10,1);
							 LCDPutString("      ");
							 _delay_ms(10);
							 GoTo(10,1);
							 LCDPutString(buffer);
							 //putString(longBuffer);
							 _delay_ms(10);
							 #if DEBUG
							 Usart_sendString("Charging ");
							 #endif
							 keyFound=0;
							 if (scanKeyPad()==1)
							 {
								 
							 if (key=='A')
							 {
							 state=stopCharging;
							 
							 #if DEBUG
							 
							 #endif
							 }
							 
							}
							 break;
						}
		case stopCharging:
					{	LCDPutString("Stop      ");
						#if DEBUG
						Usart_sendString("Charging Stopped ");
						#endif
						stopCharge();
						break;
					}
		case showInfo:
					{
						
						break;
					}
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
	initADC();
	onADC();
	DDRB |= 0x01;
	PORTB |= (1<<PB0);
	ms=0;
	while(1){
		keyFound=0;
		sprintf(buffer, '%i',ms);
		Usart_sendString(buffer);
		if (scanKeyPad()==1) {
			
			USART_Transmit(key);
			if (key=='A'){
				initFlags();
				state=idle;
				key='n';
				while(1){
					
					doStates();
					
				}
			}
			sprintf(buffer,"%c", key);
			LCDPutString(buffer);
			}
			//if(key=='1')
			//PORTB^=(1<<PB0);
		
		
			
		
		
	}
}