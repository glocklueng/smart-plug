/*
 * ADC.h
 *
 * Created: 23-Dec-14 12:06:19 PM
 *  Author: Dan
 */ 
#include <stdio.h>

#include <avr/io.h>
#include "Keypad.h"
void initADC();
unsigned int doSample();
void onADC();
void offADC();
long calculateEnergy();
void * createBuffer (size_t n);