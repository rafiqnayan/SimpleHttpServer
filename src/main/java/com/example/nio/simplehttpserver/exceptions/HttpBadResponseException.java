/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.exceptions;

/**
 *
 * @author user
 */
public class HttpBadResponseException extends Exception{

    public HttpBadResponseException() {
    }

    public HttpBadResponseException(String message) {
        super(message);
    }
    
}
