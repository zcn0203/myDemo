package com.test.my;

import java.util.Arrays;

public class QuickSortDemo {

    public static void main(String[] args) {

        int[] arr = {5, 8, 9, 7, 1, 2, 3, 0, 0, 1, 1, 2, 9, 8, 7, 1, 55, 12, 55, 33, 15, 61};
        int[] arr1 = {5, 8, 9, 7, 1, 2, 3, 0, 0, 1, 1, 2, 9, 8, 7, 1, 55, 12, 55, 33, 15, 61};

        quickSort(arr);
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr1);
        System.out.println(Arrays.toString(arr1));

    }

    private static void quickSort(int[] arr) {

        qSort(arr, 0, arr.length - 1);

    }

    private static void qSort(int[] arr, int low, int high) {

        if (low >= high) {
            return;
        }


        int mid = partation(arr, low, high);

        qSort(arr, low, mid-1);
        qSort(arr, mid+1, high);
    }

    private static int partation(int[] arr, int low, int high) {
        int key = arr[low];

        while (low < high) {
            while (arr[high] >= key && low < high) {
                high--;
            }
            arr[low] = arr[high];
            while (arr[low] <= key && low < high) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = key;
        return low;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

}
