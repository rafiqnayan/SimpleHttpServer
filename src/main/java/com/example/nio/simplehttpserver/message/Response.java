/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.message;

import com.example.nio.simplehttpserver.exceptions.HttpBadResponseException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

/**
 *
 * @author user
 */
public interface Response {
    public ByteBuffer getByteBuffer() throws HttpBadResponseException;
}
