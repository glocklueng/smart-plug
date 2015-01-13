/*
 * ADC.h
 *
 * Created: 23-Dec-14 12:06:19 PM
 *  Author: Dan
 */ 
#include <stdio.h>

#include <avr/io.h>
#include "Keypad.h"
 extern unsigned long energy;
 extern uint16_t data;
extern double lastPower;
 extern uint16_t lastData;
void initADC();
unsigned int doSample();
void onADC();
void offADC();
extern unsigned long calculateEnergy();
void * createBuffer (size_t n);