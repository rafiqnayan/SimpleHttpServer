/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.server;

import com.example.nio.simplehttpserver.message.HttpBadRequestResponse;
import com.example.nio.simplehttpserver.message.HttpFileNotFoundResponse;
import com.example.nio.simplehttpserver.message.Request;
import com.example.nio.simplehttpserver.message.Response;
import com.example.nio.simplehttpserver.message.HttpRequest;
import com.example.nio.simplehttpserver.message.HttpResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author user
 */
public class HttpServerHandler implements ServerHandler {

    public static final Pattern pattern = Pattern.compile("GET /\\?file=(.*) .*");
    
    private ServerConfiguration serverConfiguration = null;

    public HttpServerHandler(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }
    
    @Override
    public Response getResponse(Request request) {
        HttpRequest httpRequest = (HttpRequest)request;
        
        String fileName = httpRequest.getQueryParam("file");
        
        String filePath = serverConfiguration.getDataPath() + "/" + fileName;
        
        System.out.println("Serving " + filePath);
        
        HttpResponse httpResponse = new HttpResponse();
        
        try {
            String fileContent = this.getFileContent(filePath);
            httpResponse.setHttpMessage(fileContent);
        } catch (FileNotFoundException ex) {
            httpResponse = new HttpFileNotFoundResponse();
        } catch (IOException ex) {
            httpResponse = new HttpBadRequestResponse();
        }
        
        return httpResponse;
    }

    @Override
    public Request parseRequest(ByteBuffer byteBuffer) {
        String raw = new String(byteBuffer.array());
        String firstLine = raw.split("\n")[0].trim();

        Matcher matcher = pattern.matcher(firstLine);
        boolean matches = matcher.matches();

        String requestedFileName = serverConfiguration.getIndexFile();
        
        if (matches) {
            // file parameter received
            requestedFileName = matcher.group(1);
        }
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.addQueryParam("file", requestedFileName);

        return httpRequest;
    }

    private String getFileContent(String path) throws FileNotFoundException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        
        URL resourceUrl = classLoader.getResource(path);
        
        if(resourceUrl == null){
            throw new FileNotFoundException(path + " file not found");
        }
        
        File file = new File(resourceUrl.getFile());
        
        RandomAccessFile aFile = new RandomAccessFile(file, "r");
        FileChannel inChannel = aFile.getChannel();
        MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        buffer.load();
        
        StringBuilder stringBuilder = new StringBuilder();
        
        for (int i = 0; i < buffer.limit(); i++) {
            stringBuilder.append((char) buffer.get());
        }
        buffer.clear(); // do something with the data and clear/compact it.
        inChannel.close();
        aFile.close();
        
        return stringBuilder.toString();
    }

}
