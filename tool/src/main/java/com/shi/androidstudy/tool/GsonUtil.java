package com.shi.androidstudy.tool;

import com.google.gson.Gson;

/**
 * Created by SHI on 2017/3/28 11:33
 */

public class GsonUtil {

    private static Gson gson;
    private GsonUtil(){

    }
    public static Gson getInstance(){
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }
}
