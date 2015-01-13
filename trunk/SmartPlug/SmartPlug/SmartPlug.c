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
unsigned char buffer[32];
unsigned char bufferADC[10];
int idleFlag;
int scanCardFlag;
int enterPinFlag;
int showMenuFlag;
int showPricesFlag;
int startChargingFlag;
int chargingFlag;
int stopChargingFlag;
int showInfoFlag;
char* bufferPin="0000";
int pinIndex;
char inbuffer[32]; //Incoming packet
char* checkIdCommand = "11";
char* idOkCommand="12";
char* idNotOkCommand="13";
char*checkPasswordCommand= "21";
char* passswordOkCommand="22";
char* passwordNotOkCommand="23";
char* showBalanceCommand="31";
char* card;



enum state
{
	idleState,
	scanCardState,
	enterPinState,
	showMenuState,
	showPricesState,
	startChargingState,
	chargingState,
	stopChargingState,
	showInfoState
};
state= idleState;

void sendData(char* command,char* id, char* pin)
{   //sprintf(buffer, "%02i%02i%02i%04s%16s%4s%1s%01s%03i%2s",12,34,11,"0022","868b5310e1000000","0123","9","9",123,"\r\n");
	sprintf(buffer, "%02i%02i%02s%04s%16s%4s%1s%01s%03i%2s",12,34,command,"0022",id,pin,"9","9",123, "\r\n");
	Usart_sendString(buffer);
}

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
	cardPresent=0;
}

void idle()
{   
	if (idleFlag==1)
	{   
		
		lcdClear();
		LCDPutString("Swipe card ");
		GoTo(0,1);
		LCDPutString("to charge");
	idleFlag=0;
	}
	if (cardPresent==1 & ignoreFirstRead>0) 
	{
		state=scanCardState;
	}
}

void scanCard()
{
	if (scanCardFlag==1){
		scanCardFlag=0;
		lcdClear();
		card=bufferCard;
		sendData(checkIdCommand,bufferCard,bufferPin);
		
		SerialGetString(inbuffer, sizeof(inbuffer));
		LCDPutString(inbuffer);
		if (inbuffer[4]==idOkCommand[0])
		{
			if (inbuffer[5]==idOkCommand[1])
			state=enterPinState;
			else
			{
				initFlags();
			    state=idleState;
			}
			
		}
		//GoTo(0,1);
		
		//LCDPutString("Press any key -> \n Hello");
		//LCDPutString(bufferCard);
		
	}
	
}

void enterPin()
{
	lcdClear();
	LCDPutString("Enter pin:");
	pinIndex=index;
	GoTo(0,1);
	LCDPutString("Press A after");
	int count=0; //count index
	while (key!='A' )
	{
		keyFound=0;
		if (scanKeyPad()==1)
		{   if (key>='0' & key<='9')
			{   if (count<4)
				{
				bufferPin[count]=key;
				GoTo(pinIndex,0);
				LCDPutString("*");
				pinIndex++;
				count++;
				}
				//Usart_sendString(" Pin:");
				//Usart_sendString(bufferPin);
			}
			else
			if (key=='C' & pinIndex!=0)
			{	pinIndex=pinIndex-1;
				GoTo(pinIndex,0);
				LCDPutString(" ");
				count--;
				GoTo(pinIndex,0);
				//Usart_sendString(" Pin:");
				//Usart_sendString(bufferPin);
			}
		}
	}
	if (count<4)
	{
		state=enterPinState;	
	}
	else
	{
	lcdClear();
	sendData(checkPasswordCommand,card,bufferPin);
	SerialGetString(inbuffer, sizeof(inbuffer));
	LCDPutString(inbuffer);
	_delay_ms(5000);	
	
	if (inbuffer[4]==passswordOkCommand[0])
	{
	
	if (inbuffer[5] == passswordOkCommand[1])
	{
		state=chargingState;
	}
	else
	{
		state=enterPinState;
	}
	}
	}
	key='n';
	
}

void showMenu()
{
	
}

void showPrices()
{
	
}

void startCharging()
{
	
}

void charging()
{	if (chargingFlag)
	{
	startCharge();
	
	lcdClear();
	LCDPutString("Charge      ");
	chargingFlag=0;
	}
	
		calculateEnergy();
		//ultoa(calculateEnergy(),bufferADC,10);
		//Usart_sendString("charging: ");
		//Usart_sendString(bufferADC);
		
		//putString(longBuffer);
		_delay_ms(10);
		ms=0;
	
	
	
	keyFound=0;
	if (scanKeyPad()==1)
	{
		
		if (key=='A')
		{	initFlags();
			state=stopChargingState;
			
			#if DEBUG
			
			#endif
		}
		
	}
	
}

void stopCharging()
{	LCDPutString("Stop      ");
	
	stopCharge();
	
}

void showInfo()
{
	
}

void doStates(){
	switch (state)
	{
		case idleState: idle();break; 
		case scanCardState: scanCard(); break;
		case enterPinState: enterPin(); break;					
		case showMenuState: showMenu(); break;
		case showPricesState: showPrices(); break;
		case startChargingState: startCharging();	break;
		case chargingState: charging(); break;						
		case stopChargingState: stopCharging(); break;
		case showInfoState: showInfo(); break;
					
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
		if (scanKeyPad()==1) {
			sprintf(buffer,"%c", key);
			LCDPutString(buffer);
			//USART_Transmit(key);
			if (key=='A'){
				initFlags();
				state=idleState;
				key='n';
				while(1){
					
					doStates();
					
				}
			}
			if (key=='B')
			{
				   //Usart_sendString("In while loop    ");
					//SerialGetString(inbuffer, sizeof(inbuffer));
					//char c= USART_Receive();
					//Usart_sendString("After receiving  ");
					//Usart_sendString("hello21312412312312\0");
					//USART_Transmit(c)
					//Usart_sendString("After sending  ");
					
					sprintf(buffer, "%02i%02i%02i%04s%16s%4s%1s%01s%03i%2s",12,34,11,"0022","868b5310e1000000","0123","9","9",123,"\r\n");
					Usart_sendString(buffer);
					
				
			}
			
			if(key=='C')
			{
				state= chargingState;
				initFlags();
				while(1)
				doStates();
				startTimer();
			}
			
			}
			//if(key=='1')
			//PORTB^=(1<<PB0);
		
		
			
		
		
	}
}