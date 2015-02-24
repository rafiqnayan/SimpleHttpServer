/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.message;

import com.example.nio.simplehttpserver.exceptions.HttpBadResponseException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class HttpResponse implements Response {
    
    
    public static final String FILE_NOT_FOUND = "404 Not Found";
    public static final String RESPONSE_OK = "200 OK";
    public static final String BAD_REQUEST = "400 Bad Request";
    
    private String status = RESPONSE_OK;

    public String getStatusCode() {
        return status;
    }

    public void setStatus(String statusCode) {
        this.status = statusCode;
    }

    private Charset charset = Charset.forName("ISO-8859-1");

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    private CharsetEncoder encoder = charset.newEncoder();

    // The HTTP headers we send back to the client are fixed
    private String headerFormat = "HTTP/1.1 %s\r\n" + "Content-type: text/html\r\n"
            + "Connection: close\r\n" + "\r\n";

    private String httpMessage = null;

    public String getHttpMessage() {
        return httpMessage;
    }

    public void setHttpMessage(String httpMessage) {
        this.httpMessage = httpMessage;
    }

    @Override
    public ByteBuffer getByteBuffer() throws HttpBadResponseException {
        String header = String.format(headerFormat, status);
        String httpResponseContent = header + httpMessage;
        ByteBuffer byteBuffer = null;

        try {
            byteBuffer = encoder.encode(CharBuffer.wrap(httpResponseContent));
        } catch (CharacterCodingException ex) {
            // Logger.getLogger(HttpResponse.class.getName()).log(Level.SEVERE, null, ex);
            HttpBadResponseException badResponseException = 
                    new HttpBadResponseException("Failed to encode message");
            
            badResponseException.setStackTrace(ex.getStackTrace());
            throw badResponseException;
        }

        return byteBuffer;
    }

}
