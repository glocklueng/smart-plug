/*
 * ADC.c
 *
 * Created: 23-Dec-14 12:06:11 PM
 *  Author: Dan
 */ 
#include "ADC.h"
#include <util/delay.h>
 unsigned long energy=0;
 uint16_t data=0;
 double lastPower=0;
 uint16_t lastData=0;

void initADC(){
	ADMUX|=   (1<<REFS0)|(1<<MUX4) ;   //(1<<REFS1) |(1<<REFS0) the internal adcref: external ref  (1<<REFS0); and adc0 selected
	//SFIOR|=(5<<ADTS0); //enable sample by timer 1B compare match
	ADCSRA|= (1<<ADEN)|(1<ADPS0)|(1<ADPS1)|(1<ADPS2);   //enable triggered sampling  |(1<<ADATE)
	DDRD|=(1<<PIND4);
	
	   
	   
	   
}

void startCharge()
{
	PORTD|=(1<<PIND4); //start charging
	timer1Init();
	TIMSK|= (1<<TOIE1);
	
}

void stopCharge()
{
	PORTD&=~(1<<PIND4); //stop charging
	TIMSK&= ~(1<<TOIE1);
}
   
   
unsigned int doSample(){
	
	unsigned int value=0;
	ADCSRA|=(1<<ADSC);
	while((ADCSRA & (1<<ADSC)));
	
	value= ADCL+ ADCH*256;
	return value&0x3FF;
}
void onADC(){
	ADCSRA|= (1<<ADEN);
	
	
}
void offADC() {
	ADCSRA &=~(1<<ADEN);
	
}


long calculateEnergy(){
	if (ms>2000)
	
	PORTB &=~(1<<PB0);
	else
	
	PORTB |=(1<<PB0);
	
	//	onADC();
	
	if(ms>2000) {
		ms=0;
		

		
		data=doSample();
		
		//only update is different values
		if(lastData!=data) {
			
			GoTo(10,3);
			_delay_ms(10);
			LCDPutString("    ");
			GoTo(10,1);
			_delay_ms(10);
			//create buffer
			void *buffer =createBuffer(5);
			//unsigned integer check for correct converting
			if(sprintf(buffer, "%u", data ) ){
				
				LCDPutString(buffer);
				lastData=data;
				free(buffer);  //free buffer
			}
			else
			LCDPutString("not correct converted");
		}
		double vin= data*5000/512;   //mW  uW 0.4*1.023
		double power= vin * vin;
		
		if (power==0)
		{
			energy=0;
		}
		else
		{
			energy+=   power;
		}
		
		
		//only update if new value
		if(lastPower!=power) {
			//check for correct update!
			void *buffer =createBuffer(8);   //create buffer
			if(snprintf(buffer, 8,"%.3f \r\n", (double) power )) {
				
				
				GoTo(10,1);
				_delay_ms(10);
				LCDPutString("       ");   //clear number
				_delay_ms(10);
				GoTo(10,1);
				_delay_ms(10);
				LCDPutString(buffer);
				lastPower=power;
				free(buffer);   // free buffer
			}
			else
			LCDPutString("not converted");
			
		}
		void *buffer =createBuffer(16);  //create buffer
		if (buffer==ultoa(energy, buffer, 10)) {  //last number is the radix
			
			GoTo(10,1);
			LCDPutString("      ");
			_delay_ms(10);
			GoTo(10,1);
			LCDPutString(buffer);
			//putString(longBuffer);
			_delay_ms(10);
			free(buffer);      //free buffer
		}
		else
		LCDPutString("not converted correct");
		
	}
	return energy;
}

   void * createBuffer (size_t n) {
	   void *arrayPtr;
	   if((arrayPtr =malloc(n))==NULL)   //dynamic allocation 23 elements
	   exit(1);  //forced stop of execution
	   return arrayPtr;
   }
   
   
   void * gcalloc( size_t n, size_t sizeof_something){
	   void *p;
	   if((p =calloc(n, sizeof_something))==NULL){
		   exit(1);
	   }
	   return p;
   }