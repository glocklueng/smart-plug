/*
 * CFile1.c
 *
 * Created: 27-Oct-14 4:17:04 PM
 *  Author: Dan
 */ 
#include "SPI.h"
#include <avr/io.h>
#include <util/delay.h>

#include "uart.h"
unsigned char buffer[16];
unsigned char buffer2[16];
char dataS;

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



void Init_SPI_interrupts()
{
	GICR|=(1<<INT0) | (1 << INT1);     //enable external interrupt 0 and 1
	MCUCR|=(1<<ISC00)|(1<<ISC01) |(1<<ISC11) | (1<<ISC10);   //get the interrupts on rising edge for int1 and any logical change for int0
	sei(); 	//enable global interrupts
}

/*Interrupt service routine for INT0*/

ISR(INT0_vect) {
	PORTB^=(1<<PB0);
	//USART_Transmit('0');
	SPI_MasterTransmit(0x55);
	//USART_Transmit('2');
}

ISR(INT1_vect) //interrupt 1
{   //USART_Transmit('1');
	PORTB^=(1<<PB0);
	
	int i=0;
	while (PIND & (1<<PD3))
	{   SPI_MasterTransmit(0xF5);
		_delay_ms(20);
		buffer[i]=SPIRecieve();
		i++;
		
	}
	
	i=0;
	while(i<=7){
		char temp[2];
		sprintf(temp, "%02x", buffer[i]);
		buffer2[2*i] = temp[0];
		if (i!=7)
		buffer2[2*i+1] = temp[1];
		i++;
		
	}
	
	Usart_sendString("\n");
	Usart_sendString(buffer2);
	Usart_sendString("\n");
}

