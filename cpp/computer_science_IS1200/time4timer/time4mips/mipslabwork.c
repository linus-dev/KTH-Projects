/* mipslabwork.c

   This file written 2015 by F Lundevall
   Updated 2017-04-21 by F Lundevall

   This file should be changed by YOU! So you must
   add comment(s) here with your name(s) and date(s):

   This file modified 2017-04-31 by Ture Teknolog 

   For copyright and licensing, see file COPYING */

#include <stdint.h>   /* Declarations of uint_32 and the like */
#include <pic32mx.h>  /* Declarations of system-specific addresses etc */
#include "mipslab.h"  /* Declatations for these labs */

int mytime = 0x5957;

char textstring[] = "text, more text, and even more text!";
int setTime(int time_c, int push);
int timeoutcount = 0;
/* Interrupt Service Routine */
void user_isr( void )
{
  return;
}

/* Lab-specific initialization goes here */
void labinit( void )
{
  T2CON = 0x70;
  TMR2 = 0x0;
  PR2 = 31250;
  *(volatile unsigned*)(0xBF886100) &= ~(0xFF);
  *(volatile unsigned*)(0xBF886110) = 0;
  TRISD |= 0xFE0;
  T2CONSET = 0x8000;
  return;
}

/* This function is called repetitively from the main program */
void labwork( void )
{ 
  if ((IFS(0) >> 8) & 0x1) {
    timeoutcount++;
    if (timeoutcount % 10 == 0) {
      mytime = setTime(mytime, 2);
      mytime = setTime(mytime, 3);
      mytime = setTime(mytime, 4);
      time2string( textstring, mytime );
      display_string( 3, textstring );
      display_update();
      tick( &mytime );
      display_image(96, icon);
    }
    IFS(0) &= ~(0x100);
  }
}

int setTime(int time_c, int btn) {
  btn--;
  if (getbtns() >> (btn - 1) & 0x1) {
    return (time_c & ~(0xF << (btn * 4))) | (getsw() << (btn * 4)); 
  } else {
    return time_c;
  }
}
