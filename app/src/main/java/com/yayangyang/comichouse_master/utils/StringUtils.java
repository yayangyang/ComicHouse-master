package com.yayangyang.comichouse_master.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static String creatAcacheKey(Object... param) {
        String key = "";
        for (Object o : param) {
            key += "-" + o;
        }
        return key.replaceFirst("-","");
    }

    /**
     * 格式化小说内容。
     * <p/>
     * <li>小说的开头，缩进2格。在开始位置，加入2格空格。
     * <li>所有的段落，缩进2格。所有的\n,替换为2格空格。
     *
     * @param str
     * @return
     */
    public static String formatContent(String str) {
        str = str.replaceAll("[ ]*", "");//替换来自服务器上的，特殊空格
        str = str.replaceAll("[ ]*", "");//
        str = str.replace("\n\n", "\n");
        str = str.replace("\n", "\n" + getTwoSpaces());
        str = getTwoSpaces() + str;
//        str = convertToSBC(str);
        return str;
    }

    /**
     * Return a String that only has two spaces.
     *
     * @return
     */
    public static String getTwoSpaces() {
        return "\u3000\u3000";
    }


    public static ArrayList<Integer> getAllIndex(String str,String key) {
        ArrayList<Integer> integers = new ArrayList<>();
        int a = str.indexOf(key);//*第一个出现的索引位置
        integers.add(a);
        while (a != -1) {
            a = str.indexOf(key, a + 1);//*从这个索引往后开始第一个出现的位置
            if(a!=-1){
                integers.add(a);
            }
        }
        return integers;
    }

    /**
     * 已知对应字符串之后有数字才可使用该方法
     * @param str
     * @param index
     * @return
     */
    public static String getNumberByIndex(String str,int index) {
        String number="";
        str=str.substring(++index,str.length());
        LogUtils.e("str:"+str);
        for(int i=0;i<str.length();i++){
            LogUtils.e("char"+str.charAt(i));
            if(Character.isDigit(str.charAt(i))){//判断字符是否是数字
                number+=str.charAt(i);
            }else{
                break;
            }
        }
        return number;
    }

}
