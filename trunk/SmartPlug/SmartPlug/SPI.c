/*
 * CFile1.c
 *
 * Created: 27-Oct-14 4:17:04 PM
 *  Author: Dan
 */ 
#include "SPI.h"
#include <avr/io.h>
#include <util/delay.h>
#define DDR_SPI DDRB
#define DD_MISO PB6
#define DD_MOSI PB5
#define DD_SCK PB7
#define DD_SS PB4

void SPI_MasterInit()
{
	
	/*Set MOSI and SCK output and SS as output*/
	DDR_SPI |= (1<<DD_MOSI)|(1<<DD_SCK)| (1<<PB0) | (1<<DD_SS);
	/* Enable SPI, Master, set clock-rate fck/16 */
	SPCR = (1<<SPE)|(1<<MSTR)|(1<<SPR0);
}

void SPI_MasterTransmit( char data)
{
	PORTB&= ~(1<<DD_SS);
	/*start transmission */
	SPDR = data;

	/* Wait for transmission complete */
	while(!(SPSR & (1<<SPIF)));  
					
	PORTB|= (1<<DD_SS); //
}

char SPIRecieve(void){
	
	PORTB&= ~(1<<DD_SS); //SS is Low
	
	while(!(SPSR & (1<<SPIF)));// Wait for transfer
	
	PORTB|= (1<<DD_SS); // SS High
	return SPDR;
}