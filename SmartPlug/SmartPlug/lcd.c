/*
 * lcd.c
 *
 * Created: 30-Oct-14 10:25:34 AM
 *  Author: Dan
 */ 


#include <avr/io.h>
#include "lcd.h"

#define Bitmask 0x0f

int cursor_Position=0;
int line =0;

void lcd_init(){
	DDRB |= 1<<PB0;
	DDRA |= 0b11111100;
	_delay_ms(2000);
	lcd_nibble_transfer(0x30);
	_delay_ms(25);
	lcd_nibble_transfer(0x30);
	_delay_ms(25);
	lcd_nibble_transfer(0x30);
	_delay_ms(25);
	lcd_nibble_transfer(SET_FUNCTION);
	_delay_ms(2);
	lcd_cmd_write(SET_FUNCTION + LN2_BIT);
	_delay_ms(2);
    lcd_cmd_write(SET_DISPLAY);	           //display off
_delay_ms(20);	
	lcd_cmd_write(CLR_DISPLAY);       // Display clear: 0x01 clear data                 	 
_delay_ms(20);                                              
   lcd_cmd_write(SET_ENTRY_MODE+INC_BIT);          //  +INC_BIT  // Entry mode set: shift cursor 1 position to right
_delay_ms(2);
    lcd_cmd_write(SET_DISPLAY+ON_BIT+CUR_BIT+BLK_BIT);     //+CUR_BIT+BLK_BIT);   Display ON/OFF control: 0x0f
_delay_ms(2);	
lcd_cmd_write(RTN_HOME);
_delay_ms(2);



}

void delay_ns(unsigned int x){
	
	for (int i = 0; i += 100; i<x){
		asm volatile("NOP");
	}
}

void lcd_cmd_write( unsigned char cmd){
	lcd_port &= ~(1<<lcd_RS); //Sæt RS til 0.
	delay_ns(200);
	lcd_transfer(cmd);
	
}
void lcd_transfer(unsigned char d){
	lcd_port |= (1<<lcd_E);	//Sæt E til 1.
	delay_ns(200);
	lcd_nibble_transfer(d);
	lcd_port |= (1<<lcd_E);	//Sæt E til 1.
	delay_ns(200);
	lcd_nibble_transfer(d<<4);
}

void lcd_nibble_transfer(unsigned char d){
	lcd_port = (lcd_port & Bitmask) | (d & ~Bitmask);
	_delay_us(58);
	lcd_port &= ~(1<<lcd_E); //Sæt E til 0.
	_delay_us(58);
	
}
void lcd_data_write( unsigned char d){
	lcd_port |= (1<<lcd_RS);
	delay_ns(200);
	lcd_transfer(d);
}

void putString(unsigned char *s){
	while (*s)
	{	
		unsigned char d = *s;
		lcd_data_write(d);
		s++;
		cursor_Position++;
		if (cursor_Position>15){
			if (line==0)
			{	
				line=1;
			}
						
			else
			{	
				line=0;
				_delay_ms(5000);
				lcdClear();
				
				
			}
			cursor_Position=0;
			goTo(line,cursor_Position);
		}
		
	}
}

void goTo(unsigned char x, unsigned char y)
{
	if (x ==0){
		lcd_cmd_write(SET_DRAM_ADDR+ line_0 + y);
	}
	else
	{
		lcd_cmd_write(SET_DRAM_ADDR+ line_1 + y);
	}
}

void lcd_wait( unsigned int );
void lcd_update();
void lcdClear()
{
	    lcd_cmd_write(SET_DISPLAY);	           //display off
	    _delay_ms(20);
	    lcd_cmd_write(CLR_DISPLAY);       // Display clear: 0x01 clear data
	    _delay_ms(20);
		lcd_cmd_write(RTN_HOME);
		_delay_ms(2);
		lcd_cmd_write(SET_DISPLAY+ON_BIT+CUR_BIT+BLK_BIT);
		_delay_ms(2);
		
		line=0;
		cursor_Position=0;
		

}
void lcdWrite(unsigned char *s);


void clearLine(unsigned char x, unsigned char y);


