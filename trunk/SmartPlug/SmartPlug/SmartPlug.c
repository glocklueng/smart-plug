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
char* showPricesCommand="41";
char* pricesSentOkCommand="42";
char* card;
double pricePerHourDivider=1000000;
unsigned long energy=0;
char* location = "Copenhagen";
char* priceDay;
char* priceNight;
char bufferDay[32];
char bufferNight[32];


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

void sendData(char* command,char* id, char* pin, int letters)
{   //sprintf(buffer, "%02i%02i%02i%04s%16s%4s%1s%01s%03i%2s",12,34,11,"0022","868b5310e1000000","0123","9","9",123,"\r\n");
	sprintf(buffer, "%02i%02i%02s%04i%16s%4s%03i%2s",12,34,command,letters,id,pin,123, "\r\n");
	Usart_sendString(buffer);
}

initEverything()
{
	sei();
	lcd_init();
	initKeypad();
	USART_Init(64);
	timer1Init();
	int2Init();
	SPI_MasterInit();
	Init_SPI_interrupts();
	initADC();
	onADC();
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
		sendData(checkIdCommand,bufferCard,bufferPin,20);
		
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
	sendData(checkPasswordCommand,card,bufferPin,20);
	SerialGetString(inbuffer, sizeof(inbuffer));
	LCDPutString(inbuffer);
	_delay_ms(5000);	
	
	if (inbuffer[4]==passswordOkCommand[0])
	{
	
	if (inbuffer[5] == passswordOkCommand[1])
	{   
		sendData(showPricesCommand,location,bufferPin,20);
		
		state=showMenuState;
		SerialGetString(inbuffer, sizeof(inbuffer));
		LCDPutString(inbuffer);
		_delay_ms(5000);
		
		int count;
		
		int count2=0;
		int found=0;
		char ch;
		for (count = 10; count < strlen(inbuffer)-3; count ++){
			ch = inbuffer[count];
			if (found==0){
				if (ch!='-')
				{
					bufferDay[count2]=ch;
					count2++;
				}
				else
				{   bufferDay[count2]='\0';
					found=1;
					count2=0;
				}
			}
			else
			{
				bufferNight[count2]=ch;
				count2++;
			}
		}
		bufferNight[count2+1]='\0';
		lcdClear();
		free(buffer);
		sprintf(buffer,"%s", bufferDay);
		LCDPutString(buffer);
		GoTo(0,1);
		sprintf(buffer,"%s", bufferNight);
		LCDPutString(buffer);
		_delay_ms(5000);
	}
	else
	{
		state=enterPinState;
	}
	}
	}
	
	
}

void showMenu()
{
	if(showMenuFlag)
	{   lcdClear();
		LCDPutString("1.Show prices");
		LCDPutString("2.Start Charging");
		showMenuFlag=0;
	}
	keyFound=0;
	if (scanKeyPad()==1)
	{
		if (key=='1')
		{
			state=showPricesState;
		}
		if (key=='2')
		{
			state=chargingState;
		}
		if (key=='F')
		{
			state=idleState;
			initFlags();
		}
		
	}
}

void showPrices()
{	if (showPricesFlag)
	{	lcdClear();
		LCDPutString("Show prices menu");
		showPricesFlag=0;
	}
}

void startCharging()
{
	
}

void charging()
	{	if (chargingFlag)
		{
			startCharge();
			PORTB^=(1<<PB0);
			lcdClear();
			LCDPutString("Charge      ");
			chargingFlag=0;
			energy=0;
			
			timer1Init();
			startTimer();
		}
		
		if (ms>2000)
		
		PORTB &=~(1<<PB0);
		else
		
		PORTB |=(1<<PB0);
		
		//	onADC();
		
		if(ms>2000) {
			ms=0;
			
		//unsigned long totalEnergy=1000.0;
		calculateEnergy();
		void *buffer =createBuffer(16);  //create buffer
		if (buffer==ultoa(energy, buffer, 10)) {  //last number is the radix
			
			GoTo(6,1);
			LCDPutString("      ");
			_delay_ms(10);
			GoTo(6,1);
			LCDPutString(buffer);
			//putString(longBuffer);
			_delay_ms(10);
			free(buffer);      //free buffer
		}
		else
		LCDPutString("not converted correct");
		}
	
	
	
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
				//startTimer();
				while(1){
				doStates();
				}
				
			}
			
			}
			//if(key=='1')
			//PORTB^=(1<<PB0);
		
		
			
		
		
	}
}