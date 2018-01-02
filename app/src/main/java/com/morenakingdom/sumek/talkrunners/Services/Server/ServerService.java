package com.morenakingdom.sumek.talkrunners.Services.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.morenakingdom.sumek.talkrunners.Exceptions.ServerException;
import com.morenakingdom.sumek.talkrunners.Models.Client;

/**
 * Created by sumek on 1/1/18.
 */

public class ServerService {

    ServerSocket serverSocket;

    List<Client> clients = null;

    private Thread connectionEstablishedThread;

    private ConnectionEstablishedModule connectionEstablished;

    private List<Thread> socketListeners;


    public ServerService(int port) throws ServerException {
        try {

            clients = new ArrayList<>();
            this.socketListeners = new ArrayList<>();
            this.serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
            String message = String.format("Cannot create server: %s", e.getMessage());
            throw new ServerException(message);
        }
    }


    public void initConnectionEstablishedModule() {
        this.connectionEstablished = new ConnectionEstablishedModule( this );
        this.connectionEstablishedThread = new Thread( connectionEstablished );
        this.connectionEstablishedThread.start();
    }

    public void interruptConnectionEstablishedModule() {
        this.connectionEstablishedThread.interrupt();
    }

    void addClient(Client client) {
        clients.add( client );

        System.out.println( "New Client" );
    }

    void startCommunication(Socket sock) {
        ServerCommunicationModule listener = new ServerCommunicationModule( this, sock );
        Thread th = new Thread(listener);
        th.start();

        socketListeners.add(th);

        System.out.println("New connection established!!!");

    }
}
