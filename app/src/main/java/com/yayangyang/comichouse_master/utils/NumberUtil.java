package com.yayangyang.comichouse_master.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class NumberUtil {

    public static List<Integer> getRandomListByNum(int size){
        double v = Math.random() * 1000;
        List<Integer> randomNummberList=new ArrayList<>();
        for(int i=0;i<size;i++){
            boolean isOk=true;
            if(i!=0){
                isOk=false;
                v=Math.random() * 1000;
                for(int j=0;j<randomNummberList.size();j++){
                    if(v==randomNummberList.get(j)){
                        i--;
                        isOk=false;
                        break;
                    }
                    isOk=true;
                }
            }
            if(isOk){
                randomNummberList.add((int) v);
            }
        }
        LogUtils.e("randomNummberList.size():"+randomNummberList.size());
        return randomNummberList;
    }

}
