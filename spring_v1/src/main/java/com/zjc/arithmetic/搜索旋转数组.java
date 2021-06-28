package com.zjc.arithmetic;

/**
 * @ClassName : Test
 * @Author : zhangjiacheng
 * @Date : 2021/5/31
 * @Description :
 * 反转数组中查找k值
 * 对于有序数组，可以使用二分查找的方法查找元素。
 *
 * 但是这道题中，数组本身不是有序的，进行旋转后只保证了数组的局部是有序的，这还能进行二分查找吗？答案是可以的。
 *
 * 可以发现的是，我们将数组从中间分开成左右两部分的时候，一定有一部分的数组是有序的。
 * 拿示例来看，我们从 6 这个位置分开以后数组变成了 [4, 5, 6] 和 [7, 0, 1, 2] 两个部分，其中左边 [4, 5, 6] 这个部分的数组是有序的，其他也是如此。
 *
 * 这启示我们可以在常规二分查找的时候查看当前 mid 为分割位置分割出来的两个部分 [l, mid] 和 [mid + 1, r] 哪个部分是有序的，
 * 并根据有序的那个部分确定我们该如何改变二分查找的上下界，因为我们能够根据有序的那部分判断出 target 在不在这个部分：
 *
 * 如果 [l, mid - 1] 是有序数组，且 target 的大小满足 [{nums}[l],{nums}[mid])[nums[l],nums[mid])，
 * 则我们应该将搜索范围缩小至 [l, mid - 1]，否则在 [mid + 1, r] 中寻找。
 * 如果 [mid, r] 是有序数组，且 target 的大小满足 ({nums}[mid+1],{nums}[r]](nums[mid+1],nums[r]]，
 * 则我们应该将搜索范围缩小至 [mid + 1, r]，否则在 [l, mid - 1] 中寻找。
 *
 */
public class 搜索旋转数组 {



    public static int solution (int[] a, int x){

        if (a.length == 0) {
            return -1;
        }

        if (a.length == 1) {
            return a[0] == x ? 0 : -1;
        }

        int left = 0;
        int right = a.length-1;
        while (left<=right){
            int m = left + (right-left) /2;
            if (a[m]==x){
                return m;
            }
            if (a[m] <= a[right]){
                if (a[m]<x && x<=a[right]) {
                    left = m + 1;
                }else {
                    right = m-1;
                }
            }else{
                if (a[left]<x && x <a[m]){
                    right = m-1;
                }else {
                    left = m + 1;
                }
            }
        }
        return  -1;
    }

    public static void main(String[] args) {
        int[] i = {4,5,6,7,1,2,3};
        int ss = solution(i, 1);
        System.out.println(ss);
    }
}
