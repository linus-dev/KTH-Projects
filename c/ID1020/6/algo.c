#include "stdio.h"

typedef struct {
  int para;
  int brace;
  int brack;
} balance;

void ReadTest(balance *bal, char input);

int main() {
  balance data;
  data.para = 0;
  data.brace = 0;
  data.brack = 0;
  ReadTest(&data, getchar());
  printf("%s", (!data.para ? "" : "UNBALANCED PARANTHESIS!\n"));
  printf("%s", (!data.brace ? "" : "UNBALANCED BRACES!\n"));
  printf("%s", (!data.brack ? "" :  "UNBALANCED BRACKETS!\n"));
  return 0;
}

void ReadTest(balance *bal, char input) {
  switch (input) {
    case '(': {
      bal->para++;
      break;
    }
    case ')': {
      bal->para--;
      break;
    }
    case '{': {
      bal->brace++;
      break;
    }
    case '}': {
      bal->brace--;
      break;
    }
    case '[': {
      bal->brack++;
      break;
    }
    case ']': {
      bal->brack--;
      break;
    }
    default: {
      break;
    }
  }
  if (input != EOF) {
    putchar(input);
    ReadTest(bal, getchar());
  }
}

