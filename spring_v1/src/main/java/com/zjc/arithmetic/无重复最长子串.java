package com.zjc.arithmetic;

import java.util.HashSet;
import java.util.Set;

public class 无重复最长子串 {

    public static int subString(String str){
        //记录是否出现过
        Set<Character> set = new HashSet<Character>();
        int n = str.length();
        //右指针，初始值为-1，相当于字符串的左边界在左侧，还没移动
        int r=-1,l=0;
        for (int i=0;i<n;++i){
            if (i!=0){
                //左指针右侧移动一个，删除一个字符
                set.remove(str.charAt(i-1));
            }
            while (r+1<n&&!set.contains(str.charAt(r+1))){
                //一直移动右指针
                set.add(str.charAt(r+1));
                ++r;
            }
            //第i到r个字符是一个特别长的无重复字符子串
            l = Math.max(l,r-i+1);
        }
        return l;
    }


    public static void main(String[] args) {
        String s = "abcabcbb";
        int i = subString(s);
        System.out.println(i);
    }
}
