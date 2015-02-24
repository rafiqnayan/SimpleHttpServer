/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.message;

/**
 *
 * @author user
 */
public class HttpBadRequestResponse extends HttpResponse{

    public HttpBadRequestResponse() {
        this.setHttpMessage("Bad request received.");
        this.setStatus(BAD_REQUEST);
    }
    
}
