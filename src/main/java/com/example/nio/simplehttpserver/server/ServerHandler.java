/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.server;

import com.example.nio.simplehttpserver.message.Request;
import com.example.nio.simplehttpserver.message.Response;
import java.nio.ByteBuffer;

/**
 *
 * @author Rafiqunnabi Nayan
 */
public interface ServerHandler {
    public Response getResponse(Request request);
    public Request parseRequest(ByteBuffer byteBuffer);
}
