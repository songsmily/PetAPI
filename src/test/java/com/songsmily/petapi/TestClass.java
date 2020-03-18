package com.songsmily.petapi;

import java.util.Arrays;
import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {

        Test2();

        Scanner scanner = new Scanner(System.in);
        int month = scanner.nextInt();
        System.err.println(Test3(month));

        Test4();
    }


    /**
     * 输出素数
     */
    public static void Test2(){
        Scanner scanner = new Scanner(System.in);
        int begin = scanner.nextInt();
        int end = scanner.nextInt();
        while (!(begin >= 2 && end >= begin && end <= 999999)){
            begin = scanner.nextInt();
            end = scanner.nextInt();
        }
        while (begin <= end){
            Boolean pos = true;
            for (int i = 2;i < begin;i++){
                if (begin % i == 0){
                    pos = false;
                }
            }
            if (pos){
                System.err.print(begin + " ");
            }
            begin++;
        }
    }
    /**
     * 兔子出生
     */
    public static  int Test3(int month){

        if(month==1 || month==2)
           return 1;
        else
            return (Test3(month-1)+Test3(month-2));
    }
    /**
     *     最高成绩
     */
    public static void Test4(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Integer[] arr = new Integer[n];
        int i = 0;
        while (i < n){
            arr[i] = scanner.nextInt();
            i++;
        }
        Arrays.sort(arr);
        System.err.println(arr[n-1] + " " + arr[0]);
    }
}
