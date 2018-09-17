/* This implements insertion sort and prints number of swaps. */
#include "stdio.h"

int *InsertionSort(int array[], int size);
const void PrintArray(int array[], int size);

int main() {
  char line[100];
  int size;
  printf("Input the amount of integers you wish to sort: ");
  fgets (line, sizeof(line), stdin); 
  sscanf (line, "%d", &size);
  
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
  int num_swaps = 0;
  for (int i = 1; i < size; i++) {
    for (int j = i; j > 0 && (array[j] > array[j-1]); j--) {
      int tmp = array[j];
      array[j] = array[j - 1];
      array[j - 1] = tmp;
      num_swaps++;
      PrintArray(array, size);
    }
  }
  printf("Number of swaps: %d\n", num_swaps);
  return array;
}
