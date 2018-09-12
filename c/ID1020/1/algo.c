/*
 * The program takes input from the user and prints it in reverse order, until new line.
 */
#include "stdio.h"

void ReadTest(char input);

int main() {
  ReadTest(getchar());
  return 0;
}

void ReadTest(char input) {
  input != '\n' ? ReadTest(getchar()) : NULL;
  putchar(input);
}

