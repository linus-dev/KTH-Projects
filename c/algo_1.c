#include "stdio.h"
#include "stdlib.h"

void ReadTest(char input);

int main() {
  ReadTest(getchar());
  return 0;
}

void ReadTest(char input) {
  (input != '\n' ? ReadTest(getchar()) : NULL);
  putchar(input);
}

