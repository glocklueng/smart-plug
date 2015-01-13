/*
 * SPI.h
 *
 * Created: 27-Oct-14 4:43:27 PM
 *  Author: Dan
 */ 
#define DDR_SPI DDRB
#define DD_MISO PB6
#define DD_MOSI PB5
#define DD_SCK PB7
#define DD_SS PB4
#include <avr/interrupt.h>
extern unsigned char cardPresent;
extern unsigned char bufferCard[17];
void SPI_MasterInit();
void SPI_MasterTransmit( char data);
char SPIRecieve(void);
void Init_SPI_interrupts();
extern int ignoreFirstRead;

