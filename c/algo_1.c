#include "stdio.h"
#include "stdlib.h"

typedef struct char_node {
  char c;
  /* We print backwards. */
  struct char_node *prev_node;
} char_node;

char_node *ReadC();

int main() {
  char_node *node = ReadC(NULL);
  while (node != NULL) {
    char_node *old_node = node;
    putchar(node->c);
    node = node->prev_node;
    free(old_node);
  }
  return 0;
}

/* Read char. */
char_node *ReadC(char_node *prev_node) {
  char input = getchar();
  /* If it is not a new line keep going. */
  if (input != '\n') {
    char_node *node = malloc(sizeof(char_node));
    node->prev_node = prev_node;
    node->c = input;
    return ReadC(node);
  }
  /* If a new line is achieved return the previous node. */
  return prev_node;
}


