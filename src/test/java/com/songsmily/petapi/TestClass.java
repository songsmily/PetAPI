package com.songsmily.petapi;

import com.songsmily.petapi.utils.IdWorker;
import com.songsmily.petapi.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestClass {

    @Test
    public  void test1(){
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
    }
    private static void test(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            try {
                if (arr[i] % 2 == 0) {
                    throw new NullPointerException();
                } else {
                    System.out.print(i);
                }
            } finally {
                System.out.print("e");
            }
        }
    }

    private static void countChar(){
        String str = "adafaadfgsdAfafdfdFperneafnAfaf";
        char[] strArr = str.toCharArray();
        Map<String, Integer> map  = new HashMap<>();
        for (int i = 0; i < strArr.length; i++){
            if (map.containsKey(String.valueOf(strArr[i]))){
                map.put(String.valueOf(strArr[i]), map.get(String.valueOf(strArr[i])) + 1);
            }else{
                map.put(String.valueOf(strArr[i]), 1);
            }
        }
        System.out.println(map);
    }
    private static boolean choiceSort(int[] arr){
        for (int i = 0; i < arr.length ; i++) {
            int temp = i;
            for (int j = temp + 1; j < arr.length ; j++) {
                if (arr[i] < arr[j]){

                }
            }

        }
        return false;
    }

    /**
     * 删除数组中目标位置元素
     * @param arr 数组
     * @param position 目标位置
     * @return 执行状态 boolean
     */
    private static boolean deleteNumberInArray(int[] arr, int position){
        if (position < 0 || position > arr.length){
            System.out.println("参数错误！");
            return false;
        }

        int temp = 0;
        for (int i = position - 1; i < arr.length - 1; i++) {
            temp = arr[i];
            arr[i] = arr[i + 1];
            arr[i + 1] = temp;
        }
        arr[arr.length - 1] = 0;

        return true;
    }

    /**
     * 在数组中指定位置插入目标数
     * @param arr 目标数组
     * @param position 目标位置
     * @param number 目标数
     * @return 返回执行结果 boolean
     */
    private static boolean insertIntoArray(int[] arr, int position, int number){
        if (position < 0 || position > arr.length){
            System.out.println("参数错误！");
            return false;
        }

        int temp = 0;
        for (int i = arr.length - 1; i > position; i--) {
            temp = arr[i];
            arr[i] = arr[i - 1];
            arr[i - 1] = temp;
        }
        arr[position] = number;
        return true;

    }

    /**
     * 找出数组中目标值出现的所有位置
     * @param arr 目标数组
     * @param positionArr 保存 出现位置的数组
     * @param targetNumber 目标数字
     * @return 返回出现个数
     */
    private static int findAllPosition(int[] arr, int[] positionArr, int targetNumber){
        if (positionArr.length < arr.length){
            System.out.println("参数错误1");
            return -1;
        }

        int count = 0;
        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == targetNumber){
                positionArr[count] = i + 1;
                count++;

            }
        }
        return count;
    }

    /**
     * 找出数组中某个值最后出现的位置
     * @param arr 目标数组
     * @param targetNum 目标值
     * @return 目标位置  不存在则返回 -1
     */
    private static int findLastPosition(int[] arr, int targetNum){
        int index = -1;
        for (int i = arr.length - 1; i > 0; i--) {
            if (targetNum ==  arr[i]){
                index = i;
            }
        }
        return index;
    }

    /**
     * 找出数组中最小值的下标
     * @param arr 目标数组
     * @return 返回下标位置
     */
    private static int findMinIndex(int[] arr){
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[index] > arr[i]){
                index = i;
            }
        }
        return index;
    }


}
