/*
 print-prime.c
 By David Broman.
 Last modified: 2015-09-15
 This file is in the public domain.
*/


#include <stdio.h>
#include <stdlib.h>

#define COLUMNS 6

int is_prime(int n){
  if (n <= 1) {
    return 0;
  } else if (n <= 3) {
    return 1;
  } else if (n % 2 == 0 || n % 3 == 0) {
    return 0;
  }
  for (int i = 5; i*i <= n; i += 6) {
    if (n % i == 0 || n % (i + 2) == 0) {
      return 0;
    }
  }
  return 1;
}

void print_number(int n) {
  static int printed_columns = 0;
  if (printed_columns % 6 == 0) {
    printf("\n");
  }
  printf(" %10d ", n);
  printed_columns++;
}

void print_primes(int n) {
  // Should print out all prime numbers less than 'n'
  // with the following formatting. Note that
  // the number of columns is stated in the define
  // COLUMNS
  for (int i = 2; i <= n; i++) {
    if (is_prime(i)) {
      print_number(i);
    }
  }
  printf("\n");
}

// 'argc' contains the number of program arguments, and
// 'argv' is an array of char pointers, where each
// char pointer points to a null-terminated string.
int main(int argc, char *argv[]){
  if(argc == 2)
    print_primes(atoi(argv[1]));
  else
    printf("Please state an interger number.\n");
  return 0;
}

 
