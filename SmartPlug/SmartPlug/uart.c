
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
	UCSRB = (1<<RXEN)|(1<<TXEN);
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
void Usart_sendString(unsigned char *str){
	unsigned char c;
	for (;(c=*str); str++){
		USART_Transmit(c);
	}
}


//	Reads a character string terminated by the newline character ('\r' or '\n')
// The newline character will be replaced with 0.
// The maximum length of the string is len. If len characters were read
// without encountering the newline character, then the string is terminated
// with 0 and the function returns.
// str must be a character array of at least len characters.
char *SerialGetString(char *str, unsigned char len) {
	char c = 1;
	char * s;
	for (s = str; c; s++, len--) {
		if (len <= 1) break;             // stopping at len
		c = USART_Receive();             // read character
		//if (c=='\r') break;              // stop at carriage return
		if (c=='\r') c = USART_Receive();// ignore '\r' before '\n'
		if (c=='\n') break;              // stop at newline
		*s = c;                          // put character into buffer
	}
	*s = 0;
	return str;
}
