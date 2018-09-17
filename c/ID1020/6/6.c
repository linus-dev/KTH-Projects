/*
 * This program checks for a balance between paranthesis, braces, and brackets.
 * Author: Linus Berg, 2018
 */
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

/**
  * Recursively read characters from stdin and update the balance data.
  * @author Linus Berg 
  * @param balance* : Struct of balance data to update.
  * @param char : Previous character input.
  * @date 12/09/2018
  */
void ReadTest(balance *bal, char input) {
  /* Here you can add more things to check a balance. */
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
    /* Recursive call. */
    ReadTest(bal, getchar());
  }
}

