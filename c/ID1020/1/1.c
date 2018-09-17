/*
 * The program takes input from the user and prints it in reverse order, until new line.
 */
#include "stdio.h"

void ReadTest(char input);

int main() {
  ReadTest(getchar());
  return 0;
}
/**
  * Recursively read characters from stdin.
  * @author Linus Berg 
  * @param char : Previous character input.
  * @date 12/09/2018
  */
void ReadTest(char input) {
  input != '\n' ? ReadTest(getchar()) : NULL;
  putchar(input);
}

