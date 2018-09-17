/**
  * This file implements a sort that sorts an array based on negative and
  * positive values.
  * Author: Linus Berg.
  */
#include "stdio.h"

int *NegSort(int array[], int size);
const void PrintArray(int array[], int size);

int main() {
  char line[100];
  int size = 10;
  int array_of_ints[10] = {1, -3, -2, 5, -1, 6, -2, -10, 20, -40};
  NegSort(array_of_ints, size);
  return 0;
}

/**
  * NegSort 
  * @desc Sorts integer arrays based on negative or positive values.
  * @param int *: Array to be sorted.
  * @param int: Size of the array.
  * @return int *: Sorted array.
  */
int *NegSort(int array[], int size) {
  for (int i = 0; i < size; i++) {
    for (int j = i; j < size; j++) {
      /* If the current i is positive and the current j is negative, swap! */
      if (array[i] >= 0 && array[j] < 0) {
        /* array[i] is always positive here. */
        /* array[j] is always negative here. */
        int tmp = array[i];
        /* Continue to next element if i has been swapped. */
        array[i++] = array[j];
        array[j] = tmp;
        PrintArray(array, size);
      }
    }
  }
  return array;
}

const void PrintArray(int array[], int size) {
  for (int i = 0; i < size; i++) {
    printf(" %d ", array[i]); 
  }
  printf("\n");
}

