/*
 * ADC.c
 *
 * Created: 23-Dec-14 12:06:11 PM
 *  Author: Dan
 */ 
#include "ADC.h"
#include <avr/io.h>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      

unsigned char data;
double energy;

void initADC(){
	ADMUX|=   (1<<REFS0)|(1<<MUX4) ;   //(1<<REFS1) |(1<<REFS0) the internal adcref: external ref  (1<<REFS0); and adc0 selected
	//SFIOR|=(5<<ADTS0); //enable sample by timer 1B compare match
	ADCSRA|= (1<<ADEN)|(1<ADPS0)|(1<ADPS1)|(1<ADPS2);   //enable triggered sampling  |(1<<ADATE)
	DDRD|=(1<<PIND4);
	
	   
	   
	   
}

void startCharge()
{
	PORTD|=(1<<PIND4); //start charging
}

void stopCharge()
{
	PORTD&=~(1<<PIND4); //stop charging
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


double calculateEnergy(){
	data=doSample();
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

	return energy;
}