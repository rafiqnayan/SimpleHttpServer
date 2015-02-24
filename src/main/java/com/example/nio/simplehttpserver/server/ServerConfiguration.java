/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 *
 * @author user
 */
public class ServerConfiguration extends Properties {

    public static final String KEY_SERVER_IP = "server.ip";
    public static final String KEY_SERVER_PORT = "server.port";
    public static final String DEFAULT_SERVER_IP = "127.0.0.1";
    public static final String DEFAULT_SERVER_PORT = "8000";
    public static final String CONFIG_FILE_NAME = "server.config";
    public static final String KEY_SERVER_DIR = "server.dir";
    public static final String DEFAULT_SERVER_DIR = "data";
    public static final String KEY_SERVER_INDEX = "server.index";
    public static final String DEFAULT_SERVER_INDEX = "index";

    private String ipAddress;
    private int port;
    private String dataPath;
    private String indexFile;

    public ServerConfiguration() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(CONFIG_FILE_NAME).getFile());
        
        try {
            Reader reader = new FileReader(file);
            this.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.ipAddress = this.getProperty(KEY_SERVER_IP, DEFAULT_SERVER_IP);
        this.dataPath = this.getProperty(KEY_SERVER_DIR, DEFAULT_SERVER_DIR);
        this.indexFile = this.getProperty(KEY_SERVER_INDEX, DEFAULT_SERVER_INDEX);
        
        String portStr = this.getProperty(KEY_SERVER_PORT, DEFAULT_SERVER_PORT);
        this.port = Integer.parseInt(portStr);
    }

    ////// Getters and setters \\\\\\\\\\\\\\
    public String getIpAddress() {

        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getIndexFile() {
        return indexFile;
    }

    public void setIndexFile(String indexFile) {
        this.indexFile = indexFile;
    }

}
