/**
  * This file implements a inversion count function.
  * Author: Linus Berg.
  */
#include "stdio.h"

int *InsertionSort(int array[], int size);
const void PrintArray(int array[], int size);
int CountInversions(int array[], int size);

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
  CountInversions(array_of_ints, size);
  InsertionSort(array_of_ints, size);
  return 0;
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

const void PrintArray(int array[], int size) {
  for (int i = 0; i < size; i++) {
    printf(" %d ", array[i]); 
  }
  printf("\n");
}

/**
  * CountInversions 
  * @desc Counts the inversions in the given array.
  * @param int *: Array to be sorted.
  * @param int: Size of the array.
  * @return int: # of inversions. 
  */
int CountInversions(int array[], int size) {
  int invs = 0;
  printf("\n");
  for (int i = 0; i < size - 1; i++) {
    for (int j = i + 1; j < size; j++) {
      if (array[i] > array[j]) {
        invs++;
        printf("[%d, %d], [%d, %d]\n", i, array[i], j, array[j]); 
      }
    }
  }
  return invs; 
} 
  
