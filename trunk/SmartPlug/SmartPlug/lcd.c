

#include <avr/io.h>    
#include <avr/interrupt.h>
#include <stdio.h> 


#include "lcd.h"  

char disp_buffer[32];    // One dimensional



int line =0;
char index=0;

// Setup your hierarchy of functions
// Names are just a proposal for your inspiration 

//*******************************************************************
// LCD initialization sequence (works somewhat like a constructor)
//*******************************************************************

void lcd_init()    // Works like a constructor
   
   {

   // Power on delay
	lcd_direction |= 0xfc;							//	set port a as output
   lcd_wait( 20000 );                                   // Power on wait 
   lcd_wait( 20000 );
	lcd_port &= ~((1<<lcd_E) | (1<<lcd_RS)); // EN=0, RS=0
//	lcd_port &= ~(1<<lcd_RW);               // Set RW = 0 in case it is connected
 
   lcd_nibble_transfer(SET_FUNCTION+IN8_BIT);
   //lcd_wait(longdelay );  						 // wait 24 ms
   _delay_ms(24);
   lcd_nibble_transfer(SET_FUNCTION+IN8_BIT);
   //lcd_wait(longdelay);
    _delay_ms(24);
	lcd_nibble_transfer(SET_FUNCTION+IN8_BIT);
   //lcd_wait(longdelay);
    _delay_ms(24);
	lcd_nibble_transfer(SET_FUNCTION);
  // lcd_wait(longdelay);
   _delay_ms(24);
   lcd_cmd_write(SET_FUNCTION+LN2_BIT);                 // Function set: 0x28  2 lines
  // lcd_wait(WAIT_15m);                              // Wait 39 uS
 _delay_ms(2);
    lcd_cmd_write(SET_DISPLAY);						//display off
 //lcd_wait(WAIT_15m);
_delay_ms(2);	
	lcd_cmd_write(CLR_DISPLAY);       // Display clear: 0x01 clear data                 	 
	
  // lcd_wait(WAIT_15m);                // 1.53 mS  
     _delay_ms(2);                  
                               

   lcd_cmd_write(SET_ENTRY_MODE+INC_BIT);          //  +INC_BIT  // Entry mode set: shift cursor 1 position to right
  //   lcd_wait(WAIT_15m);      
  _delay_ms(2);
    lcd_cmd_write(SET_DISPLAY+ON_BIT+CUR_BIT+BLK_BIT); //+CUR_BIT+BLK_BIT);   Display ON/OFF control: 0x0f
  //   lcd_wait(WAIT_15m);                               // Wait 39 uS                      // 1.53 mS     
_delay_ms(2);	
	lcd_cmd_write(RTN_HOME);
  //lcd_wait(WAIT_15m);                            // 1.53 mS  
_delay_ms(2);
  
   } // end lcd_init()






//******************************************************************************************
// Medium level functions 
// Select RS / RW mode and call lower level funtion to complete the transfer

void lcd_cmd_write(unsigned char cmd)
   { 
    lcd_direction |= 0xfc;
     //lcd_RS=0;
  lcd_port &= ~(1<<lcd_RS);
 
  // lcd_port &= ~(1<<lcd_RW);
  asm volatile("NOP");  // Slow down timing 100 nS
   asm volatile("NOP");   // Slow down timing 100 nS
   lcd_transfer(cmd);
   } // end lcd_cmd_write()


void lcd_data_write(unsigned char d)

   {
   lcd_direction |= 0xfc;
   lcd_port|=(1<<lcd_RS);				//rs=1 when writing data
   
  // lcd_port &= ~(1<<lcd_RW);
    asm volatile("NOP");   // Slow down timing 100 nS
   	asm volatile("NOP");   // Slow down timing 100 nS

   lcd_transfer(d); 
   } // end lcd_data_write()


//********************************************************************************************
// Low level functions
// Write to the lcd data bus - generate E pulse 

void lcd_transfer (unsigned char d)

   {
	 lcd_port|= (1<<lcd_E);
	  asm volatile("NOP");  // Slow down timing 100 nS
     asm volatile("NOP"); // Slow down timing 100 nS
 lcd_nibble_transfer(d);   //(msn)
 
//	 asm volatile("NOP");   // Slow down timing 100 nS	 
	 	asm volatile("NOP");   // Slow down timing 100 nS
		lcd_port |= (1<<lcd_E);

 lcd_nibble_transfer(d<<4);   //lsn);
 
   } // end lcd_transfer()


void lcd_nibble_transfer( unsigned char d )  
 
   { 
     lcd_port|= (1<<lcd_E);
	 asm volatile("NOP");   // Slow down timing 100 nS
   	lcd_port= (lcd_port & 0x0f)| (d &0xf0);   //(always msn as 4 bit data bus)
  
  	
 	 //lcd_wait(8);   // Slow down  58 us
_delay_us(60);
	lcd_port &= ~(1<<lcd_E);
//	lcd_wait(8);
_delay_us(60);
   } // end lcd_nibble_transfer()




//clear the display
void lcdClear(void) {

lcd_cmd_write(SET_DISPLAY);	           //display off
_delay_ms(20);
lcd_cmd_write(CLR_DISPLAY);       // Display clear: 0x01 clear data
_delay_ms(20);
lcd_cmd_write(RTN_HOME);
_delay_ms(2);
lcd_cmd_write(SET_DISPLAY+ON_BIT+CUR_BIT+BLK_BIT);
_delay_ms(2);
line=0;
index=0;

}

void clearLine(unsigned char x, unsigned char y){

unsigned char max_x=16;

GoTo(x,y);
for (int i=x; i<max_x; i++)
{
lcd_data_write(SET_FUNCTION);
_delay_ms(1);
}
GoTo(x,y);
}

// Software function for delay insertion after commands/data tranfers

void lcd_wait(unsigned int count)

   { 
   unsigned int i;

   for ( i = 0 ; i < count ; i++ );    //4 cycles per count
    // end lcd_wait()

  

   } // end lcd_transfer()


//! write a zero-terminated ASCII string to the display
void LCDPutString(char *str) {
   char c=0;
for (; (c = *str) != 0; str++){
	
	if((c=='\r') ||c=='\n');
	else
	 lcd_data_write(c);

index++;

if (index>15){
	
	if (line==0)
	{
		line=1;
	}
	
	else
	{
		line=0;
		
		lcdClear();
		
		
	}
	index=0;
	GoTo(index,line);
}

}
/*  while(*str) {
  c=*str;
lcd_data_write(c);
str++;*/
  
}

//*goto x-position and y-line called by parameters x, y used in main() and internally LCDPutChar()*/

void GoTo(unsigned char x, unsigned char y){

switch (y) {
case 0: 

 lcd_cmd_write(SET_DRAM_ADDR+line_0+x);
 lcd_wait(WAIT_15m);  
 break;

 case 1: 

lcd_cmd_write(SET_DRAM_ADDR+line_1+x);
 lcd_wait(WAIT_15m);  
break;


default:

break;
}
index=x;

}

