package com.chablis.repair.utils;

import java.util.List;

/**
 * Created by chablis on 2017/7/19.
 */

public class StringUtils {
    public static String array2String(String [] array){
        String str = "";
        if (array.length==1){
            return array[0];
        }else {
            for (String i : array) {
                str += i + ",";
            }
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    public static String list2String(List<String> array){
        String str = "";
        if (array.size()==1){
            return array.get(0);
        }else {
            for (String i : array) {
                str += i + ",";
            }
            str = str.substring(0, str.length() - 1);
            return str;
        }
    }
}
