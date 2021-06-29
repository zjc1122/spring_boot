package com.zjc.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName : 电话号码字母组合
 * @Author : zhangjiacheng
 * @Date : 2021/6/29
 * @Description : TODO
 */
public class 电话号码字母组合 {

    public static List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if (digits.length()==0){
            return list;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};

        test2(list,phoneMap,digits,0,new StringBuffer());
        return list;
    }

    private static void test2(List<String> list, Map<Character, String> phoneMap, String digits, int index, StringBuffer stringBuffer) {
        if (index==digits.length()){
            list.add(stringBuffer.toString());
        }else{
            char c = digits.charAt(index);
            String s = phoneMap.get(c);
            for (int i =0;i<s.length();i++){
                char c1 = s.charAt(i);
                stringBuffer.append(c1);
                test2(list,phoneMap,digits,index+1,stringBuffer);
                stringBuffer.deleteCharAt(index);
            }
        }
    }

    public static void main(String[] args) {
        List<String> strings = letterCombinations("23");
        strings.forEach(System.out::println);
    }
}
