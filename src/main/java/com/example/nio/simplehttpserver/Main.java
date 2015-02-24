package com.example.nio.simplehttpserver;

import com.example.nio.simplehttpserver.server.HttpServerHandler;
import com.example.nio.simplehttpserver.server.Server;
import com.example.nio.simplehttpserver.server.ServerConfiguration;
import com.example.nio.simplehttpserver.server.ServerHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafiqunnabi Nayan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ServerConfiguration serverConfig = new ServerConfiguration();
        ServerHandler serverHandler = new HttpServerHandler(serverConfig);

        Server server = new Server(serverConfig, serverHandler);

        try {
            server.run();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
