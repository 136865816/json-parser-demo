package com.abc.util;

import com.abc.model.ApiRequest;
import com.abc.model.Teacher;
import com.abc.model.User;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private JsonUtil(){};

    public static <T> ApiRequest parseObject(String jsonStr, Class<T> clz){
        if(jsonStr != null && jsonStr.trim().length() >0 ){
            ApiRequest apiRequest = JSONObject.parseObject(jsonStr, ApiRequest.class);
            T t = JSONObject.parseObject(JSONObject.toJSONString(apiRequest.getData()), clz);
            apiRequest.setData(t);
            return apiRequest;
        }
        return null;
    }

    public static <T> ApiRequest<List> parseList(String jsonStr, Class<T> clz){
        if(jsonStr != null && jsonStr.trim().length() > 0){
            ApiRequest apiRequest = JSONObject.parseObject(jsonStr, ApiRequest.class);
            List<T> dataList = JSONObject.parseArray(JSONObject.toJSONString(apiRequest.getData()), clz);
            apiRequest.setData(dataList);
            return apiRequest;
        }
        return null;
    }

    public static <T> ApiRequest parse(String jsonStr, Class<T> clz){
        if(jsonStr != null && jsonStr.trim().length() >0 ){
            ApiRequest request = JSONObject.parseObject(jsonStr,ApiRequest.class);
            if(request != null && request.getData() != null){
                Object data = null;
                if(request.getData() instanceof List){
                    List<T> list = JSONObject.parseArray(JSONObject.toJSONString(request.getData()), clz);
                    data = list;
                }else{
                    T t = JSONObject.parseObject(JSONObject.toJSONString(request.getData()), clz);
                    data = t;
                }
                request.setData(data);
                return request;
            }
            return request;
        }
        return null;
    }

    public static void main(String[] a){
        User u1 = new User();
        u1.setName("a");

        User u2 = new User();
        u2.setName("b");

        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);


        Teacher teacher = new Teacher();
        teacher.setName("c");
        teacher.setUserList(userList);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setCode("200");
        apiRequest.setMsg("success");
//        apiRequest.setData(teacher);

//        apiRequest.setData(userList);
        ApiRequest apiRequest1 = JsonUtil.parse(JSONObject.toJSONString(apiRequest), Teacher.class);
        System.out.println("");
    }

}