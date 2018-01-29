#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

void print_number(int n) {
  static int printed_columns = 0;
  if (printed_columns % 6 == 0) {
    printf("\n");
  }
  printf(" %10d ", n);
  printed_columns++;
}

void print_sieves(int n) {
  char *sieves = malloc(n); 
  memset(sieves, 1, n);
  for (int i = 0; i <= sqrt(n); i++) {
    if (sieves[i]) {
      int counter = 0;
      int i_sq = pow(i + 2, 2);
      int j = i_sq;
      while (j <= n) {
        j = i_sq + counter*(i + 2);
        sieves[j - 2] = 0;
        counter++;
      }
      /*for (int j = i_sq; j <= n; j = i_sq + counter*(i + 2)) {
        counter++;
        sieves[j - 2] = 0;
      }*/
    }
  }

  /* Print array and resulting numbers */
  for (int i = 0; i < n; i++) {
    if (sieves[i]) {
      print_number(i + 2);
    }
  }
  printf("\n");
  /* ------ */
  free(sieves);
}

int main(int argc, char *argv[]) {
  print_sieves(atoi(argv[1]));
  return 0;
}
