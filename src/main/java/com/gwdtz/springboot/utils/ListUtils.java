package com.gwdtz.springboot.utils;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @program: first
 * @Date: 2019/4/15 10:15
 * @Author: Mr.Wu
 * @Description:
 */
public class ListUtils {
    //list集合去重
    public static List<String> removeDuplicate(List<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet(list.size());
        set.addAll(list);
        List<String> list1 = Lists.newArrayList();
        list1.addAll(set);
        return list1;
    }

    public static int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);Arrays.sort(nums2);
        //用HashSet存储不重复的结果
        Set<Integer> set=new HashSet<Integer>();
        int i=0,j=0;
        while(i<nums1.length&&j<nums2.length){
            if(nums1[i]==nums2[j]){
                set.add(nums1[i]);
                i++;
                j++;
            }
            else if(nums1[i]<nums2[j]){
                i++;
            }
            else{
                j++;
            }
        }
        int[] res=new int[set.size()];
        int k=0;
        //HashSet用增强型for循环遍历，结果赋给数组
        for(int temp:set){
            res[k]=temp;
            k++;
        }
        return res;
    }
}
