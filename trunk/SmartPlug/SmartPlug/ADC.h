/*
 * ADC.h
 *
 * Created: 23-Dec-14 12:06:19 PM
 *  Author: Dan
 */ 

void initADC();
unsigned int doSample();
void onADC();
void offADC();
double calculateEnergy();