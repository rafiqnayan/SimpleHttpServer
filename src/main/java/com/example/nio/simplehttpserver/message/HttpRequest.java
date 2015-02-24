/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.message;

import java.util.HashMap;

/**
 *
 * @author user
 */
public class HttpRequest implements Request{
    private HashMap<String, String> queryParams = new HashMap<String, String>();

    public HashMap<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(HashMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }
    
    
    public void addQueryParam(String paramKey, String paramValue){
        this.queryParams.put(paramKey, paramValue);
    }
    
    public boolean containsQueryParam(String paramKey){
        return this.queryParams.containsKey(paramKey);
    }
    
    public String getQueryParam(String paramKey){
        return this.queryParams.get(paramKey);
    }
}
