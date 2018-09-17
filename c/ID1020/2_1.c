/* This file implements an insertion sort. */
#include "stdio.h"

int *InsertionSort(int array[], int size);
const void PrintArray(int array[], int size);

int main() {
  char line[100];
  int size;
  
  /* Size of the array. */
  printf("Input the amount of integers you wish to sort: ");
  fgets (line, sizeof(line), stdin); 
  sscanf (line, "%d", &size);

  /* Allow the user to input integers. */ 
  printf("\nInput integers (one at a time):\n");
  int array_of_ints[size];
  for (int i = 0; i < size; i++) {
    scanf("%d", (array_of_ints + i));
  }

  InsertionSort(array_of_ints, size);
  return 0;
}

const void PrintArray(int array[], int size) {
  for (int i = 0; i < size; i++) {
    printf(" %d ", array[i]); 
  }
  printf("\n");
}

/**
  * InsertionSort
  * @desc Sorts integer arrays using insertion sort.
  * @param int *: Array to be sorted.
  * @param int: Size of the array.
  * @return int *: Sorted array.
  */
int *InsertionSort(int array[], int size) {
  for (int i = 1; i < size; i++) {
    /* If the previous element is greater than the current, swap them. */
    for (int j = i; j > 0 && (array[j] < array[j-1]); j--) {
      int tmp = array[j];
      array[j] = array[j - 1];
      array[j - 1] = tmp;
      PrintArray(array, size);
    }
  }
  return array;
}
