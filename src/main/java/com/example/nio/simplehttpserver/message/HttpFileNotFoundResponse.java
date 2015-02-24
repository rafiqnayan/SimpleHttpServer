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
public class HttpFileNotFoundResponse extends HttpResponse{

    public HttpFileNotFoundResponse() {
        this.setHttpMessage("File not found.");
        this.setStatus(FILE_NOT_FOUND);
    }
    
}
