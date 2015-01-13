
void USART_Init( unsigned int baud );
void USART_Transmit( unsigned char data );
unsigned char USART_Receive( void );
void Usart_sendString(unsigned char *str);
char *SerialGetString(char *str, unsigned char len);