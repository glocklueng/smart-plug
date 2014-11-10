
 #include "uart.h"
 #include <avr/io.h>
 #include <avr/interrupt.h>
void USART_Init( unsigned int baud )
{
	//DDRB |= 0x01;
	//PORTB |= (1<<PB0);
	
	/* Set baud rate */
	UBRRH = (unsigned char)(baud>>8);
	UBRRL = (unsigned char)baud;
	/* Enable receiver and transmitter and interrupt */
	UCSRB = (1<<RXEN)|(1<<TXEN) | (1<<RXCIE);
	/* Set frame format: 8data, 0stop bit */
	UCSRC = (1<<URSEL)|(3<<UCSZ0);
	UCSRA = (1<<U2X);
	//interrupt.
	//asm("SEI");
	//SREG=0x80;
}

unsigned char USART_Receive( void )
{
	/* Wait for data to be received */
	while ( !(UCSRA & (1<<RXC)) );
	/* Get and return received data from buffer */
	return UDR;
	
}


void USART_Transmit( unsigned char data )
{
	/* Wait for empty transmit buffer */
	while ( !( UCSRA & (1<<UDRE)) );
	/* Put data into buffer, sends the data */
	UDR = data;
}