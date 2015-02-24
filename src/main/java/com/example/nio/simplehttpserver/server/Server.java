/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.nio.simplehttpserver.server;

import com.example.nio.simplehttpserver.exceptions.HttpBadResponseException;
import com.example.nio.simplehttpserver.message.Request;
import com.example.nio.simplehttpserver.message.Response;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafiqunnabi Nayan
 */
public class Server {

    private ServerConfiguration serverConfiguration = null;
    private ServerHandler serverHandler = null;

    public Server(ServerConfiguration serverConfig, ServerHandler serverHandler) {
        this.serverConfiguration = serverConfig;
        this.serverHandler = serverHandler;
    }

    public void run() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new java.net.InetSocketAddress(serverConfiguration.getPort()));
        server.configureBlocking(false);
        SelectionKey serverkey = server.register(selector, SelectionKey.OP_ACCEPT);

        for (;;) {
            selector.select();
            Set keys = selector.selectedKeys();
            // Use a java.util.Iterator to loop through the selected keys
            for (Iterator i = keys.iterator(); i.hasNext();) {
                SelectionKey key = (SelectionKey) i.next();
                i.remove();
                if (key == serverkey) {
                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        // Make sure we actually got a connection
                        if (client == null) {
                            continue;
                        }
                        client.configureBlocking(false);
                        SelectionKey clientkey = client.register(selector, SelectionKey.OP_READ);
                    }
                } else {
                    serveClient(key);
                }
            }
        }
    }

    private void serveClient(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();

        if (!key.isReadable()) {
            return;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        client.read(byteBuffer);

        Request request = serverHandler.parseRequest(byteBuffer);
        Response response = serverHandler.getResponse(request);

        try {
            client.write(response.getByteBuffer());
        } catch (HttpBadResponseException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        byteBuffer.clear();

        client.close(); // Close the channel.
        key.cancel(); // Tell Selector to stop monitoring it.
    }

}
