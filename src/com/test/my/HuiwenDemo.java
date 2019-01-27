package com.test.my;

import java.util.Scanner;

public class HuiwenDemo {

    /**
     * 求一个字符串的最长回文数
     * @param args
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        getHuiwen(sc.nextLine());

    }

    private static void getHuiwen(String s) {

        int left;
        int right;
        int maxLeft = 0;
        int maxRight = 0;
        int maxLength = 0;

        //回文数为奇数情况
        for (int i = 1; i < s.length()-1; i++) {
            left = i-1;
            right = i+1;
            while (left >= 0 && right <= s.length()-1) {
                if (s.charAt(left) == s.charAt(right)) {
                    if (right - left > maxLength) {
                        maxLength = right - left;
                        maxLeft = left;
                        maxRight = right;
                    }
                    left--;
                    right++;
                } else {
                    break;
                }
            }
        }


        //回文数为偶数情况
        for (int i = 0; i < s.length()-1; i++) {
            left = i;
            right = i+1;
            while (left >= 0 && right <= s.length()-1) {
                if (s.charAt(left) == s.charAt(right)) {
                    if (right - left > maxLength) {
                        maxLength = right - left;
                        maxLeft = left;
                        maxRight = right;
                    }
                    left--;
                    right++;
                } else {
                    break;
                }
            }
        }
        System.out.println(s.substring(maxLeft,maxRight+1));
    }

}
