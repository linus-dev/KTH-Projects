/*
    clock.cpp
    Description: Simple clock with an alarm function. 

    @author Linus Gunnarsson
    @github linus-dev
*/

#include <stdio.h>
#include <stdlib.h> 
#include <time.h>
#include <math.h>
#include <string.h>

void timeToArray(int cur_time, int *array_to_sort);
void timeWait();
int addTime(int cur_time);
int getInput();

int main(int argc, const char *argv[]) {
  int present_time;
  int time_for_alarm;
  int split_time[3] = {0, 0, 0};
  
  printf("C-c to quit program.\n");
  printf("Input current time, format: HHMMSS \n");
  present_time = getInput(); 
  printf("Input time for alarm, format: HHMMSS \n");
  time_for_alarm = getInput();
  /* In case time is out of normal range. */
  if ((present_time < 0 || present_time > 240000) ||
      (time_for_alarm < 0 || time_for_alarm > 240000)) {
    printf("Time or alarm out of possible range, 0-240000.\n");
    return 1;
  }
  
  do {
    /* Wait until one second has passed */
    timeWait();
    /* Add one second to our time */
    present_time = addTime(present_time);
    /* Format the time into an array for easier usage in printing */
    timeToArray(present_time, split_time);
    /* If it is time for the alarm, print 'ALARM' otherwise, print time */
    if (present_time != time_for_alarm) { 
      printf("%02d:%02d:%02d\n", split_time[0], split_time[1], split_time[2]);
    } else {
      printf("ALARM\n");  
    }
  } while (present_time != time_for_alarm); 

  return 0;
}

/* Wait one second according to system time. */
void timeWait() {
  int sys_time_now = time(0);
  while (time(0) < sys_time_now + 1) {
   /* Loop until time matches. */ 
  }
}

/* Add one second to current time. */
int addTime(int cur_time) {
  /* New time */
  int n_time = cur_time + 1;
  int time_array[3] = {0, 0, 0};
  timeToArray(n_time, time_array);

  /*
    In case seconds exceed 60, if it does, also check if minutes exceed 60.
    Also reset time with time % 240000 in case it exceeds it.
  */ 
  if (fmod(time_array[2], 60) == 0 || time_array[2] > 60) {
    n_time -= 60;
    n_time += 100;
    timeToArray(n_time, time_array);
    if (fmod(time_array[1], 60) == 0 || time_array[1] > 60) {
      n_time -= 6000;
      n_time += 10000;
    } 
  }
  n_time = fmod(n_time, 240000);
  
  return n_time;
}

/* Splits time to usable format. */
void timeToArray(int cur_time, int *array_to_sort) {
  int hour = cur_time / 10000;
  int minute = (cur_time / 100) % 100;
  int sec = fmod(cur_time, 100);
  *array_to_sort = hour;
  *(array_to_sort + 1) = minute;
  *(array_to_sort + 2) = sec; 
}

/* Get input by fgets to make sure no buffer overflow occurs */
int getInput() {
  int input_int;
  char input_buffer[7];
  if (fgets(input_buffer, sizeof(input_buffer), stdin) != NULL) {
    /* strtol, Ignoring end character pointer (NULL) */
    input_int = strtol(input_buffer, NULL, 10); 
  }
  /*
    If user decided to input more than allowed,
    consume the rest of the input, otherwise it
    will be read by the next input (ALARM).
  */
  if (strlen(input_buffer) > 5) {
    int ch;
    while ((ch = getchar()) != '\n' && ch != EOF);
  }   
  return input_int;
}
