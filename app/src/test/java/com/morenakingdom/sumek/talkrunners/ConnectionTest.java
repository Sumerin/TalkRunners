package com.morenakingdom.sumek.talkrunners;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by sumek on 1/2/18.
 */

public class ConnectionTest {

    final static String IP = "127.0.0.1";
    final static int PORT = 1234;

    ServerSocket serverSocket;
    Socket clientSock;
    Socket serverSock;


    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket( PORT );
    }


    @After
    public void cleanUp() throws IOException {
        serverSocket.close();

        if (clientSock != null) {
            clientSock.close();
            clientSock = null;
        }
        if (serverSock != null) {
            serverSock.close();
            serverSock = null;
        }
    }

    @Test(timeout = 1000)
    public void ConnectToServer() throws IOException {
        InetAddress addr = InetAddress.getByName( IP );
        clientSock = new Socket( addr, PORT );

        serverSock = serverSocket.accept();
    }


    @Test(timeout = 4000)
    public void BufferedStream() throws IOException {
        InetAddress addr = InetAddress.getByName( IP );
        clientSock = new Socket( addr, PORT );

        serverSock = serverSocket.accept();

        ObjectOutputStream clientOut = new ObjectOutputStream( new BufferedOutputStream( clientSock.getOutputStream() ) );
        ObjectOutputStream serverOut = new ObjectOutputStream( new BufferedOutputStream( serverSock.getOutputStream() ) );

        clientOut.writeChar( 'a' );
        clientOut.flush();
        serverOut.writeChar( 'a' );
        serverOut.flush();

        ObjectInputStream clientIn = new ObjectInputStream( new BufferedInputStream( clientSock.getInputStream() ) );
        //ObjectInputStream serverIn = new ObjectInputStream( new BufferedInputStream( serverSock.getInputStream() ) );


    }


}
