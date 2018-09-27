#include <stdio.h>
#include <ctype.h>

int main() {
  char c;
  while((c = getchar()) != EOF) {
    putchar(c != '\n' && !isalpha(c) ? ' ' : c);
  }
  return 0;
}
